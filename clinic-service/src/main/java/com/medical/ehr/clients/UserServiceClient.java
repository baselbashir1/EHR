package com.medical.ehr.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://localhost:8081/user")
public interface UserServiceClient {

    @GetMapping("/getByUsername")
    UserDetails findByUsername(@RequestParam("username") String username);

}