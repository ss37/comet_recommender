package edu.pitt.sis.cometrecommender.models;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

/**
 * This model is used to send a list of talks as response. 
 * @author SHRUTI
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class InterestingTalksModel {
	@XmlElement
	private String title;
	@XmlElement
	private String url;
	
	public InterestingTalksModel(){
		
	}
	
	public InterestingTalksModel(String title, String url){
		this.title = title;
		this.url = url;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
