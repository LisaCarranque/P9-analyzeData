package analyzeData;

import analyzeData.config.Generated;
import analyzeData.proxy.MedicalNotesProxy;
import analyzeData.proxy.SearchPatientProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * This is the main class of analyzeData microservice
 */
@Slf4j
@EnableSwagger2
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = {"analyzeData"})
@EnableFeignClients(clients = {SearchPatientProxy.class, MedicalNotesProxy.class})
@Generated
public class Application {

    public static void main(String[] args) {
        log.info("Launch analyzeData module");
        SpringApplication.run(Application.class, args);
    }

}
