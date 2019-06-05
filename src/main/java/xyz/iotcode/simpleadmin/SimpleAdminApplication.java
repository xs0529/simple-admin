package xyz.iotcode.simpleadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.wf.jwtp.configuration.EnableJwtPermission;

@EnableJwtPermission
@SpringBootApplication
public class SimpleAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleAdminApplication.class, args);
    }

}
