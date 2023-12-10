package dot.compta.backend;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// TODO version and title need to be extracted from the pom.xml
@OpenAPIDefinition(
        info = @Info(
                title = "DotCompta",
                version = "0.0.0-SNAPSHOT"))
public class DotComptaBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(DotComptaBackEndApplication.class, args);
    }

}
