package org.cloudfoundry.demo;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.CollectionCallback;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

@Repository
public class VisitRepository {
	
	private final static String LAST10 = "last10";
	private final static String TOTAL = "TOTAL";

	@Autowired
	private MongoTemplate mongoTemplate;

	void newVisit(Visit visit) {
		Query query = new Query(new Criteria("tag").is(TOTAL));
		Update update = new Update().inc("visitCount", 1);
		mongoTemplate.updateFirst(query, update, Aggregate.class);
		mongoTemplate.insert(visit, LAST10);
		mongoTemplate.insert(visit);
	}

	Long getTotalVisits() {
		Query query = new Query(new Criteria("tag").is(TOTAL));
		return mongoTemplate.findOne(query, Aggregate.class).getVisitCount();
	}
	
	List<Visit> getLastTen() {
		return mongoTemplate.findAll(Visit.class, LAST10);
	}
	
	@PostConstruct
	void init() {
		if (!mongoTemplate.collectionExists(Aggregate.class)) {
			mongoTemplate.createCollection(Aggregate.class);
			mongoTemplate.insert(new Aggregate(TOTAL));
		}
		if (!mongoTemplate.collectionExists(LAST10)) {
			CollectionOptions collectionOptions = new CollectionOptions(100000, 10, true);
			mongoTemplate.createCollection(LAST10, collectionOptions);
		}
	}
	
	void clear() {
		mongoTemplate.dropCollection(Aggregate.class);
		mongoTemplate.dropCollection(Visit.class);
		mongoTemplate.dropCollection(LAST10);
		init();
	}
	
	public String getAggregates() {
		final StringBuilder aggregateData = new StringBuilder();
		mongoTemplate.execute(mongoTemplate.getCollectionName(Aggregate.class), 
			new CollectionCallback<String>() {
				public String doInCollection(DBCollection collection) throws MongoException, DataAccessException {
					for (DBObject dbo : collection.find()) {
						aggregateData.append(dbo.toString());
						aggregateData.append(" ");
					}
					return null;
				}
			});
		return aggregateData.toString();
	}

	public Long getCount() {
		return mongoTemplate.getCollection(mongoTemplate.getCollectionName(Visit.class)).count();
	}

	public String getDump() {
		final StringBuilder mongoData = new StringBuilder();
		mongoTemplate.execute(LAST10, 
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
}
