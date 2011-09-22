package org.cloudfoundry.demo;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Visit {

	@Id
	ObjectId id;
	
	String remoteAddress;

	String originAddress;

	Date timeOfVisit;
	
	public ObjectId getId() {
		return id;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}
	
	public String getOriginAddress() {
		return originAddress;
	}

	public void setOriginAddress(String originAddress) {
		this.originAddress = originAddress;
	}

	public Date getTimeOfVisit() {
		return timeOfVisit;
	}
	
	public void setTimeOfVisit(Date timeOfVisit) {
		this.timeOfVisit = timeOfVisit;
	}
}
