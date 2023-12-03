package com.example.papb_tubes

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherResponse(

	@field:SerializedName("visibility")
	val visibility: Int,

	@field:SerializedName("timezone")
	val timezone: Int,

	@field:SerializedName("main")
	val main: Main,

	@field:SerializedName("clouds")
	val clouds: Clouds,

	@field:SerializedName("sys")
	val sys: Sys,

	@field:SerializedName("dt")
	val dt: Int,

	@field:SerializedName("coord")
	val coord: Coord,

	@field:SerializedName("weather")
	val weather: List<WeatherItem>,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("cod")
	val cod: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("base")
	val base: String,

	@field:SerializedName("wind")
	val wind: Wind
):Parcelable

@Parcelize
data class Coord(

	@field:SerializedName("lon")
	val lon: Int,

	@field:SerializedName("lat")
	val lat: Int
):Parcelable

@Parcelize
data class Sys(

	@field:SerializedName("country")
	val country: String,

	@field:SerializedName("sunrise")
	val sunrise: Int,

	@field:SerializedName("sunset")
	val sunset: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("type")
	val type: Int
):Parcelable

@Parcelize
data class WeatherItem(

	@field:SerializedName("icon")
	val icon: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("main")
	val main: String,

	@field:SerializedName("id")
	val id: Int
):Parcelable

@Parcelize
data class Wind(

	@field:SerializedName("deg")
	val deg: Int,

	@field:SerializedName("speed")
	val speed: Int,

	@field:SerializedName("gust")
	val gust: Int
):Parcelable

@Parcelize
data class Main(

	@field:SerializedName("temp")
	val temp: Float,

	@field:SerializedName("temp_min")
	val tempMin: Float,

	@field:SerializedName("grnd_level")
	val grndLevel: Int,

	@field:SerializedName("humidity")
	val humidity: Int,

	@field:SerializedName("pressure")
	val pressure: Int,

	@field:SerializedName("sea_level")
	val seaLevel: Int,

	@field:SerializedName("feels_like")
	val feelsLike: Float,

	@field:SerializedName("temp_max")
	val tempMax: Float
):Parcelable

@Parcelize
data class Clouds(

	@field:SerializedName("all")
	val all: Int
):Parcelable
