package me.pixeldev.alya.jdk.http;

import java.util.Collections;
import java.util.Map;

public class RequestOptions {

	private final Method method;
	private final Map<String, String> headers;
	private final String body;
	private final String query;

	public RequestOptions(
			Method method,
			Map<String, String> headers,
			String body,
			String query
	) {
		this.method = method;
		this.headers = headers;
		this.body = body;
		this.query = query;
	}

	public RequestOptions(Method method, String body) {
		this(method, Collections.emptyMap(), body, "");
	}

	/**
	 * Will return type to be used at the request
	 * @return request type
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * Headers to append at request
	 * @return request headers
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * Body to be used at the request
	 * @return body of the request
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Query params to append after type
	 * @return query type
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * Request posibilities
	 */
	public enum Method {
		GET,
		POST,
		DELETE,
		PUT
	}

}