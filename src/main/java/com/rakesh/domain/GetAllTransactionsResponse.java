package com.rakesh.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAllTransactionsResponse {
	
	@JsonProperty("error")
	private String errorMessage;
	
	private List<Transaction> transactions;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

}
