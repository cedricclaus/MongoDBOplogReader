package be.ccl.mongo.springboot;

import be.ccl.mongo.oplog.service.OplogReaderService;
import be.ccl.mongo.springboot.config.MongoDBOplogreaderApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties({MongoDBOplogreaderApplicationProperties.class})
public class MongoDBOplogReaderApplication implements CommandLineRunner {

	@Autowired
	OplogReaderService service;

	public static void main(String[] args) {
		final SpringApplication springApplication = new SpringApplication(MongoDBOplogReaderApplication.class);
		springApplication.setWebEnvironment(false);
		springApplication.run(args);
	}


	@Override
	public void run(String... strings) throws Exception {
		service.execute();
	}
}
