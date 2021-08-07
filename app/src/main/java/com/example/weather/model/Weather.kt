package com.example.weather.model

import java.io.Serializable

class Weather(
    val Version: Int,
    val Key: String,
    val Type: String,
    val Rank: Int,
    val LocalizedName: String,
    val EnglishName: String,
    val PrimaryPostalCode: String,
    val Region: Region,
    val Country: Country,
    val AdministrativeArea: AdministrativeArea,
    val TimeZone: TimeZone,
    val GeoPosition: GeoPosition,
    val IsAlias: Boolean,
    val DataSets: List<String>
    ):Serializable

class Region(
    val ID: String,
    val LocalizedName: String,
    val EnglishName: String
    )

class Country(
    val ID: String,
    val LocalizedName: String,
    val EnglishName: String
    )

class AdministrativeArea(
    val ID: String,
    val LocalizedName: String,
    val EnglishName: String,
    val Level: Int,
    val LocalizedType: String,
    val EnglishType: String,
    val CountryID: String
    )

class TimeZone(
    val Code:String,
    val Name: String,
    val GmtOffset: Double,
    val IsDaylightSaving: Boolean,
    val NextOffsetChange: String
    )

class GeoPosition(
    val Latitude: Double,
    val Longitude: Double,
    val Elevation: Elevation
)
class Elevation(
    val Metric: Metric,
    val Imperial: Imperial
    )

class Metric(
    val Value: Double,
    val Unit: String,
    val UnitType: String
    )

class Imperial(
    val Value: Double,
    val Unit: String,
    val UnitType: String
    )