package com.example.verifyrepresentative.web;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InquiryRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String requestId;
	private String serviceRefNumber;
	private Date requestDate;
	
	private List<InquiryAttribute>  inquiryAttributes ;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getServiceRefNumber() {
		return serviceRefNumber;
	}

	public void setServiceRefNumber(String serviceRefNumber) {
		this.serviceRefNumber = serviceRefNumber;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

    public List<InquiryAttribute> getInquiryAttributes() {
        return inquiryAttributes;
    }

    public void setInquiryAttributes(List<InquiryAttribute> inquiryAttributes) {
        this.inquiryAttributes = inquiryAttributes;
    }

    @Override
    public String toString() {
        return "InquiryRequestDTO{" +
                "requestId='" + requestId + '\'' +
                ", serviceRefNumber='" + serviceRefNumber + '\'' +
                ", requestDate=" + requestDate +
                ", inquiryAttributes=" + inquiryAttributes +
                '}';
    }
}
