package com.github.forinil.javascriptlogger.servlet.util

/**
 * Created by Konrad Botor on 16.04.2017T13:50
 * in package com.github.forinil.javascriptlogger.servlet.util in project JavaScriptLogger.
 */
data class LogData (var level: String = "INFO",
                    var page: String = "",
                    var function: String = "",
                    var message: String = "",
                    var errorCode: Int? = null)