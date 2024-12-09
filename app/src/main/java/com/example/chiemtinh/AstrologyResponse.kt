package com.example.chiemtinh
data class AstrologyResponse(
    val location: Location,
    val astronomy: Astronomy
)

data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val tz_id: String,
    val localtime_epoch: Long,
    val localtime: String
)

data class Astronomy(
    val astro: Astro
)

data class Astro(
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonset: String,
    val moon_phase: String,
    val moon_illumination: Int,
    val is_moon_up: Int,
    val is_sun_up: Int
)