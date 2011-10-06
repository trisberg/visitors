package org.cloudfoundry.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.CollectionCallback;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

@Repository
public class CloudRepository {
	
	private final static String CLOUDS = "clouds";

	@Autowired
	private MongoTemplate mongoTemplate;

	public List<Cloud> getClouds() {
		List<Cloud> clouds = mongoTemplate.findAll(Cloud.class, CLOUDS);
		for (Cloud cloud : clouds) {
			Long count = getCloudCount(cloud.address);
			if (count != null) {
				cloud.setVisitCount(count);
				saveCloud(cloud);
			}
		}
		return clouds;
	}
	
	public void saveCloud(Cloud cloud) {
		mongoTemplate.save(cloud, CLOUDS);
	}
	
	public void deleteCloud(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		mongoTemplate.remove(query, CLOUDS);
	}
	
	public String getDump() {
		final StringBuilder mongoData = new StringBuilder();
		mongoTemplate.execute(CLOUDS, 
			new CollectionCallback<String>() {
				public String doInCollection(DBCollection collection) throws MongoException, DataAccessException {
					for (DBObject dbo : collection.find()) {
						mongoData.append(dbo.toString());
						mongoData.append(" ");
					}
					return null;
				}
			});
		return mongoData.toString();
	}
	
	private Long getCloudCount(String address) {
		RestTemplate rt = new RestTemplate();
		String value;
		try {
			value = rt.getForObject(address + "/count", String.class);
		} catch (Exception e) {
			return null;
		}
		Long ret;
		try {
			ret = Long.valueOf(value);
		} catch (NumberFormatException e) {
			return null;
		}
		return ret;
	}
}
