package com.example.gittest

import android.util.Log
import okhttp3.Headers
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okio.Buffer
import okio.BufferedSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.EOFException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 *
 * 请描述类的作用
 * @author linxiao date: 2022年4月12日, 0012 13:53
 */
class RetrofitManager {

    companion object {
        private const val TIME_OUT = 5
        private val client: OkHttpClient
            get() {
                return OkHttpClient.Builder().apply {
                    connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                    addInterceptor {chain->
                        val request = chain.request()
                        if(request.method=="GET"){
                             fun printParms(): StringBuilder {
                                val split = request.url.toString().split("?")
                                val builder = StringBuilder().apply {
                                    append("当前请求的URL--${split[0]}\n")
                                    append("当前请求的参数如下:\n")
                                }
                                split[1].split("&").forEach {
                                    val split1 = it.split("=")
                                    builder.append("参数名:${split1[0]}--参数值:${split1[1]}\n")
                                }
                                return builder
                            }
                            Log.d("000",printParms().toString())
                        }
                        val proceed = chain.proceed(request)
                        proceed
                    }
                    addInterceptor {chain->
                        val request = chain.request()
                        val response = chain.proceed(request)
                        val responseBody = response.body
                        val contentLength = responseBody!!.contentLength()
                        if (!bodyEncoded(response.headers)) {
                            val source: BufferedSource = responseBody.source()
                            source.request(Long.MAX_VALUE) // Buffer the entire body.
                            val buffer: Buffer = source.buffer
                            val charset: Charset = charset("UTF-8")
                            val contentType: MediaType? = responseBody.contentType()
                            if (contentType != null) {
                                if (!isPlaintext(buffer)) {
                                    response
                                }
                                if (contentLength != 0L) {
                                    val result = buffer.clone().readString(charset)
                                    //   if (request.method == "GET") { } //打印参数信息，如果要针对某个请求方式的话，那就加上这个if
                                    val builder = StringBuilder().apply {
                                        append("URL-GET-${response.request.url}返回\n")
                                    }
                                    builder.append(result)
                                    Log.d("000",builder.toString())

                                }

                            }
                        }
                        response
                    }
                }.build()
            }

        fun <S> getServices(service: Class<S>, baseUrl: String):S{
         return   Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(baseUrl)
                .build()
                .create(service)
        }
        private fun isPlaintext(buffer: Buffer): Boolean {
            try {
                val prefix = Buffer()
                val byteCount = if (buffer.size < 64) {
                    buffer.size
                } else {
                    64
                }
                buffer.copyTo(prefix, 0, byteCount)
                for (i in 0..16) {
                    if (prefix.exhausted()) {
                        break
                    }
                    val codePoint = prefix.readUtf8CodePoint()
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false
                    }
                }
                return true
            } catch (e: EOFException) {
                return false // Truncated UTF-8 sequence.
            }
        }

        private fun bodyEncoded(headers: Headers): Boolean {
            val contentEncoding = headers["Content-Encoding"]
            return contentEncoding != null && contentEncoding.toLowerCase() != "identity"
        }
    }
}