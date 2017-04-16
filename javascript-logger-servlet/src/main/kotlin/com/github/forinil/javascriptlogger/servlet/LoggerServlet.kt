package com.github.forinil.javascriptlogger.servlet

import org.slf4j.LoggerFactory
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by Konrad Botor on 15.04.2017T15:42
 * in package com.github.forinil.javascriptlogger.servlet in project JavaScriptLogger.
 */
@WebServlet(name = "LoggerServlet",
        displayName = "LoggerServlet",
        description = "Logger Servlet",
        loadOnStartup = 1,
        urlPatterns = arrayOf("/logger/*"))
open class LoggerServlet: HttpServlet() {
    private val logger = LoggerFactory.getLogger(LoggerServlet::class.java)

    @Throws(ServletException::class, IOException::class)
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        logger.trace("doPost")
        resp.contentType = "text/html"

        // Actual logic goes here.
        val out = resp.writer
        out.println("{\"result\": \"OK\"}")
    }
}