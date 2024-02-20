package ${instance.packageName};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ${instance.className} {

    public static void main(String[] args) {
        SpringApplication.run(${instance.className}.class, args);
    }

}