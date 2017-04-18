package com.github.forinil.javascriptlogger.servlet

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.forinil.javascriptlogger.servlet.util.LogData
import com.github.forinil.javascriptlogger.servlet.util.ResponseData
import org.junit.Test
import org.mockito.Mockito
import org.slf4j.LoggerFactory
import org.slf4j.impl.SimpleLogger
import java.io.*
import java.util.*
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import kotlin.test.junit.JUnitAsserter


/**
 * Created by Konrad Botor on 16.04.2017T12:48
 * in package com.github.forinil.javascriptlogger.servlet in project JavaScriptLogger.
 */
class LoggerServletTest: Mockito() {
    private val logger = LoggerFactory.getLogger(LoggerServletTest::class.java)
    private val mapper = ObjectMapper()

    @Test
    fun testServlet() {
        val request = Mockito.mock(HttpServletRequest::class.java)
        val response = Mockito.mock(HttpServletResponse::class.java)
        val output = ByteArrayOutputStream()
        val writer = PrintWriter(output)
        val logData = LogData("INFO", "mockPage", "mockFunction", "mockMessage", null)
        val input = mapper.writeValueAsString(logData)
        val reader = BufferedReader(StringReader(input))

        logger.info("Input object: {}, input string: {}", logData, input)

        Mockito.`when`(response.writer).thenReturn(writer)
        Mockito.`when`(request.method).thenReturn("POST")
        Mockito.`when`(request.reader).thenReturn(reader)

        LoggerServlet().service(request, response)

        writer.flush()

        // Test response
        val result = output.toString("UTF-8").replace("\n", "").replace("\r", "")
        val expected = mapper.writeValueAsString(ResponseData())

        logger.info("Actual response: {}, expected response: {}", result, expected)
        JUnitAsserter.assertTrue("Wrong response", result.contains(expected))

        // Test contents of the log file
        val loggerConfiguration = Properties()
        loggerConfiguration.load(this::class.java.getResourceAsStream("/simplelogger.properties"))
        val logFile = FileReader(loggerConfiguration.getProperty(SimpleLogger.LOG_FILE_KEY))
        val logFileContents = logFile.readText()

        JUnitAsserter.assertTrue("Incorrect log file content", logFileContents.contains("mockPage::mockFunction - mockMessage"))
    }
}