package com.be05.sns.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ResponseDto {

    private String message;

    public ResponseDto toMessage(String msg) {
        ResponseDto response = new ResponseDto();
        response.setMessage(msg);
        return response;
    }
}