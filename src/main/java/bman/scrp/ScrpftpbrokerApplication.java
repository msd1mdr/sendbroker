package bman.scrp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;


@SpringBootApplication
public class ScrpftpbrokerApplication {

	public static void main(String[] args) {
//		SpringApplication.run(ScrpftpbrokerApplication.class, args);
		SpringApplication springApplication = new SpringApplication(ScrpftpbrokerApplication.class);

        springApplication.addListeners(new ApplicationPidFileWriter());     // register PID write to spring boot. It will write PID to file
        springApplication.run(args);
	

	}

}
