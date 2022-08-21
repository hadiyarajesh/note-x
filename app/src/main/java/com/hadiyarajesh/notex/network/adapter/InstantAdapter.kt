package com.hadiyarajesh.notex.network.adapter

import com.squareup.moshi.*
import java.time.Instant

class InstantAdapter : JsonAdapter<Instant>() {
    @FromJson
    override fun fromJson(reader: JsonReader): Instant? {
        val string = reader.nextString()
        return Instant.parse(string)
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Instant?) {
        val string = value?.toString()
        writer.value(string)
    }
}
