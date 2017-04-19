var logger = this["javascript-logger-webjar"].com.github.forinil.javascriptlogger.webjar.Logger

function info() {
	var message = document.getElementById('messageText').value;
	logger.info('index', 'info_test', message)
}

function debug() {
	var message = document.getElementById('messageText').value;
	logger.debug('index', 'debug_test', message)
}

function warn() {
	var message = document.getElementById('messageText').value;
	logger.warn('index', 'warn_test', message)
}

function trace() {
	var message = document.getElementById('messageText').value;
	logger.trace('index', 'trace_test', message)
}

function error() {
	var message = document.getElementById('messageText').value;
	var errorCode = document.getElementById('errorCode').value;
	if (errorCode === '') {
		errorCode = null
	}
	logger.error('index', 'error_test', message, errorCode)
}

window.onerror = function (msg, url, lineNo, columnNo, error) {
    var string = msg.toLowerCase();
    var substring = "script error";
    if (string.indexOf(substring) > -1){
        logger.error('index', 'onError', 'Script Error: See Browser Console for Detail');
    } else {
        var message = [
            'Message: ' + msg,
            'URL: ' + url,
            'Line: ' + lineNo,
            'Column: ' + columnNo,
            'Error object: ' + JSON.stringify(error)
        ].join(' - ');

        logger.error('index', 'onError', message, -1);
    }

    return false;
};