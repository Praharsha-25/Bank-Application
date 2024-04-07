package com.bank.entity;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name="transactions")
public class Transaction {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long transactionId;
	@Column(name="accno",nullable=false)
	private Long accno;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "accno_inv", nullable = false)
	private Bank accno1;
	@Column(name="date", nullable=false)
	private Date date;
	@Column(name="description",nullable=false)
	private String description;
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	public Long getAccno() {
		return accno;
	}
	public void setAccno(Long accno) {
		this.accno = accno;
	}
	public Bank getAccno1() {
		return accno1;
	}
	public void setAccno1(Bank accno1) {
		this.accno1 = accno1;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
