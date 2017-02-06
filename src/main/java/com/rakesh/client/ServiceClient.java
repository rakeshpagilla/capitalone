package com.rakesh.client;


import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakesh.domain.GetAllTransactionsRequest;
import com.rakesh.domain.GetAllTransactionsResponse;

public class ServiceClient {
	
	protected final ObjectMapper mapper = new ObjectMapper();

	private WebTarget webClient;

	public ServiceClient(String endpointUrl ) {

		    mapper.setSerializationInclusion(Include.NON_EMPTY);
	        JacksonJsonProvider jsonProv = new JacksonJsonProvider(mapper);

	        ClientBuilder cb = ClientBuilder.newBuilder()
	                .register(jsonProv, MessageBodyReader.class, MessageBodyWriter.class);
	        webClient = cb.build().target(endpointUrl);
	}
	
	
	public GetAllTransactionsResponse doPost(GetAllTransactionsRequest getAllTransactionsRequest ) throws IOException {

		Invocation.Builder invocationBuilder =  webClient.request(MediaType.APPLICATION_JSON);
		GetAllTransactionsResponse response = (GetAllTransactionsResponse) invocationBuilder.post(Entity.json(getAllTransactionsRequest),GetAllTransactionsResponse.class);
		return response;
		
	}
}
