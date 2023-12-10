package dot.compta.backend.configs;

import dot.compta.backend.constants.APIConstants;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private final CorsEndpointProperties corsEndpointProperties;

    public WebConfig(CorsEndpointProperties corsEndpointProperties) {
        this.corsEndpointProperties = corsEndpointProperties;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping(APIConstants.ALL_MAPPINGS)
                .allowedOrigins(covertListToStringCommaSeparated(corsEndpointProperties.getAllowedOrigins()))
                .allowedMethods(covertListToStringCommaSeparated(corsEndpointProperties.getAllowedMethods()));
    }

    private String[] covertListToStringCommaSeparated(List<String> elements) {
        return elements.toArray(new String[0]);
    }
}