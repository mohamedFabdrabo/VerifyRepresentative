package com.example.verifyrepresentative.web;

import com.example.verifyrepresentative.exception.RecordNotFoundException;
import com.example.verifyrepresentative.model.OTP;
import com.example.verifyrepresentative.model.Representative;
import com.example.verifyrepresentative.service.VerifyRepresentativeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/")
public class VerifyRepresentativeController {
    private static final Logger logger = LoggerFactory.getLogger(VerifyRepresentativeController.class);
    @Autowired
    VerifyRepresentativeService service;

    @PostMapping("/AddRepresentative")
    public ResponseEntity<Representative> createOrUpdateEmployee(@RequestBody Representative representative)
    {
        Representative updated = service.CreateRepresentative(representative);
        return new ResponseEntity<Representative>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/generateOTP")
    public ResponseEntity<InquiryResponseDTO> GenerateOTP(@RequestBody InquiryRequestDTO request) throws RecordNotFoundException
    {
        Representative representative = service.getRepresentativeByPhone(request.getServiceRefNumber());
        if(representative==null)
        {
            logger.error("Invalid Generation : Phone number not found");
            throw new RecordNotFoundException("Invalid Number");
        }

        // Generates OTP and links OTP to representative in database
        ResponseDto<OTP> smsresponse = service.sendOtpToMobMerchant(representative.getPhoneNumber());
        if(smsresponse.getStatus().getCode().equals("400"))
        {
            logger.error("SMS Not : Phone number not found");
            throw new RecordNotFoundException("Message Not Sent");
        }
        OTP new_otp = smsresponse.getData();

        // create Response
        StatusDTO status = new StatusDTO();
        status.setCode("0");
        status.setMessage("GeneratedSuccessfully OTP: "+ new_otp.getOtp());
        Date date = new Date();

        InquiryResponseDTO response = new InquiryResponseDTO();
        response.setStatus(status);
        response.setResponseDate(date);
        return new ResponseEntity<InquiryResponseDTO>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/verifyOTP")
    public ResponseEntity<InquiryResponseDTO> verifyOTP(@RequestBody InquiryRequestDTO request) throws RecordNotFoundException {
        boolean success = false;
        Representative representative = service.getRepresentativeByPhone(request.getServiceRefNumber());
        if(representative==null)
            throw new RecordNotFoundException("Invalid Number");

        String inputOTP = request.getInquiryAttributes().get(0).getValue();
        OTP OTP = service.getRecentOTPForRepresentative(representative);
        String ActualOTP = OTP.getOtp();
        if(inputOTP.equals(ActualOTP))
            success = true;

        // create Response
        InquiryResponseDTO response = new InquiryResponseDTO();
        if(success)
        {
            Date d1 = OTP.getRequestDate();
            Date d2 = new Date();
            long difference_In_Time = d2.getTime() - d1.getTime();
            long difference_In_Minutes = (difference_In_Time / (1000 * 60));

            StatusDTO status = new StatusDTO();
            Date date = new Date();
            if(difference_In_Minutes > 10)
            {
                status.setCode("1");
                status.setMessage("Expired OTP");
            }
            else {
                status.setCode("0");
                status.setMessage("Correct OTP");
            }


            response.setStatus(status);
            response.setResponseDate(date);
        }
        else
        {
            StatusDTO status = new StatusDTO();
            status.setCode("1");
            status.setMessage("Wrong OTP");
            Date date = new Date();
            response.setStatus(status);
            response.setResponseDate(date);
        }
        return new ResponseEntity<InquiryResponseDTO>(response, new HttpHeaders(), HttpStatus.OK);
    }


}
