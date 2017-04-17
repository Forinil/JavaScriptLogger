package com.github.forinil.javascriptlogger.servlet

import org.slf4j.LoggerFactory
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.forinil.javascriptlogger.servlet.util.LogData
import com.github.forinil.javascriptlogger.servlet.util.ResponseData
import com.github.forinil.javascriptlogger.servlet.util.ResponseStatus
import org.slf4j.event.Level


/**
 * Created by Konrad Botor on 15.04.2017T15:42
 * in package com.github.forinil.javascriptlogger.servlet in project JavaScriptLogger.
 */
@WebServlet(name = "LoggerServlet",
        displayName = "LoggerServlet",
        description = "Logger Servlet",
        loadOnStartup = 1,
        urlPatterns = arrayOf("/logger"))
open class LoggerServlet: HttpServlet() {
    private val logger = LoggerFactory.getLogger(LoggerServlet::class.java)
    private val mapper = ObjectMapper()
    private val messageTemplate = "{}::{} - {}"
    private val errorTemplate = messageTemplate + " - {}"

    @Throws(ServletException::class, IOException::class)
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        logger.trace("doPost")

        // Parse request
        val request = mapper.readValue(req.reader, LogData::class.java)
        val level: Level
        try {
            level = getLevel(request)
            logger.trace("Data received: {}", request)
        } catch(e: Exception) {
            logger.error("Unknown log level", e)
            sendResponse(resp, ResponseData(ResponseStatus.FAILURE, "Unknown log level"))
            return
        }

        when (level) {
            Level.INFO -> logger.info(messageTemplate, request.page, request.function, request.message)
            Level.TRACE -> logger.trace(messageTemplate, request.page, request.function, request.message)
            Level.DEBUG -> logger.debug(messageTemplate, request.page, request.function, request.message)
            Level.WARN -> logger.warn(messageTemplate, request.page, request.function, request.message)
            Level.ERROR -> logger.error(errorTemplate, request.page, request.function, request.message, request.errorCode)
            else -> {
                logger.error("Unknown log level in request: {}", request)
                sendResponse(resp, ResponseData(ResponseStatus.FAILURE, "Unknown log level"))
                return
            }
        }

        // Send response
        sendResponse(resp, ResponseData())
    }

    private fun sendResponse(resp: HttpServletResponse, resonseData: ResponseData) {
        resp.contentType = "text/html"
        val out = resp.writer
        out.println(mapper.writeValueAsString(resonseData))
    }

    private fun getLevel(logData: LogData): Level {
        when (logData.level) {
            Level.ERROR.toString() -> return Level.ERROR
            Level.WARN.toString() -> return Level.WARN
            Level.INFO.toString() -> return Level.INFO
            Level.DEBUG.toString() -> return Level.DEBUG
            Level.TRACE.toString() -> return Level.TRACE
            else -> throw IllegalArgumentException("Unknown log level: %s".format(logData.level))
        }
    }
}