package com.example.verifyrepresentative.model;

import javax.persistence.*;

@Entity
@Table(name="Representatives")
public class Representative {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Rep_SEQ")
    @SequenceGenerator(sequenceName = "Rep_SEQ", allocationSize = 1, name = "Rep_SEQ")
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;

    @Column(name="address")
    private String address;

    @Column(name="name")
    private String name;

    @Column(name="nationalID")
    private String nationalID;

    @Column(name="phoneNumber", nullable=false, length=14)
    private String phoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
