package com.aai.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.sf.json.JSONObject;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.aai.restful.service.TestRecognizationService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


public class JunitTestcls {


  	String xmlReq= "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
  			"<records> " +
			" <record ikey=\"1000080893\"> <id>1000080893</id> <addr1>SHANGHAI PUDONG DEVELOPMENT BANK</addr1> <addr2>JINGAN SUB-BRANCH</addr2> <addr3/> <addr4/> </record>" +
			" <record ikey=\"5\"> "+
			"    <id>abc</id> "+
			"    <addr1>Cognizant</addr1> "+
			"    <addr2>technology solutions</addr2>"+
			"    <addr3>555 W Madison St</addr3>"+
			"    <addr4>Chicago IL 60601</addr4>"+
			"  </record>"+
			"  <record ikey=\"4\">"+
			"    <id>4</id>"+
			"    <addr1>Northern</addr1>"+
			"    <addr2>Trust</addr2>"+
			"    <addr3>50 S Lasalle</addr3>"+
			"    <addr4>Chicago IL 60603</addr4>"+
			"  </record> "+
					"  <crfModel>0925M</crfModel> " +
					"<dataSetName>0925DS</dataSetName> " +
					"  <path>C:/nerdata/</path> " +
					"   <option>1</option>" +
					" </records>";
  	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		// Obtain our environment naming context
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");

		// Look up our data source
		DataSource ds = (DataSource) envCtx.lookup("jdbc/aaiDataSource");

		// Allocate and use a connection from the pool
		Connection conn = ds.getConnection();
		
		conn.close();
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/*public void testDB(){
	jdbcTemplate.query("SELECT ID,NAME,RUNCOUNT, DATASET_ID, FLAG FROM CrfModels c WHERE c.NAME=?",
			  new PreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {ps.setString(1, crfModelName);	}
				},
	            new RowCallbackHandler() {
		  			@Override
	                public void processRow(ResultSet rs) throws SQLException {
		  				crfModelList.add(new CRFModel(rs.getInt("ID"), rs.getString("NAME"), rs.getInt("RUNCOUNT"), rs.getInt("DATASET_ID"), rs.getString("FLAG")));
	                }
      });
	}*/
	
	@Ignore
	@Test
	public void searchTest(){
		
		Client client = Client.create();
		 
		WebResource webResource = client.resource("http://192.168.0.12:8080/WebProject/customer/CustInfo");
 
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
 
		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "	+ response.getStatus());
		}
 
		String output = response.getEntity(String.class);
 
		System.out.println("Output from Server .... \n");
		System.out.println(output);

	}
	
	/*@Test
	public void test() {
		
		System.out.println("Total Results:");
		try {
			session.beginTransaction();
			Criteria criteria = session.createCriteria(WiresVComp.class);
			criteria.setProjection(Projections.rowCount());
			List employees = criteria.list();
			
			if (employees!=null) {
				Integer rowCount = (Integer) employees.get(0);
				System.out.println("Total Results:" + rowCount);
			}
			session.getTransaction().commit();
		}catch (HibernateException e) {
			 e.printStackTrace();
			 session.getTransaction().rollback();
		}
		
	}*/
	
	
	@Ignore
	@Test
	public void testUnmarshall(){
		
		 try {
	          /*  //Unmarshaling            
	            JAXBContext context = JAXBContext.newInstance(Records.class);
	            Unmarshaller unmarshaller = context.createUnmarshaller();
	            //Unmarshal the String
	            Records recordsObj = (Records) unmarshaller.unmarshal(new StringReader(xmlReq));
	            //Checking that the values were recieved ok
	            System.out.print(recordsObj.getCrfModel());
	            //return Response.ok("XML recieved from client!!!").build();
	             */
			 
			 TestRecognizationService trService = new TestRecognizationService();
			// System.out.println(trService.processRequest(xmlReq));
			 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}


	@Ignore
	@Test
	public void readDataFromFile() {
		String filename = "C:/nerdata/0925M/0925DS_0925M_result.out";
		ArrayList<String[]> data = new ArrayList<String[]>();
	
		String text = new String();
		
		try {
			// Open an input stream

			int i = 0;
			File file = new File(filename);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			// Read a line of text
			while ((text = br.readLine()) != null) {
				//data.add(text.split("\t"));
				System.out.println(text.split("\t"));
				i++;
			}
			fr.close();
		    // Close our input stream
			// Catches any error conditions
		}catch (IOException e) {
			System.err.println("Unable to read from file");
			// System.exit(-1);
		}
	//	return data;

	}
	
	

}
