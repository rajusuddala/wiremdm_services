package com.aai.test;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.aai.dao.AaiServiceDao;
import com.aai.info.CRFModel;
import com.aai.info.Token;

public class TestDao {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		ApplicationContext context = 
	    		new ClassPathXmlApplicationContext("com/aai/config/spring-test.xml");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private static JdbcTemplate jdbcTemplate;
    public void setDataSource(DataSource dataSource) {
    	
    	jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @Test
	public void test() {
    	
		String tableName = "finall_banks_swifft";
		String ikey = "1234";
		List<Token> tokenList = new ArrayList<Token>();
		
		Token token1 = new Token();
		token1.setId(1);
		token1.setEntity("bname");
		token1.setValue("STATE");
		tokenList.add(token1);
		
		token1 = new Token();
		token1.setId(1);
		token1.setEntity("bsuffix");
		token1.setValue("BANK");
		tokenList.add(token1);
		
		token1 = new Token();
		token1.setId(1);
		token1.setEntity("misc");
		token1.setValue("EMPIRE");
		tokenList.add(token1);
		//new CRFModel(rs.getInt("ID"), rs.getString("NAME"), rs.getInt("RUNCOUNT"), rs.getInt("DATASET_ID"), rs.getString("FLAG"))
		
		CRFModel model = new CRFModel(1,"finall_banks_swifft",3,24,"Y");
      //String sql1 = "INSERT INTO "+tableName+ "_ ? (I_KEY, TOKEN_ID, TOKEN) VALUES (?, ?, ?)";
      
//      	for(Token token: tokenList){
//      		String SQL = "INSERT INTO "+tableName+"_"+token.getEntity()+" (I_KEY, TOKEN_ID, TOKEN) VALUES (?, ?, ?)";
//      		
//   			jdbcTemplate.update(SQL, new Object[] {ikey,token.getId(),token.getValue()});
//   		
//      	}
		
		AaiServiceDao.insertBatch(tokenList, tableName, ikey, model);
	}

}
