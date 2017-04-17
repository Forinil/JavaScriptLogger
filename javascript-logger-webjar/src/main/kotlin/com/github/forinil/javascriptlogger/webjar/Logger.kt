package com.github.forinil.javascriptlogger.webjar

import com.github.forinil.javascriptlogger.servlet.util.LogData
import jquery.jq
import org.w3c.dom.events.Event
import org.w3c.xhr.XMLHttpRequest
import kotlin.browser.document
import kotlin.browser.window

/**
* Created by Konrad Botor on 15.04.2017T15:40
* in package com.github.forinil.javascriptlogger.webjar in project JavaScriptLogger.
*/
object Logger {
    fun info(page: String, function: String, message: String) {
        console.info("[INFO] $page::$function - $message")
        val logData = LogData(page, function, message)
        sendPostRequest(logData)
    }

    fun debug(page: String, function: String, message: String) {
        console.info("[DEBUG] $page::$function - $message")
        val logData = LogData(page, function, message)
        sendPostRequest(logData)
    }

    fun error(page: String, function: String, message: String, errorCode: String) {
        console.error("[ERROR] $page::$function - $message - $errorCode")
        val logData = LogData(page, function, message, errorCode)
        sendPostRequest(logData)
    }

    fun warn(page: String, function: String, message: String) {
        console.warn("[WARN] $page::$function - $message")
        val logData = LogData(page, function, message)
        sendPostRequest(logData)
    }

    fun trace(page: String, function: String, message: String) {
        console.info("[TRACE] $page::$function - $message")
        val logData = LogData(page, function, message)
        sendPostRequest(logData)
    }

    private fun sendPostRequest(logData: LogData) {
        val xhttp = XMLHttpRequest()
        xhttp.open("POST", "/logger", true)
        xhttp.send(logData)
    }
}
