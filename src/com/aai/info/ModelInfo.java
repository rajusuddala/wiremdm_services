package com.aai.info;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ModelInfo")
public class ModelInfo {

	@XmlElement(required = true)
	String crfModel;
	
	@XmlElement(required = true)
	String dataSetName;
	
	@XmlElement(required = true)
	String path;
	
	@XmlElement(required = true)
	int option;
	
	public String getCrfModel() {
		return crfModel;
	}
	public void setCrfModel(String crfModel) {
		this.crfModel = crfModel;
	}
	public String getDataSetName() {
		return dataSetName;
	}
	public void setDataSetName(String dataSetName) {
		this.dataSetName = dataSetName;
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
