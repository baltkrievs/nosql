package by.darashchonak.mentoring.nosql.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import java.util.Arrays;

/**
 * Created by Mikhail_Darashchonak on 6/23/17.
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public TemplateResolver templateResolver() {
        TemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        return templateResolver;
    }

    @Bean(name = "collection")
    public MongoCollection<Document> collection(){

        char[] password = "#password.1234".toCharArray();

        MongoCredential credential = MongoCredential.createCredential("user", "tasks", password);
        MongoClientOptions.Builder optionsBuilder = new MongoClientOptions.Builder();
        optionsBuilder.maxWaitTime(3000);
        MongoClientOptions options = optionsBuilder.build();

        ServerAddress server = new ServerAddress("localhost", 27017);
        MongoClient mongoClient = new MongoClient(server, Arrays.asList(credential), options);

        MongoDatabase database = mongoClient.getDatabase("tasks");
        MongoCollection<Document> collection = database.getCollection("tasks");

        return collection;
    }
}
