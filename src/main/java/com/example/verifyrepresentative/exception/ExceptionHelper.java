package com.example.verifyrepresentative.exception;

import com.example.verifyrepresentative.DTO.InquiryResponseDTO;
import com.example.verifyrepresentative.DTO.StatusDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionHelper {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);

    @ExceptionHandler(value = { RecordNotFoundException.class })
    public ResponseEntity<InquiryResponseDTO> handleRecordNotFoundException(RecordNotFoundException ex) {
        StatusDTO status = new StatusDTO();
        status.setCode("1");
        status.setMessage("Record Not Found Exception: "+ex.getMessage());
        Date date = new Date();
        InquiryResponseDTO response = new InquiryResponseDTO();
        response.setStatus(status);
        response.setResponseDate(date);
        logger.error("Record Not Found Exception: ",ex.getMessage());
        return new ResponseEntity<InquiryResponseDTO>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = { SMSNotSentException.class })
    public ResponseEntity<InquiryResponseDTO> handleSMSNotSentException(RecordNotFoundException ex) {
        StatusDTO status = new StatusDTO();
        status.setCode("1");
        status.setMessage("SMS Not Sent Exception: "+ex.getMessage());
        Date date = new Date();
        InquiryResponseDTO response = new InquiryResponseDTO();
        response.setStatus(status);
        response.setResponseDate(date);
        logger.error("SMS Not Sent Exception: ",ex.getMessage());
        return new ResponseEntity<InquiryResponseDTO>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
