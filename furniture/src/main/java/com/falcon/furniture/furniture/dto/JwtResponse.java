package com.falcon.furniture.furniture.dto;

import lombok.Data;
import org.joda.time.DateTime;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private DateTime expireTime;
}
