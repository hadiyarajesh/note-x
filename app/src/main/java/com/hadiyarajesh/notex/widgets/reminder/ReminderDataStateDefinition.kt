package com.hadiyarajesh.notex.widgets.reminder

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.glance.state.GlanceStateDefinition
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import java.io.File
import java.io.InputStream
import java.io.OutputStream

object ReminderDataStateDefinition : GlanceStateDefinition<ReminderData> {
    private const val DATA_STORE_FILENAME = "reminderDataWidget"

    private val Context.datastore by dataStore(DATA_STORE_FILENAME, ReminderDataSerializer)

    override suspend fun getDataStore(context: Context, fileKey: String): DataStore<ReminderData> {
        return context.datastore
    }

    override fun getLocation(context: Context, fileKey: String): File {
        return context.dataStoreFile(DATA_STORE_FILENAME)
    }

    object ReminderDataSerializer : Serializer<ReminderData> {
        val moshi = Moshi.Builder().add(
            PolymorphicJsonAdapterFactory.of(ReminderData::class.java, "state")
                .withSubtype(ReminderData.Available::class.java, "available")
                .withSubtype(ReminderData.Unavailable::class.java, "unavailable")
                .withDefaultValue(ReminderData.Loading)
        ).build()
        val adapter = moshi.adapter(ReminderData::class.java)
        override val defaultValue: ReminderData
            get() = ReminderData.Loading

        override suspend fun readFrom(input: InputStream): ReminderData = try {
            adapter.fromJson(input.readBytes().decodeToString()) ?: ReminderData.Unavailable(
                "Unknown Error"
            )
        } catch (e:JsonDataException) {
            throw CorruptionException("Could not read data: ${e.message}")
        }

        override suspend fun writeTo(t: ReminderData, output: OutputStream) {
            output.use {
                it.write(adapter.toJson(t).encodeToByteArray())
            }
        }

    }

}