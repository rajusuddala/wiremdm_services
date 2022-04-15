package com.aai.test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.aai.info.Record;
import com.aai.info.Records;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class testRecognize {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Ignore
	@Test
	public void test1(){
		
		
	}
	
	//@Ignore
	@Test
	public void test() {
        Client client = Client.create();

        // Define the URL for testing the example application
        WebResource webResource = client.resource("http://localhost:8080/wiremdmServices/restservice/testRecognize/recognizedata");
    //    WebResource webResource = client.resource("http://localhost:8080/rest/service/employee/1");
        // Test the POST method
        Records records = new Records();
        List<Record> recordList = new ArrayList<Record>();
        Record record = new Record();
        record.setId("1");
        
        recordList.add(record);
  //      records.setCrfModel("abc");
        
    //    records.setDataSetName("xyz");
        records.setRecord(recordList);

        ClientResponse response = webResource.type("application/xml").post(ClientResponse.class, records);

        System.out.println(Level.INFO+ "POST status: {0}"+ response.getStatus());
        
        if (response.getStatus() == 201) {
        	System.out.println("POST succeeded");
        } else {
        	System.out.println("POST failed");
        }

	}

}
