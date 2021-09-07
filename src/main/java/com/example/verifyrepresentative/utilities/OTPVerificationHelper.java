package com.example.verifyrepresentative.utilities;

import com.example.verifyrepresentative.DTO.InquiryResponseDTO;
import com.example.verifyrepresentative.DTO.StatusDTO;
import com.example.verifyrepresentative.model.OTP;

import java.util.Date;

public class OTPVerificationHelper {

    public static InquiryResponseDTO verifyOTPHelper(String inputOTP, OTP OTP) {

        boolean success = false;
        String ActualOTP = OTP.getOtp();
        if (inputOTP.equals(ActualOTP))
            success = true;

        // create Response
        InquiryResponseDTO response = new InquiryResponseDTO();
        if (success) {
            Date d1 = OTP.getRequestDate();
            Date d2 = new Date();
            long difference_In_Time = d2.getTime() - d1.getTime();
            long difference_In_Minutes = (difference_In_Time / (1000 * 60));

            StatusDTO status = new StatusDTO();
            Date date = new Date();
            if (difference_In_Minutes > 10) {
                status.setCode("1");
                status.setMessage("Expired OTP");
            } else {
                status.setCode("0");
                status.setMessage("Correct OTP");
            }


            response.setStatus(status);
            response.setResponseDate(date);
        } else {
            StatusDTO status = new StatusDTO();
            status.setCode("1");
            status.setMessage("Wrong OTP");
            Date date = new Date();
            response.setStatus(status);
            response.setResponseDate(date);
        }
    return response;
    }
}
