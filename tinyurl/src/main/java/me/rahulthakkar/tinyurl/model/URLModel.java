package me.rahulthakkar.tinyurl.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class URLModel {
	private String shortURLHash;
	private String longURL;
	
	public URLModel(){
	}

	public URLModel(String shortURLHash, String longURL) {
		setLongURL(longURL);
		setShortURLHash(shortURLHash);
	}

	public String getLongURL() {
		return longURL;
	}

	public void setLongURL(String longURL) {
		if(longURL != null){
			this.longURL = longURL.trim();
		}
	}

	public String getShortURLHash() {
		return shortURLHash;
	}

	public void setShortURLHash(String shortURLHash) {
		if(shortURLHash != null){
			this.shortURLHash = shortURLHash.trim();
		}
	}
	
	
}
