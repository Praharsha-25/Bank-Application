package com.bank.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.entity.Transaction;
import com.bank.repository.TransactionRepository;
import com.bank.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService{
	@Autowired
	private TransactionRepository tr;

	
	public TransactionServiceImpl(TransactionRepository tr) {
		super();
		this.tr = tr;
	}
	@Override
	public Transaction saveTr(Transaction transaction) {
	    return tr.save(transaction);
	}

	@Override
	public List<Transaction> getTrById() {
		return tr.findAll();
	}
}
