package com.rakesh.domain;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class Transaction {

	private BigDecimal amount;
	
	@JsonProperty("is-pending")
	private boolean isPending;
	
	@JsonProperty("aggregation-time")
	private DateTime aggregationTime;
	
	@JsonProperty("account-id")
	private String accountId;
	
	@JsonProperty("clear-date")
	private DateTime clearDate;
	
	@JsonProperty("transaction-id")
	private String transactionId;
	
	@JsonProperty("raw-merchant")
	private String rawMerchant;
	
	@JsonProperty("categorization")
	private String categorization;
	
	@JsonProperty("merchant")
	private String merchant;
	
	@JsonProperty("transaction-time")
	private DateTime transactionTime;
	
	@JsonProperty("payee-name-only-for-testing")
	private String payeeName;

	@JsonProperty("memo-only-for-testing")
	private String testingMemo;
	
	public String getTestingMemo() {
		return testingMemo;
	}

	public void setTestingMemo(String testingMemo) {
		this.testingMemo = testingMemo;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public BigDecimal getAmount() {
		return amount.divide(new BigDecimal(10000));
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public boolean isPending() {
		return isPending;
	}

	public void setPending(boolean isPending) {
		this.isPending = isPending;
	}

	public DateTime getAggregationTime() {
		return aggregationTime;
	}

	public void setAggregationTime(DateTime aggregationTime) {
		this.aggregationTime = aggregationTime;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public DateTime getClearDate() {
		return clearDate;
	}

	public void setClearDate(DateTime clearDate) {
		this.clearDate = clearDate;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getRawMerchant() {
		return rawMerchant;
	}

	public void setRawMerchant(String rawMerchant) {
		this.rawMerchant = rawMerchant;
	}

	public String getCategorization() {
		return categorization;
	}

	public void setCategorization(String categorization) {
		this.categorization = categorization;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public DateTime getTransactionTime() {
		return transactionTime;
	}

	@JsonDeserialize(using = DateTimeDeserializer.class)
	public void setTransactionTime(DateTime transactionTime) {
		this.transactionTime = transactionTime;
	}
}
