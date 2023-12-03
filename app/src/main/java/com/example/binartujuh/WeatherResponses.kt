package com.example.binartujuh

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherResponses(

	@field:SerializedName("data")
	val data: String,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
) : Parcelable

@Parcelize
data class AreasItem(

	@field:SerializedName("coordinate")
	val coordinate: String,

	@field:SerializedName("level")
	val level: String,

	@field:SerializedName("latitude")
	val latitude: String,

	@field:SerializedName("domain")
	val domain: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("region")
	val region: String,

	@field:SerializedName("longitude")
	val longitude: String,

	@field:SerializedName("tags")
	val tags: String,

	@field:SerializedName("params")
	val params: String
):Parcelable

@Parcelize
data class TimesItem(

	@field:SerializedName("datetime")
	val datetime: String,

	@field:SerializedName("h")
	val h: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("value")
	val value: String,

	@field:SerializedName("day")
	val day: String,

	@field:SerializedName("celcius")
	val celcius: String,

	@field:SerializedName("fahrenheit")
	val fahrenheit: String,

	@field:SerializedName("code")
	val code: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("deg")
	val deg: String,

	@field:SerializedName("card")
	val card: String,

	@field:SerializedName("sexa")
	val sexa: String,

	@field:SerializedName("kph")
	val kph: String,

	@field:SerializedName("mph")
	val mph: String,

	@field:SerializedName("ms")
	val ms: String,

	@field:SerializedName("kt")
	val kt: String
):Parcelable

@Parcelize
data class ParamsItem(

	@field:SerializedName("times")
	val times: List<TimesItem>,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("type")
	val type: String
):Parcelable

@Parcelize
data class Issue(

	@field:SerializedName("month")
	val month: String,

	@field:SerializedName("hour")
	val hour: String,

	@field:SerializedName("year")
	val year: String,

	@field:SerializedName("day")
	val day: String,

	@field:SerializedName("timestamp")
	val timestamp: String,

	@field:SerializedName("minute")
	val minute: String,

	@field:SerializedName("second")
	val second: String
):Parcelable

@Parcelize
data class Data(

	@field:SerializedName("issue")
	val issue: Issue,

	@field:SerializedName("areas")
	val areas: List<AreasItem>
):Parcelable
