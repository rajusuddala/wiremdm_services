package com.aai.restful.service;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.aai.dao.AaiServiceDao;
import com.aai.info.Record;
import com.aai.info.Records;
import com.aai.info.Status;
import com.aai.info.Token;
import com.aai.util.SpecialCharacterParser;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations.AnswerAnnotation;
import edu.stanford.nlp.ling.CoreLabel;



@Path("/testRecognize")
public class TestRecognizationService {
	
	
	
	@POST
	@Path("recognizedata")
	@Consumes("application/x-www-form-urlencoded")
	@Produces({"application/xml", "application/json"})
	public Status acceptall(@Context UriInfo uri,@Context HttpHeaders headers, MultivaluedMap<String, String> formParams) {
		Records records = new Records();
		Status status = new Status();
		
		try {
			
			for(String formParam : formParams.keySet()){
				
				if(null != formParam){
					System.out.println("FormParams:"+formParam);
					records = processFullXMLRequest(formParam);
				}
			}
			
			for(String header : uri.getQueryParameters().keySet()){
				System.out.println("QueryParams:"+header);
			}
			
			for(String header : uri.getPathParameters().keySet()){
				System.out.println("PathParams:"+header);
			}
			
			for(String header : headers.getRequestHeaders().keySet()){
				System.out.println("RequestHeaders:"+header);
			}
	
		if(null != records){
			status.setCode(0);
			status.setMessage("SUCCESS");
		}

		}catch (Exception e) {
			
			status.setCode(0);
			status.setMessage(e.getMessage());
			e.printStackTrace();
		}
		
		return status;
	}


	@POST
	@Path("recognizedatauri")
	@Consumes("application/x-www-form-urlencoded")
	@Produces({"application/xml", "application/json"})
	public Status uriInfo(@Context UriInfo ui) {
		Records records = new Records();
		Status status = new Status();
		 MultivaluedMap<String, String> queryParams = ui.getQueryParameters();

		try {
			 Iterator<Entry<String, List<String>>> i = queryParams.entrySet().iterator();
		        
				while (i.hasNext()) {
					
					Map.Entry mapEntry = (Map.Entry) i.next();
					System.out.println("The key is: " + mapEntry.getKey() + ",value is :" + mapEntry.getValue());
		
				}
		if(null != records){
			status.setCode(0);
			status.setMessage("SUCCESS");
		}

		}catch (Exception e) {
			
			status.setCode(0);
			status.setMessage(e.getMessage());
			e.printStackTrace();
		}
		
		return status;
	}
	
	
	@POST
	@Path("recognizedataformparam")
	@Consumes("application/x-www-form-urlencoded")
	@Produces({"application/xml", "application/json"})
	public Status formdata(@FormParam("xmlReq") String xmlReq) {
		Records records = new Records();
		Status status = new Status();

		try {
			
			System.out.println(xmlReq);
			
		
		if(null != records){
			status.setCode(0);
			status.setMessage("SUCCESS");
		}

		}catch (Exception e) {
			
			status.setCode(0);
			status.setMessage(e.getMessage());
			e.printStackTrace();
		}
		
		return status;
	}

	
	
	@POST
	@Path("recognizedataform")
	@Consumes("application/x-www-form-urlencoded")
	@Produces({"application/xml", "application/json"})
	public Status formParams(MultivaluedMap<String, String> formParams) {
		Records records = new Records();
		Status status = new Status();

		try {
			
			for(String header : formParams.keySet()){
				System.out.println(header);
			}
			
			
			for(List<String> header : formParams.values()){
				System.out.println(header);
			}
			 /*Iterator<Entry<String, List<String>>> i = formParams.entrySet().iterator();
		        
				while (i.hasNext()) {
					
					Map.Entry mapEntry = (Map.Entry) i.next();
					System.out.println("The key is: " + mapEntry.getKey() + ",value is :" + mapEntry.getValue());
		
				}*/
		if(null != records){
			status.setCode(0);
			status.setMessage("SUCCESS");
		}

		}catch (Exception e) {
			
			status.setCode(0);
			status.setMessage(e.getMessage());
			e.printStackTrace();
		}
		
		return status;
	}

	@POST
	@Path("testRecognizeFullXML")
	@Produces({"application/xml", "application/json"})
	public Status testRecognizeFullXML(@Context HttpHeaders headers) {
		
		Records records = new Records();
		Status status = new Status();
		
		for(String header : headers.getRequestHeaders().keySet()){
			System.out.println(header);
		}

		try {
			if(headers.getRequestHeader("reqXML") != null){
				
				records = processFullXMLRequest(headers.getRequestHeader("reqXML").get(0));
			}
		
		
		if(null != records){
			status.setCode(0);
			status.setMessage("SUCCESS");
		}

		}catch (Exception e) {
			
			status.setCode(0);
			status.setMessage(e.getMessage());
			e.printStackTrace();
		}
		
		return status;
	}
	
	
	
	public Records processFullXMLRequest(String recordXml) throws Exception{
			
		JAXBContext jaxbContext = JAXBContext.newInstance(Records.class);
		 
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Records records = (Records) jaxbUnmarshaller.unmarshal(new StringReader( recordXml ) );
		
		if(null !=records){
			
			records = performTestRecognize(records);
			
			for(Record record : records.getRecord()){
				
				List<Token> tokenList  = record.getToken();
				if(null != tokenList && tokenList.size() > 0) {
				
					AaiServiceDao.processCRFModelInfo(tokenList,record.getIkey(),records.getCrfmodel(),records.getDatasetname());
				}
			}
		}

		return records;
	}
	
	
	@POST
	@Path("recognizationData")
	//@Consumes({"application/xml", "application/json"})
	@Produces({"application/xml", "application/json"})
	public Status recognizationData(@Context HttpHeaders headers) {
		
		Record record = new Record();
		Status status = new Status();
		
		try {
			if(headers.getRequestHeader("reqXML") != null){
				
				record = processRequest(headers.getRequestHeader("reqXML").get(0));
			}
			
			if(null != record.getToken()){
				status.setCode(0);
				status.setMessage("SUCCESS");
			}
		} catch (Exception e) {
			
			status.setCode(0);
			status.setMessage(e.getMessage());
			e.printStackTrace();
		}
		 
		return status;

	}
	

	
	public Record processRequest(String recordXml)throws Exception{
		

//		StringWriter writer = new StringWriter();
	
		JAXBContext jaxbContext = JAXBContext.newInstance(Record.class);
		 
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		//StringBuffer xmlStr = new StringBuffer( xmlReq );
		Record reqRecordObj = (Record) jaxbUnmarshaller.unmarshal( new StringReader( recordXml ) );
//		System.out.println("reqRecordObj Ikey:"+reqRecordObj.getIkey());
		Record resRecordObj = performTestRecognize(reqRecordObj);
		
		List<Token> tokenList  = resRecordObj.getToken();
		if(null != tokenList && tokenList.size() > 0) {
		
			AaiServiceDao.processCRFModelInfo(tokenList,reqRecordObj.getIkey(),reqRecordObj.getCrfmodel(),reqRecordObj.getDatasetname());
		}
		return resRecordObj;
		//return doRecordMarshalling(resRecordObj);	
	}
	
		
/*	public String processRequest(String recordsXml,String modelInfoXml)throws Exception{
		
		JAXBContext jaxbContext = JAXBContext.newInstance(Records.class);
		 
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		//StringBuffer xmlStr = new StringBuffer( xmlReq );
		Records recordsObj = (Records) jaxbUnmarshaller.unmarshal( new StringReader( recordsXml ) );
		
		jaxbContext = JAXBContext.newInstance(ModelInfo.class);
		 
		jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		//StringBuffer xmlStr = new StringBuffer( xmlReq );
		ModelInfo modelInfo = (ModelInfo) jaxbUnmarshaller.unmarshal( new StringReader( modelInfoXml ) );
		
		recordsObj = performTestRecognize(recordsObj);
		
		return doMarshalling(recordsObj);	
	}*/
	
	
	
	public String doRecordMarshalling(Record record) throws JAXBException{
		
		StringWriter writer = new StringWriter();
		
		JAXBContext jaxbContext = JAXBContext.newInstance(Record.class);
		
		Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(record, writer);
		
		return writer.toString();	
		
	}
	
	
	public String doMarshalling(Records records) throws JAXBException{
		
		StringWriter writer = new StringWriter();
		
		JAXBContext jaxbContext = JAXBContext.newInstance(Records.class);
		
		Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(records, writer);
				
		return writer.toString();	
		
	}
	

	public Record performTestRecognize(Record record) throws Exception{
		
		String serializedClassifier = record.getPath() + record.getCrfmodel()+"/" + record.getDatasetname() +"_"+ record.getCrfmodel()+".crf";
		File f = new File(serializedClassifier);
		Record respRecord = new Record();
		
		if(f.exists()){
			System.out.println("Path: "+serializedClassifier);
			AbstractSequenceClassifier classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
			
			List<Token> tokenList = new ArrayList<Token>();
			Token token = new Token();
	
			List<String> addrList = new ArrayList<String>();
			
			if(null != record.getAddr1()) {
				addrList.add(record.getAddr1());
			}
			
			if(null != record.getAddr2()) {
				addrList.add(record.getAddr2());
			}
			
			if(null != record.getAddr3()) {
				addrList.add(record.getAddr3());
			}
			
			if(null != record.getAddr4()) {
				addrList.add(record.getAddr4());
			}
			
			StringBuffer address = new StringBuffer();
			
			for (String val : addrList) {
				address.append(val + " ");
			}
				
			if(null != address && address.length() > 0) {
				
				String addrString = SpecialCharacterParser.removeSpecialCharacters(address.toString().trim());
				
				System.out.println("Sentance : "+addrString);
				
				 List<List<CoreLabel>> out = classifier.testSentences(addrString);
			        for (List<CoreLabel> sentence : out) {
			        	tokenList = new ArrayList<Token>();
			        	respRecord = new Record();
			          for (CoreLabel word : sentence) {
			  			  long tokenId = System.nanoTime();
			        	  token = new Token();
			        	  System.out.println("TokenID: "+tokenId);
			        	  token.setId(tokenId);
			        	  token.setEntity(word.get(AnswerAnnotation.class));
			        	  token.setValue(word.word());
			        	  tokenList.add(token);
			            System.out.print(word.word() + '/' + word.get(AnswerAnnotation.class) + ' ');
			          }
			          
			          respRecord.setToken(tokenList);
			         // System.out.println("sentence: " +sentence);
			        }
			}
			
		  }else{
			  System.out.println("File not found!");
		  }        
		return respRecord;
	}
	
	
	
	public Records performTestRecognize(Records records) throws Exception{
		
		String serializedClassifier = records.getPath() + records.getCrfmodel()+"/" + records.getDatasetname() +"_"+ records.getCrfmodel()+".crf";
		File f = new File(serializedClassifier);
		System.out.println("Path: "+serializedClassifier);
		
		if(f.exists()){
			
			AbstractSequenceClassifier classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
	
			List<Token> tokenList = new ArrayList<Token>();
			Token token = new Token();
			List<Record> recordList = records.getRecord();
			
			int i=0;
			for (Record record : recordList) {
				
				List<String> addrList = new ArrayList<String>();
				
				if(null != record.getAddr1()) 
					addrList.add(record.getAddr1());
				
				if(null != record.getAddr2()) 
					addrList.add(record.getAddr2());
				
				if(null != record.getAddr3()) 
					addrList.add(record.getAddr3());
				
				if(null != record.getAddr4()) 
					addrList.add(record.getAddr4());
				
				StringBuffer address = new StringBuffer();
				
				for (String val : addrList) {
					address.append(val + " ");
				}
		
				if(null != address && address.length() > 0) {
					
					String addrString = SpecialCharacterParser.removeSpecialCharacters(address.toString().trim());
					System.out.println("Sentance : "+addrString);
					
					List<List<CoreLabel>> out = classifier.testSentences(addrString);
				    	for (List<CoreLabel> sentence : out) {
				    		tokenList = new ArrayList<Token>();
				        	for (CoreLabel word : sentence) {
				  			  long tokenId = System.nanoTime();
				        	  token = new Token();
				        	  token.setId(tokenId);
				        	  System.out.println("TokenID: "+tokenId);
				        	  token.setEntity(word.get(AnswerAnnotation.class));
				        	  token.setValue(word.word());
				        	  tokenList.add(token);
				            System.out.print(word.word() + '/' + word.get(AnswerAnnotation.class) + ' ');
				          }
				          
				        	recordList.get(i).setToken(tokenList);
				        }
				}
				i++;
			}

		}else{
			  System.out.println("File not found!");
		}        
		
		return records;
	}
	
	
	
/*	public Records performTestRecognize(Records records,ModelInfo modelInfo) throws Exception{
		
		String serializedClassifier = records.getPath() + records.getCrfmodel()+"/" + records.getDatasetname() +"_"+ records.getCrfmodel()+".crf";
		File f = new File(serializedClassifier);
		System.out.println("Path: "+serializedClassifier);
	//	Records respRecords = new Records();
		
		if(f.exists()){
			
			AbstractSequenceClassifier classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
	
			List<Record> recordList = records.getRecord();
		//	List<Record> resqRecordList = new ArrayList<Record>();
			List<Token> tokenList = new ArrayList<Token>();
			Token token = new Token();
			
			int i=0;
			for (Record record : recordList) {
				
				StringBuffer sb = new StringBuffer();
				
				if(null != record.getAddr1())
					sb.append(record.getAddr1());
								
				if(null !=  record.getAddr2())
					sb.append(" "+record.getAddr2());
					
				if(null != record.getAddr3())
					sb.append(" "+record.getAddr3());
					
				
				if(null != record.getAddr4())
					sb.append(" "+record.getAddr2());
				
			
				String address = sb.toString();

				if(null != address){
				
					List<List<CoreLabel>> out = classifier.testSentences(address);
			        for (List<CoreLabel> sentence : out) {
			        	tokenList = new ArrayList<Token>();
			          for (CoreLabel word : sentence) {
			        	  token = new Token();
			        	  token.setId(System.nanoTime());
			        	  token.setEntity(word.get(AnswerAnnotation.class));
			        	  token.setValue(word.word());
			        	  tokenList.add(token);
			        	  System.out.print(word.word() + '/' + word.get(AnswerAnnotation.class) + ' ');
			          }
			          
			          recordList.get(i).setToken(tokenList);
			        }
				}
				i++;
			}
		}
		
		return records;
	}*/
		
}
