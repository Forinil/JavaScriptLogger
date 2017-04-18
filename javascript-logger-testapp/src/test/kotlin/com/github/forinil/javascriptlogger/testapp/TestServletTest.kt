package com.github.forinil.javascriptlogger.testapp

import org.junit.Test
import org.mockito.Mockito
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.PrintWriter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.test.junit.JUnitAsserter

/**
 * Created by Konrad Botor on 18.04.2017T16:44
 * in package com.github.forinil.javascriptlogger.testapp in project JavaScriptLogger.
 */
class TestServletTest: Mockito() {
	private val logger = LoggerFactory.getLogger(TestServletTest::class.java)

	@Test
	fun testServlet() {
		val request = Mockito.mock(HttpServletRequest::class.java)
		val response = Mockito.mock(HttpServletResponse::class.java)
		val output = ByteArrayOutputStream()
		val writer = PrintWriter(output)

		Mockito.`when`(response.writer).thenReturn(writer)
		Mockito.`when`(request.method).thenReturn("GET")

		TestServlet().service(request, response)

		writer.flush()

		val result = output.toString("UTF-8")
		logger.info("Servlet response: {}", result)

		JUnitAsserter.assertTrue("No <script> tag", result.contains("script"))
		JUnitAsserter.assertTrue("No <script> tag for 'kotlin.js' library", result.contains("kotlin.js"))
		JUnitAsserter.assertTrue("No <script> tag for 'javascript-logger-webjar.js' library", result.contains("javascript-logger-webjar.js"))
		JUnitAsserter.assertTrue("No <span> tag", result.contains("span"))
		JUnitAsserter.assertTrue("Wrong 'onClick' attribute", result.contains("info()"))
	}
}