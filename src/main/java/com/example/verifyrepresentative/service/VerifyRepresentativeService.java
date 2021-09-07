package com.example.verifyrepresentative.service;

import com.example.verifyrepresentative.exception.RecordNotFoundException;
import com.example.verifyrepresentative.exception.SMSNotSentException;
import com.example.verifyrepresentative.model.OTP;
import com.example.verifyrepresentative.model.Representative;
import com.example.verifyrepresentative.repository.OTPRepo;
import com.example.verifyrepresentative.repository.RepresentativeRepo;
import com.example.verifyrepresentative.DTO.ResponseDto;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class VerifyRepresentativeService {

    final static Logger LOGGER = Logger.getLogger(VerifyRepresentativeService.class);
    @Autowired
    Environment environment;
    @Autowired
    RepresentativeRepo RepresentativeRepository;
    @Autowired
    OTPRepo OTPRepository;

    public List<Representative> getAllRepresentatives()
    {
        List<Representative> RepresentativeList = RepresentativeRepository.findAll();

        if(RepresentativeList.size() > 0) {
            return RepresentativeList;
        } else {
            return new ArrayList<Representative>();
        }
    }
    public List<OTP> getAllOTPs()
    {
        List<OTP> OTPList = OTPRepository.findAll();

        if(OTPList.size() > 0) {
            return OTPList;
        } else {
            return new ArrayList<OTP>();
        }
    }
    public OTP getRecentOTPForRepresentative(Representative entity) throws RecordNotFoundException {
        OTP recent_otp = OTPRepository.findTopByRepresentativeOrderByRequestDateDesc(entity);

        if(recent_otp != null) {
            return recent_otp;
        } else {
            LOGGER.error("No OTP generated for this representative");
            throw new RecordNotFoundException("NO OTP generated for this Representative");
        }
    }
    public Representative CreateRepresentative(Representative entity)
    {
        entity = RepresentativeRepository.save(entity);
        return entity;
    }

    public String generateOTP() {
        LOGGER.info("generating new OTP");
        int randomPin = (int) (Math.random() * 9000) + 1000;
        String otp = String.valueOf(randomPin);
        LOGGER.info("New OTP value = " + otp);
        return otp;
    }
        public ResponseDto<OTP> sendOtpToMobMerchant(String MobileNumber) throws RecordNotFoundException, SMSNotSentException {

            ResponseDto responseDto = new ResponseDto();
            String code = generateOTP();
            Representative salesRep = RepresentativeRepository.findByPhoneNumber(MobileNumber);

            if (salesRep == null)
                throw new RecordNotFoundException("No representative found with this phone number");


            boolean smsSent = sendSMS(salesRep.getPhoneNumber(), code);
            /*
            if (!smsSent) {
                throw new SMSNotSentException("Failed to Send SMS");
            }*/


            OTP new_otp = new OTP();
            new_otp.setOtp(code);
            new_otp.setRepresentative(salesRep);
            new_otp.setRequestDate(new Date());
            OTPRepository.save(new_otp);

            responseDto.setData(new_otp);
            LOGGER.info("new OTP generated with value+:"+code);
            return responseDto;
        }

    public boolean sendSMS(String msisdn, String otp) throws RecordNotFoundException {

        RestTemplate restTemplate = new RestTemplate();

        LOGGER.info("starting to send sms message to msisdn = " + msisdn);

        String smsUrl = environment.getProperty("sms.url");
        String msg = environment.getProperty("message.sms");
        String sender = environment.getProperty("sms.sender");

        StringBuilder params = new StringBuilder("?");
        params.append("msisdn=");
        params.append(msisdn);
        params.append("&msg=");
        params.append(msg.replace("otp", otp));
        params.append("&sender=");
        params.append(sender);

        ResponseEntity<List> response = null;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            response = restTemplate.exchange(smsUrl + params, HttpMethod.GET, entity, List.class);

        } catch (Exception ex) {

            LOGGER.error(ex.getMessage());
            return false;
        }

        if (response == null || response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            LOGGER.error("Failed to send SMS");
            return false;
        }
        return true;
    }

    public Representative getRepresentativeById(Long id)
    {
        return RepresentativeRepository.getById(id);
    }
    //public OTP getOTPByValue(String Value){return OTPRepository.findByOtp(Value);}
    public Representative getRepresentativeByPhone(String phone) throws RecordNotFoundException {
        Representative result = RepresentativeRepository.findByPhoneNumber(phone);
        if(result == null)
            throw new RecordNotFoundException("No Representative found for this number");
        return result;
    }



}
