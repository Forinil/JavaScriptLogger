package com.github.forinil.javascriptlogger.webjar

import com.github.forinil.javascriptlogger.webjar.util.LogData
import com.github.forinil.javascriptlogger.webjar.util.ResponseData
import com.github.forinil.javascriptlogger.webjar.util.ResponseStatus
import org.w3c.dom.events.Event
import org.w3c.xhr.JSON
import org.w3c.xhr.XMLHttpRequest
import org.w3c.xhr.XMLHttpRequestResponseType

import kotlin.js.JSON.parse

/**
* Created by Konrad Botor on 15.04.2017T15:40
* in package com.github.forinil.javascriptlogger.webjar in project JavaScriptLogger.
*/
object Logger {
    @JsName("info")
    fun info(page: String, function: String, message: String) {
        console.info("[INFO] $page::$function - $message")
        val logData = LogData("INFO", page, function, message)
        sendPostRequest(logData)
    }

	@JsName("debug")
    fun debug(page: String, function: String, message: String) {
        console.info("[DEBUG] $page::$function - $message")
        val logData = LogData("DEBUG", page, function, message)
        sendPostRequest(logData)
    }

	@JsName("error")
    fun error(page: String, function: String, message: String, errorCode: Int) {
        console.error("[ERROR] $page::$function - $message - $errorCode")
        val logData = LogData("ERROR", page, function, message, errorCode)
        sendPostRequest(logData)
    }

	@JsName("warn")
    fun warn(page: String, function: String, message: String) {
        console.warn("[WARN] $page::$function - $message")
        val logData = LogData("WARN", page, function, message)
        sendPostRequest(logData)
    }

	@JsName("trace")
    fun trace(page: String, function: String, message: String) {
        console.info("[TRACE] $page::$function - $message")
        val logData = LogData("TRACE", page, function, message)
        sendPostRequest(logData)
    }

    private fun sendPostRequest(logData: LogData) {
        val xhttp = XMLHttpRequest()
        xhttp.open("POST", "logger", true)
        xhttp.onreadystatechange = fun(event: Event) {
            if (xhttp.responseType == XMLHttpRequestResponseType.JSON) {
                val response: ResponseData = parse(text = xhttp.responseText)
                if (response.result != ResponseStatus.OK) {
                    console.error(response.message)
                }
            }
        }
        xhttp.send(logData.toJSON())
    }
}
