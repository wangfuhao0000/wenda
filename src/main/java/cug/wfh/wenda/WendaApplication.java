package cug.wfh.wenda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;

@SpringBootApplication
public class WendaApplication {

    public static void main(String[] args) {
        SpringApplication.run(WendaApplication.class, args);
    }

}
