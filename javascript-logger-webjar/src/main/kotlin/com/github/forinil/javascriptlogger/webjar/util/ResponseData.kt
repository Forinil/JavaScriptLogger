package com.github.forinil.javascriptlogger.webjar.util

import com.github.forinil.javascriptlogger.webjar.util.ResponseStatus

/**
 * Created by Konrad Botor on 17.04.2017T15:56
 * in package com.github.forinil.javascriptlogger.servlet.util in project JavaScriptLogger.
 */
data class ResponseData(var result: ResponseStatus = ResponseStatus.OK,
						var message: String = "")