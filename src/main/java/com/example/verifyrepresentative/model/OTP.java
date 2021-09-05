package com.example.verifyrepresentative.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="OTP_TOKENS")
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "otp")
    private String otp;

    @Column(name = "requestDate")
    private Date requestDate;

    @ManyToOne
    @JoinColumn(name = "representative")
    private Representative representative;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Representative getRepresentative() {
        return representative;
    }

    public void setRepresentative(Representative representative_id) {
        this.representative = representative_id;
    }
}



