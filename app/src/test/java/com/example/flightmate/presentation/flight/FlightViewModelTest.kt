package com.example.flightmate.presentation.flight

import com.example.flightmate.domain.model.flight.FlightAirline
import com.example.flightmate.domain.model.flight.FlightInfo
import com.example.flightmate.domain.model.flight.FlightStatus
import com.example.flightmate.domain.usecase.GetFlightListUseCase
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FlightViewModelTest {

    private lateinit var viewModel: FlightViewModel
    private lateinit var fakeUseCase: FakeGetFlightListUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeUseCase = FakeGetFlightListUseCase()
        viewModel = FlightViewModel(fakeUseCase)
        viewModel.manualRefresh()
        testDispatcher.scheduler.runCurrent()
        viewModel.cancelRefresh()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun should_filter_flights_by_status_when_status_filter_is_applied() = runTest {
        // Arrange
        val expected = listOf(fakeFlight1, fakeFlight2) // 只有延遲的航班
        val job = launch { viewModel.flightList.collect {} }

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
    fun should_filter_flights_by_airline_when_airline_filter_is_applied() = runTest {
        // Arrange
        val expected = listOf(fakeFlight2)
        val job = launch { viewModel.flightList.collect {} }

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
    fun should_filter_flights_by_status_and_airline_when_both_filters_are_applied() = runTest {
        // Arrange
        val expected = listOf(fakeFlight2)
        val job = launch { viewModel.flightList.collect {} }

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

    @Test
    fun should_show_all_flights_when_no_filter_is_applied() = runTest {
        // Arrange
        val expected = listOf(fakeFlight1, fakeFlight2, fakeFlight3)
        val job = launch { viewModel.flightList.collect {} }

        // Act: no filter applied
        advanceUntilIdle()

        // Assert
        val result = viewModel.flightList.value
        assertEquals(expected, result)

        // Tear down
        job.cancel()
    }

    @Test
    fun should_show_empty_list_when_no_flight_matches_filters() = runTest {
        // Arrange
        val job = launch { viewModel.flightList.collect {} }

        // Act
        viewModel.filterByStatus(FlightStatus.DELAYED)
        viewModel.filterByAirline("ZZZ")
        advanceUntilIdle()

        // Assert
        val result = viewModel.flightList.value
        assertTrue(result.isEmpty())

        // Tear down
        job.cancel()
    }

    @Test
    fun should_show_all_flights_when_filters_are_cleared() = runTest {
        // Arrange
        val expected = listOf(fakeFlight1, fakeFlight2, fakeFlight3)
        val job = launch { viewModel.flightList.collect {} }

        // Act
        // 先設定
        viewModel.filterByStatus(FlightStatus.DELAYED)
        viewModel.filterByAirline("MDA")
        advanceUntilIdle()

        // 再取消
        viewModel.filterByStatus(FlightStatus.DELAYED)
        viewModel.filterByAirline("MDA")
        advanceUntilIdle()

        // Assert
        val result = viewModel.flightList.value
        assertEquals(expected, result)

        // Tear down
        job.cancel()
    }

    @Test
    fun should_return_distinct_airlines_when_airline_list_is_collected() = runTest {
        // Arrange
        val expected = listOf(fakeFlight1.airline, fakeFlight2.airline)
        val job = launch { viewModel.airlineList.collect {} }

        // Act
        advanceUntilIdle()

        // Assert
        val result = viewModel.airlineList.value
        assertEquals(expected, result)

        // Tear down
        job.cancel()
    }
}

val fakeFlight1 = FlightInfo(
    expectTime = "09:00",
    realTime = "08:50",
    airline = FlightAirline(
        code = "UIA",
        name = "立榮航空",
        logoUrl = "",
        url = "",
        no = "B78690",
    ),
    upAirportCode = "MZG",
    upAirportName = "澎湖",
    airplaneType = "AT76",
    airBoardingGate = "",
    flightStatus = FlightStatus.DELAYED,
    delayCause = ""
)

val fakeFlight2 = fakeFlight1.copy(
    airline = FlightAirline(
        code = "MDA",
        name = "華信航空",
        logoUrl = "",
        url = "",
        no = "AE334",
    )
)

val fakeFlight3 = fakeFlight1.copy(
    airline = FlightAirline(
        code = "UIA",
        name = "立榮航空",
        logoUrl = "",
        url = "",
        no = "AT76",
    ),
    flightStatus = FlightStatus.ON_TIME
)

class FakeGetFlightListUseCase : GetFlightListUseCase {
    override suspend fun invoke(): Result<List<FlightInfo>> {
        return Result.success(listOf(fakeFlight1, fakeFlight2, fakeFlight3))
    }
}
