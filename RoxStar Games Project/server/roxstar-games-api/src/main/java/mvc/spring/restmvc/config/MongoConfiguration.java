package mvc.spring.restmvc.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "mvc.spring.restmvc.dao")
public class MongoConfiguration extends AbstractMongoConfiguration {

    @Value("${mongo.db.name}")
    private String mongoDbName;

    @Value("${mongo.db.host}")
    private String mongoUri;

    @Override
    public MongoClient mongoClient() {
        return new MongoClient(mongoUri);
    }

    @Override
    protected String getDatabaseName() {
        return mongoDbName;
    }
}
