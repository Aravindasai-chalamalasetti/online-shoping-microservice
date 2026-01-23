package com.orderservice.orderservice;

import com.orderservice.dto.GeneralHttpResponseDTO;
import com.orderservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userdetails-service")
public interface UserClient {
    @GetMapping("/api/user/findUid/{userId}")
    public GeneralHttpResponseDTO<UserDTO> findUuid(@PathVariable String userId);
}
