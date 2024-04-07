package com.bank.entity;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="bank_dtls1")
public class Bank {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long accno;
	@Column(name="Name",nullable=false)
	private String Name;
	@Column(name="age",nullable=false)
	private Integer age;
	@Column(name="gender", nullable=false)
	private String gender;
	@Column(name="balance", nullable=false)
	private Float balance;
	@Column(name="aadhar", nullable=false, unique=true)
	private Long aadhar;
	@Lob
	private Blob image;
	@OneToMany(mappedBy="accno1", cascade=CascadeType.ALL)
	private List<Transaction> transaction = new ArrayList<>();
	private Date date = new Date();
	public Long getAccno() {
		return accno;
	}
	public void setAccno(Long accno) {
		this.accno = accno;
	}
	
	public List<Transaction> getTransaction() {
		return transaction;
	}
	public void setTransaction(List<Transaction> transaction) {
		this.transaction = transaction;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Float getBalance() {
		return balance;
	}
	public void setBalance(Float balance) {
		this.balance = balance;
	}
	public Long getAadhar() {
		return aadhar;
	}
	public void setAadhar(Long aadhar) {
		this.aadhar = aadhar;
	}
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image) {
		this.image = image;
	}
	public Date getDate() {
    	return date;
	}
	public void addTransaction(Transaction transaction1) {
        transaction.add(transaction1);
    }
}
