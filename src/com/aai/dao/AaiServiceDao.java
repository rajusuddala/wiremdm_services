package com.aai.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.aai.info.CRFModel;
import com.aai.info.Token;


@Repository
public class AaiServiceDao {
	
	private static JdbcTemplate jdbcTemplate;
	private static DataSource dataSource;
	private static AaiServiceDao dao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
    	this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        dao = this;
    }
    
    
    //JDBC-backed implementations of the methods
    public static void processCRFModelInfo(List<Token> tokenList,String ikey, final String crfModelName,String datasetName) {
    	
    	final List<CRFModel> crfModelList = new ArrayList<CRFModel>();
    	
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
		  	
		  	if(crfModelList.size() > 0){
		  		for(CRFModel crfModel: crfModelList){
			  	
		  			final int runCount = crfModel.getRunCount();
		  			final int id = crfModel.getId();
		  			
		  			jdbcTemplate.update("UPDATE CRFMODELS SET RUNCOUNT=? WHERE ID = ?",
		    			new PreparedStatementSetter() {
							
							@Override
							public void setValues(PreparedStatement ps) throws SQLException {
								ps.setInt(1, runCount+1);
								ps.setInt(2, id);
							};
						});
		  			
		  			insertBatch(tokenList,crfModelName,ikey,crfModel);
		  		}
		  	}
    	}
    
    
    public static void insertBatch(final List<Token> tokens, final String tableName,final String ikey, final CRFModel model){
    	 
    	  String sql = "INSERT INTO "+tableName + "_accuracy(I_KEY, TOKEN_ID, TOKEN_VALUE,CRFID,RUN_NUMBER,ENTITY_NAME) VALUES (?, ?, ?, ?, ?, ?)";
    	 
    	  jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
    		
    		@Override
    		public void setValues(PreparedStatement ps, int i) throws SQLException {
    			Token token = tokens.get(i);
    			ps.setString(1, ikey);
    			ps.setLong(2, token.getId());
    			ps.setString(3, token.getValue());
    			ps.setLong(4, model.getId());
    			ps.setLong(5, model.getRunCount());
    			ps.setString(6, token.getEntity());
    		}
    		
    		@Override
    		public int getBatchSize() { 
    			return tokens.size();
    		}
    	  });
    	  
    	  //INSERT INTO finall_banks_swifft_ ? (I_KEY, TOKEN_ID, TOKEN) VALUES (?, ?, ?)
    	  if(null !=  model.getFlag() && model.getFlag().equalsIgnoreCase("Y")){
    		  
    		  for(Token token: tokens){
    	      		String SQL = "INSERT INTO "+tableName+"_"+token.getEntity()+" (ID, I_KEY, TOKEN_ID, TOKEN) VALUES (?, ?, ?, ?)";
    	      		
    	   			jdbcTemplate.update(SQL, new Object[] {System.nanoTime(),ikey,token.getId(),token.getValue()});
    	   		
    	      	}
    	  
/*	    	  String sql1 = "INSERT INTO "+tableName+ "_ ? (ID, I_KEY, TOKEN_ID, TOKEN) VALUES (?, ?, ?, ?)";
	     	 
	    	  jdbcTemplate.batchUpdate(sql1, new BatchPreparedStatementSetter() {
	    		
	    		@Override
	    		public void setValues(PreparedStatement ps, int i) throws SQLException {
	    			Token token = tokens.get(i);
	    			ps.setString(1, token.getEntity());
	    			ps.setInt(2, (int) System.currentTimeMillis());
	    			ps.setString(3, ikey);
	    			ps.setLong(4, token.getId());
	    			ps.setString(5, token.getValue());
	    		}
	    		
	    		@Override
	    		public int getBatchSize() {
	    			return tokens.size();
	    		}
	    	  });*/
    	  }
    	  
    	}
    
      
 }
