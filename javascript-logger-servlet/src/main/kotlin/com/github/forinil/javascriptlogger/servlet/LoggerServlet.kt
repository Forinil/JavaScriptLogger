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

    @Throws(ServletException::class, IOException::class)
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        logger.trace("doPost")
        // Parse request
        val mapper = ObjectMapper()
        mapper.readValue(req.reader, LogData::class.java)

        // Send response
        resp.contentType = "text/html"
        val out = resp.writer
        out.println("{\"result\": \"OK\"}")
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