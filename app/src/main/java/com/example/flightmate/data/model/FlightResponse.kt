package com.example.flightmate.data.model

import com.example.flightmate.domain.model.flight.FlightAirline
import com.example.flightmate.domain.model.flight.FlightStatus
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlin.String
import com.example.flightmate.domain.model.flight.FlightInfo

/**
 * 航班資訊。
 *
 * @param instantSchedule 航班資訊列表。
 */
@JsonClass(generateAdapter = true)
data class FlightResponse(
    @Json(name = "InstantSchedule")
    val instantSchedule: List<FlightInfoResponse>
)

/**
 * @param expectTime 預計時間。
 * @param realTime 實際時間。
 * @param airlineName 航空公司名稱。
 * @param airlineCode 航空公司代號。
 * @param airlineLogo 航空公司標誌。
 * @param airlineUrl 航空公司網址。
 * @param airlineNo 航空公司編號。
 * @param upAirportCode 起飛機場代號。
 * @param upAirportName 起飛機場名稱。
 * @param airplaneType 機型。
 * @param airBoardingGate 登機門。
 * @param flightStatus 狀態。
 * @param delayCause 延誤原因。
 */
@JsonClass(generateAdapter = true)
data class FlightInfoResponse(
    @Json(name = "expectTime")
    val expectTime: String,

    @Json(name = "realTime")
    val realTime: String,

    @Json(name = "airLineName")
    val airlineName: String,

    @Json(name = "airLineCode")
    val airlineCode: String,

    @Json(name = "airLineLogo")
    val airlineLogo: String,

    @Json(name = "airLineUrl")
    val airlineUrl: String,

    @Json(name = "airLineNum")
    val airlineNo: String,

    @Json(name = "upAirportCode")
    val upAirportCode: String,

    @Json(name = "upAirportName")
    val upAirportName: String,

    @Json(name = "airPlaneType")
    val airplaneType: String,

    @Json(name = "airBoardingGate")
    val airBoardingGate: String,

    @Json(name = "airFlyStatus")
    val flightStatus: String,

    @Json(name = "airFlyDelayCause")
    val delayCause: String
)

fun FlightInfoResponse.toDomain(): FlightInfo {
    return FlightInfo(
        expectTime = expectTime,
        realTime = realTime,
        airline = FlightAirline(
            code = airlineCode,
            name = airlineName,
            logoUrl = airlineLogo,
            url = airlineUrl,
            no = airlineNo
        ),
        upAirportCode = upAirportCode,
        upAirportName = upAirportName,
        airplaneType = airplaneType,
        airBoardingGate = airBoardingGate,
        flightStatus = FlightStatus.fromLabel(flightStatus),
        delayCause = delayCause
    )
}
