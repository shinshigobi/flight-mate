package com.example.flightmate.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 航班資訊。
 *
 * @param instantSchedule 航班資訊列表。
 */
@JsonClass(generateAdapter = true)
data class FlightResponse(
    @Json(name = "InstantSchedule")
    val instantSchedule: List<FlightInfo>
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
data class FlightInfo(
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
