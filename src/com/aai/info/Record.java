package com.aai.info;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Record")
public class Record {

//	@XmlAttribute(required = true)
	@XmlElement
	String ikey;
	@XmlElement
	String id;
	@XmlElement
	String addr1;
	@XmlElement
	String addr2;
	@XmlElement
	String addr3;
	@XmlElement
	String addr4;
	@XmlElement
	String addr5;
	@XmlElement
	String addr6;
	
	@XmlElement(required = true)
	String crfmodel;
	
	@XmlElement(required = true)
	String datasetname;
	
	@XmlElement(required = true)
	String path;
	
	@XmlElement(required = true)
	int option;
	
	@XmlElement
	//@XmlValue
	List<Token> token;
	
	

	public String getIkey() {
		return ikey;
	}
	public void setIkey(String ikey) {
		this.ikey = ikey;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getAddr3() {
		return addr3;
	}
	public void setAddr3(String addr3) {
		this.addr3 = addr3;
	}
	public String getAddr4() {
		return addr4;
	}
	public void setAddr4(String addr4) {
		this.addr4 = addr4;
	}
	public String getAddr5() {
		return addr5;
	}
	public void setAddr5(String addr5) {
		this.addr5 = addr5;
	}
	public String getAddr6() {
		return addr6;
	}
	public void setAddr6(String addr6) {
		this.addr6 = addr6;
	}
	public List<Token> getToken() {
		return token;
	}
	public void setToken(List<Token> token) {
		this.token = token;
	}
	public String getCrfmodel() {
		return crfmodel;
	}
	public void setCrfmodel(String crfmodel) {
		this.crfmodel = crfmodel;
	}
	public String getDatasetname() {
		return datasetname;
	}
	public void setDatasetname(String datasetname) {
		this.datasetname = datasetname;
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
