package com.nicolas.imagegallery.session

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.provider.Settings
import android.util.Base64
import com.google.gson.Gson
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.security.NoSuchAlgorithmException
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec

class SessionManager(context: Context) {


    companion object {
        private const val AUTH_TOKEN = "auth_token"

        private const val UTF8 = "UTF-8"
        private const val CRYPTO_ALGO_CIPHER = "PBEWITHSHA256AND128BITAES-CBC-BC"
        private const val ITERATION_COUNT = 1000
        private const val KEY_LENGTH = 256

        private const val PREF_NAME = "image_gallery_app_shared"
    }


    private var sharedPref: SharedPreferences
    private var salt: ByteArray
    private var sekrit: String
    private val emptyIV = ByteArray(16)

    init {
        sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        salt = getDeviceId(context)
        sekrit = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    private fun getDeviceId(context: Context): ByteArray {
        var deviceId = ByteArray(0)

        try {
            deviceId = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            ).toByteArray(charset(UTF8))

        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return deviceId
    }

    private fun encrypt(value: String?): String {

        try {
            val bytes = value?.toByteArray(charset(UTF8)) ?: ByteArray(0)

            val keyFactory = compatSecretKeyFactory()
            val key = keyFactory.generateSecret(PBEKeySpec(sekrit.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH))
            val pbeCipher = Cipher.getInstance(CRYPTO_ALGO_CIPHER)
            pbeCipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(emptyIV))

            return String(Base64.encode(pbeCipher.doFinal(bytes), Base64.NO_WRAP), Charset.forName(UTF8))

        } catch (e: Exception) {
//            e.printStackTrace()
            throw RuntimeException()
        }
    }

    private fun decrypt(value: String?): String {
        try {
            val bytes = if (value != null) Base64.decode(value, Base64.DEFAULT) else ByteArray(0)

            val keyFactory = compatSecretKeyFactory()
            val key = keyFactory.generateSecret(PBEKeySpec(sekrit.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH))
            val pbeCipher = Cipher.getInstance(CRYPTO_ALGO_CIPHER)
            pbeCipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(emptyIV))

            return String(pbeCipher.doFinal(bytes), Charset.forName(UTF8))

        } catch (e: Exception) {
//            e.printStackTrace()
            throw RuntimeException()
        }

    }

    @Throws(NoSuchAlgorithmException::class)
    private fun compatSecretKeyFactory(): SecretKeyFactory {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Use compatibility key factory -- only uses lower 8-bits of passphrase chars
            SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1And8bit")
        } else {
            // Traditional key factory. Will use lower 8-bits of passphrase chars on
            // older Android versions (API level 18 and lower) and all available bits
            // on KitKat and newer (API level 19 and higher).
            SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        }
    }

    fun clear() {
        val editor = this.sharedPref.edit()
        editor.clear()
        editor.commit()
    }

    private fun saveValue(key: String, value: String) {
        val editor = this.sharedPref.edit()
        editor.putString(key, value)
        editor.commit()
    }

    private fun saveValue(key: String, value: Long) {
        val editor = this.sharedPref.edit()
        editor.putLong(key, value)
        editor.commit()
    }

    private fun saveValue(key: String, value: Boolean) {
        val editor = this.sharedPref.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }

    private fun deleteValue(key: String) {
        val editor = this.sharedPref.edit()
        editor.remove(key)
        editor.commit()
    }
    
    /**
     * Save token in a secure way
     */
    fun saveToken(token: String) {
        saveValue(encrypt(AUTH_TOKEN), encrypt(token))
    }

    /**
     * Get the saved token, remember first to decrypt it.
     */
    fun getToken(): String? {
        val token = this.sharedPref.getString(encrypt(AUTH_TOKEN), "")!!
        return if (token.isEmpty()) null else decrypt(token)
    }


}