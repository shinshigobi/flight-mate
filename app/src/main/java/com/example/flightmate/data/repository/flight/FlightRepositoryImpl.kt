package com.example.flightmate.data.repository.flight

import com.example.flightmate.data.model.toDomain
import com.example.flightmate.data.remote.FlightApi
import com.example.flightmate.domain.exception.AppException
import com.example.flightmate.domain.model.flight.FlightInfo
import com.squareup.moshi.JsonDataException
import java.io.IOException
import javax.inject.Inject

class FlightRepositoryImpl @Inject constructor(
    private val api: FlightApi
) : FlightRepository {
    override suspend fun getFlightInfo(): Result<List<FlightInfo>> {
        return try {
            val response = api.getFlightInfoList()
            if (response.isSuccessful) {
                val flightList = response.body()?.instantSchedule?.map { it.toDomain() } ?: emptyList()
                Result.success(flightList)
            } else {
                Result.failure(
                    AppException.HttpError(
                        code = response.code(),
                        errorBody = response.errorBody()?.string()
                    )
                )
            }
        } catch (e: JsonDataException) {
            Result.failure(AppException.ApiError("Invalid JSON format"))
        } catch (e: IOException) {
            Result.failure(AppException.NetworkError)
        } catch (e: Exception) {
            Result.failure(AppException.UnknownError(e))
        }
    }
}
