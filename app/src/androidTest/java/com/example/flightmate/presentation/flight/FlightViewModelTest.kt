package com.example.flightmate.presentation.flight

import com.example.flightmate.data.model.FlightInfoResponse
import com.example.flightmate.data.model.FlightResponse
import com.example.flightmate.data.repository.flight.FlightRepository
import com.example.flightmate.domain.model.flight.FlightStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class FlightViewModelTest {

    private lateinit var viewModel: FlightViewModel
    private lateinit var mockRepository: MockFlightRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockRepository = MockFlightRepository()
        viewModel = FlightViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun flightList_filters_by_status() = runTest {
        // Arrange
        val expected = listOf(mockFlight1, mockFlight2) // 只有延遲的航班
        val job = launch {
            viewModel.flightList.collect { }
        }

        // Act
        viewModel.filterByStatus(FlightStatus.DELAYED)
        advanceUntilIdle() // 等待 combine 和 flow 完成

        // Assert
        val result = viewModel.flightList.value
        assertEquals(expected, result)

        // Tear down
        job.cancel()
    }

    @Test
    fun flightList_filters_by_airline() = runTest {
        // Arrange
        val expected = listOf(mockFlight2)
        val job = launch {
            viewModel.flightList.collect { }
        }

        // Act
        viewModel.filterByAirline("MDA")
        advanceUntilIdle() // 等待 combine 和 flow 完成

        // Assert
        val result = viewModel.flightList.value
        assertEquals(expected, result)

        // Tear down
        job.cancel()
    }

    @Test
    fun flightList_filters_by_both_airline_and_status() = runTest {
        // Arrange
        val expected = listOf(mockFlight1)
        val job = launch {
            viewModel.flightList.collect { }
        }

        // Act
        viewModel.filterByStatus(FlightStatus.DELAYED)
        viewModel.filterByAirline("MDA")
        advanceUntilIdle()

        // Assert
        val result = viewModel.flightList.value
        assertEquals(expected, result)

        // Tear down
        job.cancel()
    }
}

val mockFlight1 = FlightInfoResponse(
    expectTime = "09:00",
    realTime = "08:50",
    airlineName = "立榮航空",
    airlineCode = "UIA",
    airlineLogo = "",
    airlineUrl = "",
    airlineNo = "B78690",
    upAirportCode = "MZG",
    upAirportName = "澎湖",
    airplaneType = "AT76",
    airBoardingGate = "",
    flightStatus = "延遲Delayed",
    delayCause = ""
)

val mockFlight2 = mockFlight1.copy(
    airlineCode = "MDA",
    airlineNo = "AE334",
    flightStatus = "延遲Delayed"
)

val mockFlight3 = mockFlight1.copy(
    airlineCode = "UIA",
    airlineNo = "AT76",
    flightStatus = "準時On Time"
)

class MockFlightRepository : FlightRepository {
    override suspend fun getFlightInfo(): Response<FlightResponse> {
        return Response.success(FlightResponse(listOf(mockFlight1, mockFlight2, mockFlight3)))
    }
}
