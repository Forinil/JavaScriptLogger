package com.github.forinil.javascriptlogger.testapp

import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import org.slf4j.LoggerFactory
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by Konrad Botor on 18.04.2017T15:01
 * in package com.github.forinil.javascriptlogger.testapp in project JavaScriptLogger.
 */
@WebServlet(name = "TestServlet",
		displayName = "TestServlet",
		description = "TestServlet",
		loadOnStartup = 2,
		urlPatterns = arrayOf("/"))
open class TestServlet: HttpServlet() {
	private val logger = LoggerFactory.getLogger(TestServlet::class.java)

	@Throws(ServletException::class)
	override fun init() {
		logger.trace("init")
		super.init()
	}

	@Throws(ServletException::class, IOException::class)
	override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
		logger.info("doGet")

		//Generate webpage
		val webPage = StringBuilder().appendHTML(true).html {
			head {
				title {
					+"JavaScript Logger Test Application"
				}
				script(ScriptType.textJavaScript, "webjars/javascript-logger-webjar/1.0-SNAPSHOT/js/lib/kotlin.js")
				script(ScriptType.textJavaScript, "webjars/javascript-logger-webjar/1.0-SNAPSHOT/js/javascript-logger-webjar.js")
				script(ScriptType.textJavaScript, "static/js/logger.js")
			}
			body {
				div {
					span {
						onClick = "info()"
						+"INFO test"
					}
				}
			}
		}

		// Send response
		resp.contentType = "text/html"
		val out = resp.writer
		out.println(webPage.toString())
	}
}