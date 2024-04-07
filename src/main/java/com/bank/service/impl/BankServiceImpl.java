package com.bank.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.entity.*;
import com.bank.repository.BankRepository;
import com.bank.service.BankService;

@Service
public class BankServiceImpl implements BankService{
	@Autowired
	private BankRepository bankRepo;
	public BankServiceImpl(BankRepository bankRepo) {
		super();
		this.bankRepo = bankRepo;
	}

	@Override
	public List<Bank> getAllUsers(){
		return bankRepo.findAll();
	}
	@Override
	public Bank saveUser(Bank bank) {
		return bankRepo.save(bank);
	}
	@Override
	public Bank getUserById(Long id) {
		return bankRepo.findById(id).orElse(null);
	}
	@Override
	public Bank updateUser(Bank bank) {
		return bankRepo.save(bank);
	}
	

	@Override 
	public void deleteUserById(Long id) {
		bankRepo.deleteById(id);
	}
}
