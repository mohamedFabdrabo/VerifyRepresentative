package com.example.verifyrepresentative.service;

import com.example.verifyrepresentative.exception.RecordNotFoundException;
import com.example.verifyrepresentative.model.OTP;
import com.example.verifyrepresentative.model.Representative;
import com.example.verifyrepresentative.repository.OTPRepo;
import com.example.verifyrepresentative.repository.RepresentativeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VerifyRepresentativeService {

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
        List<OTP> OTPList = OTPRepository.findByRepresentative(entity);
        List<OTP> OTPListSorted = OTPRepository.findByRepresentativeOrderByRequestDate(entity);

        if(OTPList.size() > 0) {
            /*OTP recent = OTPList.get(0);
            Date recentDate = recent.getRequestDate();
            for(int i =0;i<OTPList.size();i++)
            {
                OTP current =OTPList.get(i);
                Date currentDate = current.getRequestDate();
                if(currentDate.compareTo(recentDate) > 0)
                {
                    recent = current;
                    recentDate = currentDate;
                }
            }
            return recent;*/
            return OTPListSorted.get(OTPListSorted.size()-1);
        } else {
            throw new RecordNotFoundException("NO OTP Found for this Representative");
        }
    }
    public Representative CreateRepresentative(Representative entity)
    {
        entity = RepresentativeRepository.save(entity);
        return entity;
    }
    public OTP GenerateOTP(Representative entity)
    {
        int random_number = (int) (Math.random()*10000);
        OTP new_otp = new OTP();
        new_otp.setOtp(""+random_number);
        new_otp.setRepresentative(entity);
        new_otp.setRequestDate(new Date());
        OTPRepository.save(new_otp);
        return new_otp;
    }
    public Representative getRepresentativeById(Long id)
    {
        return RepresentativeRepository.getById(id);
    }
    //public OTP getOTPByValue(String Value){return OTPRepository.findByOtp(Value);}
    public Representative getRepresentativeByPhone(String phone)
    {
        return RepresentativeRepository.findByPhoneNumber(phone);
    }



}
