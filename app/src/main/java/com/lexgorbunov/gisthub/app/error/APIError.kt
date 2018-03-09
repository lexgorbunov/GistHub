package com.lexgorbunov.gisthub.app.error

import com.google.gson.annotations.SerializedName

class APIError {

    var code: Int? = null
        private set

    @SerializedName("error")
    var message: String = ""
        private set

    // Local parameters
    var localizedMessage: String = ""
    var isLocalized: Boolean = false
}
