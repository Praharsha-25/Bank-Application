package com.bank.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.entity.Transaction;

@Service
public interface TransactionService {
	Transaction saveTr(Transaction transaction);
	List<Transaction> getTrById();
}
