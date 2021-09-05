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

    @Column(name = "request_date")
    private Date request_date;

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

    public Date getRequest_date() {
        return request_date;
    }

    public void setRequest_date(Date request_date) {
        this.request_date = request_date;
    }

    public Representative getRepresentative() {
        return representative;
    }

    public void setRepresentative(Representative representative_id) {
        this.representative = representative_id;
    }
}



