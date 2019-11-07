package com.ibm.rms.service;

import org.springframework.data.mongodb.core.MongoOperations;

import com.ibm.rms.model.DatabaseSequences;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class SequenceGeneratorService {
	
	public static String generateSequence(DB db, String seq_name) {
	    String sequence_collection = "database_sequences";
	    String sequence_field = "seq";

	    DBCollection seq = db.getCollection(sequence_collection);

	    DBObject query = new BasicDBObject();
	    query.put("id", seq_name);
	    
	    DBObject change = new BasicDBObject(sequence_field, 1);
	    DBObject update = new BasicDBObject("$inc", change);

	    DBObject res = seq.findAndModify(query, new BasicDBObject(), new BasicDBObject(), false, update, true, true);
	    return res.get(sequence_field).toString();
	}
}
