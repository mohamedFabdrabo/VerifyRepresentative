package com.example.verifyrepresentative.utilities;

import com.example.verifyrepresentative.DTO.InquiryResponseDTO;
import com.example.verifyrepresentative.DTO.StatusDTO;

import java.util.Date;

public class InquiryHelper {
    public static InquiryResponseDTO CreateResponse(String code, String message)
    {
        StatusDTO status = new StatusDTO();
        status.setCode(code);
        status.setMessage(message);
        Date date = new Date();
        InquiryResponseDTO response = new InquiryResponseDTO();
        response.setStatus(status);
        response.setResponseDate(date);
        return response;
    }
}
