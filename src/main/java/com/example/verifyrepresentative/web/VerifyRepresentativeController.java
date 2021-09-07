package com.example.verifyrepresentative.web;

import com.example.verifyrepresentative.DTO.InquiryRequestDTO;
import com.example.verifyrepresentative.DTO.InquiryResponseDTO;
import com.example.verifyrepresentative.DTO.ResponseDto;
import com.example.verifyrepresentative.exception.RecordNotFoundException;
import com.example.verifyrepresentative.exception.SMSNotSentException;
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

import static com.example.verifyrepresentative.utilities.InquiryHelper.CreateResponse;
import static com.example.verifyrepresentative.utilities.OTPVerificationHelper.verifyOTPHelper;

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
    public ResponseEntity<InquiryResponseDTO> GenerateOTP(@RequestBody InquiryRequestDTO request) throws RecordNotFoundException, SMSNotSentException {
        // Generates OTP and links OTP to representative in database
        ResponseDto<OTP> smsresponse = service.sendOtpToMobMerchant(request.getServiceRefNumber());
        OTP new_otp = smsresponse.getData();
        // create Response
        InquiryResponseDTO response = CreateResponse("0","OTP Generated Successfully OTP: "+ new_otp.getOtp());
        return new ResponseEntity<InquiryResponseDTO>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/verifyOTP")
    public ResponseEntity<InquiryResponseDTO> verifyOTP(@RequestBody InquiryRequestDTO request) throws RecordNotFoundException {
        if(request.getInquiryAttributes().size() == 0)
            throw new RecordNotFoundException("No OTP is provided");
        String inputOTP = request.getInquiryAttributes().get(0).getValue();
        Representative representative = service.getRepresentativeByPhone(request.getServiceRefNumber());
        OTP OTP = service.getRecentOTPForRepresentative(representative);
        InquiryResponseDTO response = verifyOTPHelper(inputOTP, OTP);
        return new ResponseEntity<InquiryResponseDTO>(response, new HttpHeaders(), HttpStatus.OK);
    }


}
