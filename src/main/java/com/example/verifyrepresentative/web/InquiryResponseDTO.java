package com.example.verifyrepresentative.web;


import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InquiryResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@NotNull
	private StatusDTO status;
	private Date responseDate;
	@NotNull
	private List<BillDTO> bills;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<ExtraDataDTO> extraData;

	public StatusDTO getStatus() {
		return status;
	}

	public void setStatus(StatusDTO status) {
		this.status = status;
	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		if (responseDate == null) {
			this.responseDate = new Date();
		} else {
			this.responseDate = responseDate;
		}
	}

	public List<BillDTO> getBills() {
		return bills;
	}

	public void setBills(List<BillDTO> bills) {
		this.bills = bills;
	}

	public List<ExtraDataDTO> getExtraData() {
		return extraData;
	}

	public void setExtraData(List<ExtraDataDTO> extraData) {
		this.extraData = extraData;
	}

	@Override
	public String toString() {
		String s = "InquiryResponseDTO [";
		if (status != null) {
			s += "status=" + status;
		}
		s += ", responseDate=" + responseDate;
		if (bills != null) {
			s += ", bills=";
			for (BillDTO billDTO : bills) {
				s += billDTO.toString() + " , ";
			}
		}
		if (extraData != null) {
			s += ", extraData= ";
			for (ExtraDataDTO extraDataDTO : extraData) {
				s += extraDataDTO.toString() + " , ";
			}
		}
		s += " ]";
		return s;
	}
	public static class BillDTO implements Serializable {

		private static final long serialVersionUID = 1L;
		private String billRefNumber;
		private Date issueDate;
		private Date dueDate;
		@Min(value = 0)
		private Double amount;
		@JsonInclude(JsonInclude.Include.NON_NULL)
		private PaymentRangeDTO paymentRange;

		public String getBillRefNumber() {
			return billRefNumber;
		}

		public void setBillRefNumber(String billRefNumber) {
			this.billRefNumber = billRefNumber;
		}

		public Date getIssueDate() {
			return issueDate;
		}

		public void setIssueDate(Date issueDate) {
			this.issueDate = issueDate;
		}

		public Date getDueDate() {
			return dueDate;
		}

		public void setDueDate(Date dueDate) {
			this.dueDate = dueDate;
		}

		public PaymentRangeDTO getPaymentRange() {
			return paymentRange;
		}

		public void setPaymentRange(PaymentRangeDTO paymentRange) {
			this.paymentRange = paymentRange;
		}

		public Double getAmount() {
			return amount;
		}

		public void setAmount(Double amount) {
			this.amount = amount;
		}

		@Override
		public String toString() {
			String s = "BillDTO [billRefNumber=" + billRefNumber
					+ ", issueDate=" + issueDate + ", dueDate=" + dueDate
					+ ", amount=" + amount;
			if (paymentRange != null) {
				s += ", paymentRange=" + paymentRange.toString() + "]";
			} else {
				s += ", paymentRange=" + paymentRange + "]";
			}
			return s;
		}


		public static class PaymentRangeDTO implements Serializable {

			private static final long serialVersionUID = 1L;
			@Min(value=0)
			private Double lower;
			@Min(value=0)
			private Double upper;
			private String currencyCode;

			public Double getLower() {
				return lower;
			}

			public void setLower(Double lower) {
				this.lower = lower;
			}

			public Double getUpper() {
				return upper;
			}

			public void setUpper(Double upper) {
				this.upper = upper;
			}

			public String getCurrencyCode() {
				return currencyCode;
			}

			public void setCurrencyCode(String currencyCode) {
				this.currencyCode = currencyCode;
			}

			@Override
			public String toString() {
				return "PaymentRange [lower=" + lower + ", upper=" + upper
						+ ", currencyCode=" + currencyCode + "]";
			}

		}
	}
}
