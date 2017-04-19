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
		val webPage = generateWebPage()

		// Send response
		resp.contentType = "text/html"
		val out = resp.writer
		out.println(webPage)
	}

	private fun generateWebPage(): String {
		return StringBuilder().appendHTML(true).html {
			head {
				title {
					+"JavaScript Logger Test Application"
				}
				link("webjars/bootstrap/3.3.7-1/css/bootstrap.min.css", "stylesheet")
				script(ScriptType.textJavaScript, "webjars/javascript-logger-webjar/1.0-SNAPSHOT/js/lib/kotlin.js")
				script(ScriptType.textJavaScript, "webjars/javascript-logger-webjar/1.0-SNAPSHOT/js/javascript-logger-webjar.js")
				script(ScriptType.textJavaScript, "static/js/logger.js")
			}
			body {
				div {
					classes = setOf("container")
					span {
						style = "display: none"
						onClick = "info()"
						+"INFO test"
					}
					div {
						classes = setOf("panel", "panel-default")
						div {
							classes = setOf("panel-body")
							form {
								action = ""
								id = "loggerTestForm"
								classes = setOf("form-horizontal")
								div {
									classes = setOf("form-group")
									label {
										for_ = "messageText"
										classes = setOf("col-sm-2", "control-label")
										+"Message"
									}
									div {
										classes = setOf("col-sm-10")
										input {
											type = InputType.text
											id = "messageText"
											classes = setOf("form-control")
										}
									}
								}
								div {
									classes = setOf("form-group")
									label {
										for_ = "errorCode"
										classes = setOf("col-sm-2", "control-label")
										+"Error code"
									}
									div {
										classes = setOf("col-sm-10")
										input {
											type = InputType.text
											id = "errorCode"
											classes = setOf("form-control")
										}
									}
								}
								div {
									classes = setOf("form-group")
									div {
										classes = setOf("col-sm-offset-2", "col-sm-10")
										button {
											type = ButtonType.button
											classes = setOf("btn", "btn-info")
											style = "margin: 5px; padding: 6px 12px;"
											onClick = "info()"
											+"INFO"
										}
										button {
											type = ButtonType.button
											classes = setOf("btn", "btn-primary")
											style = "margin: 5px; adding: 6px 12px;"
											onClick = "debug()"
											+"DEBUG"
										}
										button {
											type = ButtonType.button
											classes = setOf("btn", "btn-warning")
											style = "margin: 5px; padding: 6px 12px;"
											onClick = "warn()"
											+"WARN"
										}
										button {
											type = ButtonType.button
											classes = setOf("btn", "btn-default")
											style = "margin: 5px; padding: 6px 12px;"
											onClick = "trace()"
											+"TRACE"
										}
										button {
											type = ButtonType.button
											classes = setOf("btn", "btn-danger")
											style = "margin: 5px; padding: 6px 12px;"
											onClick = "error()"
											+"ERROR"
										}
									}
								}
							}
						}
					}
				}
			}
		}.toString()
	}
}