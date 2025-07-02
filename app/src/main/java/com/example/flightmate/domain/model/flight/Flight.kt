package com.example.flightmate.domain.model.flight

enum class FlightStatus(val label: String) {
    ARRIVED("抵達Arrived"),
    DELAYED("延遲Delayed"),
    ON_TIME("準時On Time"),
    CANCELLED("取消Cancelled"),
    UNKNOWN("");

    companion object {
        fun fromLabel(label: String): FlightStatus =
            entries.find { it.label == label } ?: UNKNOWN
    }
}

data class FlightAirline(
    val code: String,
    val name: String
)
