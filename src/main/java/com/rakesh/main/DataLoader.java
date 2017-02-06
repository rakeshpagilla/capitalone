package com.rakesh.main;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.rakesh.client.ServiceClient;
import com.rakesh.domain.Args;
import com.rakesh.domain.GetAllTransactionsRequest;
import com.rakesh.domain.GetAllTransactionsResponse;
import com.rakesh.domain.Transaction;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

public class DataLoader{


	public static void main(String[] args) throws IOException {

		Options opts = buildOptions();

		CommandLineParser parser = new DefaultParser();

		CommandLine cmd = null;
		ServiceClient serviceClient = new ServiceClient("https://2016.api.levelmoney.com/api/v2/core/get-all-transactions");
		GetAllTransactionsResponse response = serviceClient.getAllTransactions(createRequest());
		
		try {
			cmd = parser.parse(opts, args);

			if (cmd.hasOption("-donuts")) {
				displayTransactionsData(response, true);
			}else if (cmd.hasOption("-ignorecc")) {
				displayTransactionsWithoutCreditCardPayments(response);
			}else{
				displayTransactionsData(response, false);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			//log.error("Failed to parse comand line properties", e);
		}
	}

	static Options buildOptions() {
		Options o = new Options();
		o.addOption("donuts", "donuts", false, "Here you can set parameter to Filter Donuts.");
		o.addOption("ignorecc", "ignorecard", false, "Here you can set parameter to filter credit card transaction .");
		return o;
	}
	
	
	public static List<Transaction> sortTransactionsByDate(List<Transaction> transactions) {
		Collections.sort(transactions, new Comparator<Transaction>() {

			public int compare(Transaction t1, Transaction t2) {
				return t1.getTransactionTime().compareTo(t2.getTransactionTime());
			}
		});
		return transactions;
	}
	public static void displayTransactionsData(GetAllTransactionsResponse response, boolean filterByDonuts){
		
		List<Transaction> transactions = sortTransactionsByDate(response.getTransactions());
		
		int size = transactions.size();
		int months = 0 ;
		BigDecimal totalEarnings = new BigDecimal(0);
		BigDecimal totalSpent = new BigDecimal(0);
		
		for(int i=0 ; i < size ; i++)	{
			Transaction transaction = transactions.get(i);
			int month = transaction.getTransactionTime().getMonthOfYear();
			int year = transaction.getTransactionTime().getYear();
			BigDecimal amount = transactions.get(i).getAmount();
			BigDecimal spent= new BigDecimal(0);
			BigDecimal income = new BigDecimal(0);
			if(amount.compareTo(BigDecimal.ZERO) >0){
				income = income.add(amount);
			}else{
				if(!(filterByDonuts && (StringUtils.containsIgnoreCase(transaction.getMerchant(), "Dunkin") || StringUtils.containsIgnoreCase(transaction.getMerchant(), "Krispy"))))
				spent = spent.add(amount.abs());
			}
			
			while(i < size-1 && year == transactions.get(i+1).getTransactionTime().getYear() && month == transactions.get(i+1).getTransactionTime().getMonthOfYear()){
				if(transactions.get(i+1).getAmount().compareTo(BigDecimal.ZERO) >0){
					income = income.add(transactions.get(i+1).getAmount());
				}else{
					if(!(filterByDonuts && (StringUtils.containsIgnoreCase(transactions.get(i+1).getMerchant(), "Dunkin") || StringUtils.containsIgnoreCase(transactions.get(i+1).getMerchant(), "Krispy"))))
					spent = spent.add(transactions.get(i+1).getAmount().abs());
				}
				i++;
			}
			months++;
			totalEarnings = totalEarnings.add(income);
			totalSpent =  totalSpent.add(spent);
			System.out.println("\""+ transaction.getTransactionTime().getYear()+ "-" + transaction.getTransactionTime().getMonthOfYear() +
					"\":{\"spent\":" +spent + ", \"income\":" + income + "},");
		}
		
		System.out.println("Average  spent " +totalSpent.divide(new BigDecimal(months),RoundingMode.HALF_UP) + " income " + totalEarnings.divide(new BigDecimal(months),RoundingMode.HALF_UP));
	}
	
	public static void displayTransactionsWithoutCreditCardPayments(GetAllTransactionsResponse response){
		
		List<Transaction> transactions = sortTransactionsByDate(response.getTransactions());
		
		int size = transactions.size();
		int months = 0 ;
		BigDecimal totalEarnings = new BigDecimal(0);
		BigDecimal totalSpent = new BigDecimal(0);
		
		for(int i=0 ; i < size ; i++)	{
			Transaction transaction = transactions.get(i);
			int month = transaction.getTransactionTime().getMonthOfYear();
			int year = transaction.getTransactionTime().getYear();
			BigDecimal amount = transactions.get(i).getAmount();
			BigDecimal spent= new BigDecimal(0);
			BigDecimal income = new BigDecimal(0);
			if (amount.compareTo(BigDecimal.ZERO) > 0) {
				income = income.add(amount);
			} else {
				spent = spent.add(amount.abs());
			}

			while(i < size-1 && year == transactions.get(i+1).getTransactionTime().getYear() && month == transactions.get(i+1).getTransactionTime().getMonthOfYear()){
				DateTime dt = transactions.get(i).getTransactionTime();
				BigDecimal currAmount = transactions.get(i).getAmount();
				dt = dt.plusHours(24);
				if(transactions.get(i+1).getAmount().compareTo(BigDecimal.ZERO) >0){
					if(dt.isAfter(transactions.get(i+1).getTransactionTime())  && currAmount.abs().compareTo(transactions.get(i+1).getAmount()) == 0){
						System.out.println(" Credit Card Payment Transactions Id's " + transactions.get(i).getTransactionId() + " " + transactions.get(i+1).getTransactionId());
						spent = spent.subtract(transactions.get(i+1).getAmount().abs());
					}else{
						income = income.add(transactions.get(i+1).getAmount());
					}
				}else{
					if(dt.isAfter(transactions.get(i+1).getTransactionTime())  && currAmount.compareTo(transactions.get(i+1).getAmount().abs()) == 0){
						System.out.println(" Credit Card  Payment Transactions Id's " + transactions.get(i).getTransactionId() + " " + transactions.get(i+1).getTransactionId());
						income = income.subtract(transactions.get(i+1).getAmount().abs());
					}else{
						spent = spent.add(transactions.get(i+1).getAmount().abs());
					}
				}
				i++;
			}
			months++;
			totalEarnings = totalEarnings.add(income);
			totalSpent =  totalSpent.add(spent);
			System.out.println( transaction.getTransactionTime().getYear()+ "-" + transaction.getTransactionTime().getMonthOfYear() +
					" spent " +spent + " income " + income);
		}
		
		System.out.println("Average  spent " +totalSpent.divide(new BigDecimal(months),RoundingMode.HALF_UP) + " income " + totalEarnings.divide(new BigDecimal(months),RoundingMode.HALF_UP));
	}
	
	private static GetAllTransactionsRequest createRequest(){
		GetAllTransactionsRequest request = new GetAllTransactionsRequest();
		Args ar = new Args();
		ar.setApiToken("AppTokenForInterview");
		ar.setUid(1110590645L);
		ar.setToken("0D41B3B1C00F3F481322F8B95CB3E90C");
		ar.setJsonStrictMode(false);
		ar.setJsonVerboseResponse(false);
		request.setArgs(ar);
		return request;
	}

}
