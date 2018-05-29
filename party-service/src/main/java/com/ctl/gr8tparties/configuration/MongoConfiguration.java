package com.ctl.gr8tparties.configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
@Profile("!unit-test")
public class MongoConfiguration extends AbstractMongoConfiguration {

    @Value("${mongo.uri}")
    private String mongoUri;

    @Value("${mongo.database}")
    private String database;

    @Override
    public MongoClient mongoClient() {
        return new MongoClient(new MongoClientURI(mongoUri));
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }
}
