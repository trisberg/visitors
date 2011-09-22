package org.cloudfoundry.demo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Aggregate {
	
	@Id
	private String tag;
	
	private Long visitCount;

	public Aggregate(String tag) {
		this.tag = tag;
		this.visitCount = 0L;
	}

	public String getTag() {
		return tag;
	}

	public Long getVisitCount() {
		return visitCount;
	}

}
