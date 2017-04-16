package com.github.forinil.javascriptlogger.servlet

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.forinil.javascriptlogger.servlet.util.LogData
import org.junit.Test
import org.mockito.Mockito
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.PrintWriter
import java.io.StringReader
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import kotlin.test.junit.JUnitAsserter


/**
 * Created by Konrad Botor on 16.04.2017T12:48
 * in package com.github.forinil.javascriptlogger.servlet in project JavaScriptLogger.
 */
class LoggerServletTest: Mockito() {
    private val logger = LoggerFactory.getLogger(LoggerServletTest::class.java)

    @Test
    fun testServlet() {
        val request = Mockito.mock(HttpServletRequest::class.java)
        val response = Mockito.mock(HttpServletResponse::class.java)
        val output = ByteArrayOutputStream()
        val writer = PrintWriter(output)
        val logData = LogData("INFO", "test", "mock", "mock test", null)
        val mapper = ObjectMapper()
        val input = mapper.writeValueAsString(logData)
        val reader = BufferedReader(StringReader(input))

        logger.info("Input object: {}, input string: {}", logData, input)

        Mockito.`when`(response.writer).thenReturn(writer)
        Mockito.`when`(request.method).thenReturn("POST")
        Mockito.`when`(request.reader).thenReturn(reader)

        LoggerServlet().service(request, response)

        writer.flush()

        val result = output.toString("UTF-8").replace("\n", "").replace("\r", "")
        val expected = "{\"result\": \"OK\"}"

        logger.info("Actual: {}, expected: {}", result, expected)
        JUnitAsserter.assertTrue("Wrong output", result.contains(expected))
    }
}