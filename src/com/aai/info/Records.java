package com.aai.info;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Records")
public class Records {
	
	@XmlElement(required = true)
	String datasetname;
	
	@XmlElement(required = true)
	String crfmodel;
	
	@XmlElement(required = true)
	String path;
	
	@XmlElement(required = true)
	int option;
	

	@XmlElement(required = true)
	List<Record> record;
	
	public List<Record> getRecord() {
		return record;
	}
	public void setRecord(List<Record> record) {
		this.record = record;
	}
	public String getDatasetname() {
		return datasetname;
	}
	public void setDatasetname(String datasetname) {
		this.datasetname = datasetname;
	}
	public String getCrfmodel() {
		return crfmodel;
	}
	public void setCrfmodel(String crfmodel) {
		this.crfmodel = crfmodel;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getOption() {
		return option;
	}
	public void setOption(int option) {
		this.option = option;
	}
}
