package com.example.verifyrepresentative.repository;

import com.example.verifyrepresentative.model.OTP;
import com.example.verifyrepresentative.model.Representative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OTPRepo extends JpaRepository<OTP, Long> {
    List<OTP> findByRepresentative(Representative representative_id);
    OTP findTopByRepresentativeOrderByRequestDateDesc(Representative representative_id);
    //OTP findByOtp(String otp);
}
