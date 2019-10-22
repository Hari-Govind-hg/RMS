package com.ibm.rms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("database_sequences")
public class DatabaseSequences {
	
	@Id
    private String id;
    private long seq;
}
