package com.rakesh.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Args {
	
	private Long uid;
	
	private String token;
	
	@JsonProperty("api-token")
	private String apiToken;
	
	@JsonProperty("json-strict-mode")
	private boolean jsonStrictMode;
	
	@JsonProperty("json-verbose-response")
	private boolean jsonVerboseResponse;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
	public String getApiToken() {
		return apiToken;
	}

	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}

	public boolean isJsonStrictMode() {
		return jsonStrictMode;
	}

	public void setJsonStrictMode(boolean jsonStrictMode) {
		this.jsonStrictMode = jsonStrictMode;
	}

	public boolean isJsonVerboseResponse() {
		return jsonVerboseResponse;
	}

	public void setJsonVerboseResponse(boolean jsonVerboseResponse) {
		this.jsonVerboseResponse = jsonVerboseResponse;
	}
}
