package me.pixeldev.alya.jdk.http;

import java.util.HashMap;
import java.util.Map;

public class HttpException extends Exception {

	private static final Map<Integer, String> STATUS_NAMES = new HashMap<>();

	static {
		// 400: Bad Request. Usually thrown when the request was not given as expected
		STATUS_NAMES.put(HttpStatusCodes.BAD_REQUEST, "Bad Request");
		// 403: Unauthorized. An API request was not allowed
		STATUS_NAMES.put(HttpStatusCodes.UNAUTHORIZED, "Unauthorized");
		// 404: Not Found. Something was referenced through the API that does not exist
		STATUS_NAMES.put(HttpStatusCodes.NOT_FOUND, "Not Found");
		// 500: Internal Server Error. Usually thrown when the server fails doing the requested task
		STATUS_NAMES.put(HttpStatusCodes.INTERNAL_SERVER_ERROR, "Internal Server Error");
	}

	/** The error response, it's usually in JSON */
	private final String response;

	/** The response status code */
	private final int statusCode;

	public HttpException(
			String response,
			int statusCode,
			Throwable cause,
			boolean writableStackTrace
	) {
		super(response, cause, false, writableStackTrace);
		this.response = response;
		this.statusCode = statusCode;
	}

	public HttpException(
			String response,
			int statusCode,
			boolean writableStackTrace
	) {
		this(response, statusCode, null, writableStackTrace);
	}

	public String getStatusName() {
		return STATUS_NAMES.getOrDefault(statusCode, statusCode + "");
	}

	public String getResponse() {
		return response;
	}

	public int getStatusCode() {
		return statusCode;
	}

}