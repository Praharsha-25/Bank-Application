package com.bank.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.entity.Bank;
@Service
public interface BankService {
	List<Bank> getAllUsers();
	Bank saveUser(Bank bank);
	Bank getUserById(Long id);
	Bank updateUser(Bank bank);
	void deleteUserById(Long id);
}
