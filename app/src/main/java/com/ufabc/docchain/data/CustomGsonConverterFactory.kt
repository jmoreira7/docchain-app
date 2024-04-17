package com.ufabc.docchain.data

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class CustomGsonConverterFactory private constructor(
    private val gson: Gson
) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val delegate =
            GsonConverterFactory.create(gson).responseBodyConverter(type, annotations, retrofit)
        return CustomGsonResponseBodyConverter(delegate as Converter<ResponseBody, Any>)
    }

    private class CustomGsonResponseBodyConverter<T>(
        private val delegate: Converter<ResponseBody, T>
    ) : Converter<ResponseBody, T> {

        override fun convert(value: ResponseBody): T? {
            val fixedJson = fixJsonFormat(value.string())
            return delegate.convert(ResponseBody.create(value.contentType(), fixedJson))
        }

        private fun fixJsonFormat(json: String): String {
            return json.replace("=", ":").replace(Regex(":([^,}]*)"), ": \"$1\"")
        }
    }

    companion object {
        fun create(gson: Gson): CustomGsonConverterFactory {
            return CustomGsonConverterFactory(gson)
        }
    }
}