package com.aai.info;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Model")
public class CRFModel {
	
	private int id;
	private String name;
	private int runCount;
	private int datasetId;
	private String flag;
	
	public CRFModel(int id, String name, int runCount, int datasetId, String flag) {
		super();
		this.id = id;
		this.name = name;
		this.runCount = runCount;
		this.datasetId = datasetId;
		this.flag = flag;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRunCount() {
		return runCount;
	}
	public void setRunCount(int runCount) {
		this.runCount = runCount;
	}
	public int getDatasetId() {
		return datasetId;
	}
	public void setDatasetId(int datasetId) {
		this.datasetId = datasetId;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	

}
