package com.aai.test;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Ignore;
import org.junit.Test;

import com.aai.info.Record;
import com.aai.info.Records;
import com.aai.restful.service.TestRecognizationService;
 
 
public class HttpXMLDataPost {
 
    static Properties props;
  	String xmlReq= "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><records> " +
			"<record i_key=\"1000080893\"> <id>1000080893</id> <addr1>SHANGHAI PUDONG DEVELOPMENT BANK</addr1> <addr2>JINGAN SUB-BRANCH</addr2> <addr3/> <addr4/> </record>" +
					"</records>";

  	String modelInfo = "<ModelInfo><crfModel>0925M</crfModel><dataSetName>0925DS</dataSetName><path>C:/nerdata/<path></ModelInfo>";
  	
  	String recordXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
  						"<Record> "+
  						"<ikey>1000742795</ikey>" +
  						"<addr1>STATE BANK OF BIKANER AND JAIPUR</addr1>" +
  						"<addr2>EMPIRE TOWER</addr2>" +
  						"<addr3>FLOOR 1011 AHMEDABAD</addr3>" +
  						"<addr4/>" +
  						"<crfmodel>finall_banks_swifft</crfmodel>" +
  						"<datasetname>bank_final_swift</datasetname>" +
  						"<path>/usr/nerdata/</path>" +
  						"<option>1</option>" +
  						"</Record>";
  	
  	String fullXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
			"<Records> "+
			"	<option>1</option>"+
			"	<datasetname>bank_final_swift</datasetname>"+
			"	<crfmodel>finall_banks_swifft</crfmodel>"+
			"	<path>c:\\nerdata\\</path>"+
			"	<record>"+
			"		<option>1</option>"+
			"			<ikey>1000830106</ikey>"+
			"			<addr1>WELL FARGO BANK</addr1>"+
			"			<addr2>2420 EAST SUNSET ROAD</addr2>"+
			"			<addr3>LAS VEGAS  NEVADA USA</addr3>"+
			"	</record>"+
			"	<record>"+
			"		<option>1</option>"+
			"		<ikey>1000830107</ikey>"+
			"		<addr1>WELLS FARGO BANK</addr1>"+
			"		<addr2>401 B ST  STUITE 2201</addr2>"+
			"		<addr3>SAN DIEGO  CA92101  USA </addr3>"+
			"	</record>"+
			"</Records>";
			    
  	
    @Ignore
    @Test
    public void unmarshalling(){
    	
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Records.class);
			 
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			//StringBuffer xmlStr = new StringBuffer( xmlReq );
			Records reqRecordsObj = (Records) jaxbUnmarshaller.unmarshal( new StringReader( fullXML ) );
			if(null != reqRecordsObj){
				
			//	ModelInfo modelInfo = reqRecordsObj.getModelInfo();
				
				System.out.println("reqRecordObj Ikey:"+reqRecordsObj.getCrfmodel());
				for(Record record: reqRecordsObj.getRecord()){
					System.out.println(record.getAddr1());
				}
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
/*    public static void main(String[] args)
    {
        String fileName = "";
        String propertiesFileName = "";
 
        if (args.length < 2)
        {
            HttpXMLDataPost httpXMLDataPost = new HttpXMLDataPost();
            System.err.println("Usage: java "+ httpXMLDataPost.getClass().getName()+
            " XML_File_Name Properties_File_Name");
            System.exit(1);
        }
 
        fileName = args[0].trim();
        propertiesFileName = args[1].trim();
        boolean success = XMLDataPost(fileName, propertiesFileName);
        System.out.println(success);
    }*/
 @Ignore
    @Test
    public void TestFullXML(){
    	TestRecognizationService test = new TestRecognizationService();
    	try {
			test.processFullXMLRequest(fullXML);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
 //	@Ignore
    @SuppressWarnings("deprecation")
	@Test
    public void XMLDataPost(){
 
      
		String strXMLFilename = "C:/Users/shreyas/Desktop/xmlReq.xml";
    	
        boolean success = false;
        HttpClient httpclient = new DefaultHttpClient();
 
        try {
 
    /*        props = new Properties();
            props.load(new FileInputStream("properties/" + propertiesFileName.trim()));
 */
 
            HttpPost httpPost = new HttpPost("http://localhost:8080/wiremdmServices/restservice/testRecognize/recognizedata");
 
           /* File file = new File(strXMLFilename.trim());
 
            InputStreamEntity reqEntity = new InputStreamEntity(new FileInputStream(file), -1);*/
            HttpXMLDataPost xmlPost = new HttpXMLDataPost();
            StringEntity entity = new StringEntity(
                    "<DocSearch><searchString>*.pdf</searchString>" +
                    "<userName>Sudhakar KV</userName></DocSearch>", "text/xml", "ISO-8859-1");
      //      System.out.println(reqEntity);
         //   entity.setContentType("application/xml");
        //    reqEntity.setChunked(true);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
           // nameValuePairs.add(new BasicNameValuePair("firstname","as400"));
           // nameValuePairs.add(new BasicNameValuePair("lastname","samplecode"));
            nameValuePairs.add(new BasicNameValuePair(fullXML,"xmlReq"));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs)); 
         //   httpPost.setEntity(new UrlEncodedFormEntity());
          
        //  httpPost.setEntity(entity);
            httpPost.setHeader("reqXML", fullXML);
    //        httpPost.setHeader("modelInfo",modelInfo);
            
 
            System.out.println("Executing request " + httpPost.getRequestLine());
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
 
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            if(response.getStatusLine().getStatusCode() == 200){
                success = true;
            }
            if (resEntity != null) {
                System.out.println("Response content length: " + resEntity.getContentLength());
                String responseBody = EntityUtils.toString(resEntity);
                System.out.println("Data: " + responseBody);
                System.out.println("Chunked?: " + resEntity.isChunked());
            }
            EntityUtils.consume(resEntity);
        } 
        catch (Exception e) {
            System.out.println(e);
        }
        finally {
            httpclient.getConnectionManager().shutdown();
        }
 
     //   return success;
 
    }
    

 
}