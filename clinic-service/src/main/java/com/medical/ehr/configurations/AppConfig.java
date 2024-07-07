package com.medical.ehr.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // @Bean
    // public RestTemplate restTemplate(RestTemplateBuilder builder) {
    //     return builder
    //             .additionalInterceptors((request, body, execution) -> {
    //                 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //                 if (authentication != null && authentication.getCredentials() instanceof String) {
    //                     String token = (String) authentication.getCredentials();
    //                     request.getHeaders().setBearerAuth(token);
    //                 }
    //                 return execution.execute(request, body);
    //             })
    //             .build();
    // }


}
