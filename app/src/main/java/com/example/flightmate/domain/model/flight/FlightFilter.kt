package com.example.flightmate.domain.model.flight

/**
 * 航班資訊篩選條件。
 *
 * @param flightStatus 航班狀態。
 * @param airlineCode 航空公司代號。
 */
data class FlightFilter(
    val flightStatus: Set<FlightStatus> = emptySet(),
    val airlineCode: Set<String> = emptySet()
)
