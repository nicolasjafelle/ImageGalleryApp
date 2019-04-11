package com.nicolas.imagegallery.api

import com.nicolas.imagegallery.BuildConfig
import com.nicolas.imagegallery.ImageGalleryApplication
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by nicolas on 11/9/17.
 */
class ApiClient private constructor() {

    companion object {

        private const val TIMEOUT_MILLIS: Long = 1000 * 60 * 10

        private val TIMEOUT_UNIT = TimeUnit.MILLISECONDS

        private const val DISK_CACHE_SIZE: Long = 10 * 1024 * 1024

        private lateinit var retrofit: Retrofit

        val instance: ApiService by lazy {
            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig::HOST.get())
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(ApiService::class.java)
        }

        private fun getOkHttpClient(): OkHttpClient {

            val builder = OkHttpClient.Builder()
            val loggingInterceptor = HttpLoggingInterceptor()

            if (BuildConfig::DEBUG.get()) {
                loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
            }

            builder.retryOnConnectionFailure(true)
            builder.connectTimeout(TIMEOUT_MILLIS, TIMEOUT_UNIT)
            builder.readTimeout(TIMEOUT_MILLIS, TIMEOUT_UNIT)
            builder.writeTimeout(TIMEOUT_MILLIS, TIMEOUT_UNIT)
            builder.addInterceptor(loggingInterceptor)
            builder.addInterceptor(HeaderInterceptor())
            builder.cache(getCache())

//            if (BuildConfig.DEBUG) { //TODO UNCOMMENT THIS IF WE HAVE UNTRUSTED CERTIFICATES
//                // Create a trust manager that does not validate certificate chains
//                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
//                    @Throws(CertificateException::class)
//                    override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
//                    }
//
//                    @Throws(CertificateException::class)
//                    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
//                    }
//
//                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
//                        return arrayOf()
//                    }
//                })
//
//                // Install the all-trusting trust manager
//                val sslContext = SSLContext.getInstance("SSL")
//                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
//                // Create an ssl socket factory with our all-trusting manager
//                val sslSocketFactory = sslContext.socketFactory
//
//                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
//                builder.hostnameVerifier { hostname, session -> true }
//            }

            return builder.build()
        }

        private fun getCache(): Cache {
            val file = File(ImageGalleryApplication.instance.cacheDir, "cache")
            return Cache(file, DISK_CACHE_SIZE)
        }

    }

}