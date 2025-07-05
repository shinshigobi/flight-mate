package com.example.flightmate.domain.model.flight

enum class FlightStatus(val label: String, val displayLabel: String) {
    ARRIVED("抵達Arrived", "Arrived"),
    DELAYED("延遲Delayed", "Delayed"),
    ON_TIME("準時On Time", "On Time"),
    CANCELLED("取消Cancelled", "Cancelled"),
    UNKNOWN("", "");

    companion object {
        fun fromLabel(label: String): FlightStatus =
            entries.find { it.label == label } ?: UNKNOWN
    }
}

/**
 * 航班資訊。
 *
 * @param expectTime 預計時間。
 * @param realTime 實際時間。
 * @param airline 航空公司資訊。
 * @param upAirportCode 起飛機場代號。
 * @param upAirportName 起飛機場名稱。
 * @param airplaneType 機型。
 * @param airBoardingGate 登機門。
 * @param flightStatus 航班狀態。
 * @param delayCause 延誤原因。
 */
data class FlightInfo(
    val expectTime: String,
    val realTime: String,
    val airline: FlightAirline,
    val upAirportCode: String,
    val upAirportName: String,
    val airplaneType: String,
    val airBoardingGate: String,
    val flightStatus: FlightStatus,
    val delayCause: String
)

/**
 * 航空公司資訊。
 *
 * @param code 航空公司名稱。
 * @param name 航空公司代號。
 * @param logoUrl 航空公司標誌。
 * @param url 航空公司網址。
 * @param no 航班編號。
 */
data class FlightAirline(
    val code: String,
    val name: String,
    val logoUrl: String,
    val url: String,
    val no: String,
)
