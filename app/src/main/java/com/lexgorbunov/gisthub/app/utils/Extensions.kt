package com.lexgorbunov.gisthub.app.utils

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.lexgorbunov.gisthub.app.error.APIError
import okhttp3.ResponseBody
import java.io.IOException

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun tryParseError(context: Context, throwable: Throwable?): APIError? {
    if (throwable is HttpException) {
        val body: ResponseBody? = throwable.response().errorBody()
        if (body != null) {
            val adapter: TypeAdapter<APIError> = Gson().getAdapter(APIError::class.java)
            try {
                val apiError: APIError = adapter.fromJson(body.string())
                if (!TextUtils.isEmpty(apiError.message) && TextUtils.isEmpty(apiError.localizedMessage)) {
                    val resId = context.resources.getIdentifier(apiError.message, "string", context.packageName)
                    if (resId != 0) {
                        apiError.localizedMessage = context.resources.getString(resId)
                        apiError.isLocalized = true
                    } else {
                        apiError.localizedMessage = apiError.message
                        Log.w("MISSING TRANSLATION", apiError.message)
                    }
                }
                return apiError
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    return null
}
