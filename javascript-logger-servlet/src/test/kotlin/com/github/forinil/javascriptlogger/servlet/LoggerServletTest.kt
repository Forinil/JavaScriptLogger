package com.github.forinil.javascriptlogger.servlet

import org.junit.Test
import org.mockito.Mockito
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.PrintWriter
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import kotlin.test.junit.JUnitAsserter


/**
 * Created by Konrad Botor on 16.04.2017T12:48
 * in package com.github.forinil.javascriptlogger.servlet in project JavaScriptLogger.
 */
class LoggerServletTest: Mockito() {
    val logger = LoggerFactory.getLogger(LoggerServletTest::class.java)

    @Test
    fun testServlet() {
        val request = Mockito.mock(HttpServletRequest::class.java)
        val response = Mockito.mock(HttpServletResponse::class.java)
        val output = ByteArrayOutputStream()
        val writer = PrintWriter(output)

        Mockito.`when`(response.writer).thenReturn(writer)
        Mockito.`when`(request.method).thenReturn("POST")

        LoggerServlet().service(request, response)

        writer.flush()

        val result = output.toString("UTF-8").replace("\n", "")
        val expected = "{\"result\": \"OK\"}"

        logger.info("Actual: %s%nExpected: %s".format(result, expected))
        JUnitAsserter.assertTrue("Wrong output", result.contains(expected))
    }
}