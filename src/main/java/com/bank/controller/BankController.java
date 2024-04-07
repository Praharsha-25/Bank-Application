package com.bank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bank.entity.Bank;
import com.bank.entity.Transaction;
import com.bank.service.BankService;
import com.bank.service.TransactionService;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.InputStreamResource;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class BankController {
	private BankService bankService;
	private TransactionService transactionService;
	
	public BankController(BankService bankService, TransactionService transactionService) {
		super();
		this.bankService = bankService;
		this.transactionService = transactionService;
	}
	@GetMapping("/bank")
	public String bankForm(Model model) {
		Bank bank = new Bank();
		
		model.addAttribute("bank",bank);
		return "bank_form";
	}
	@PostMapping("/users")	
	public String createUser(@ModelAttribute("bank") Bank bank,@RequestParam("image1") MultipartFile image,HttpServletRequest request) throws IOException, SerialException, SQLException {
		byte[] bytes = image.getBytes();
		Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
		bank.setImage(blob);
		bankService.saveUser(bank);
		return "redirect:/users";
	}
	@GetMapping("/users")
	public String listUsers(Model model) {
		model.addAttribute("users",bankService.getAllUsers());
		return "bank_users";
	}
	@GetMapping("users/edit/{accno}")
	public String editUser(@PathVariable Long accno, Model model) {
		model.addAttribute("user",bankService.getUserById(accno));
		return "edit_user";
	}
	@PostMapping("/users/{accno}")
	public String updateUser(@PathVariable Long accno,@ModelAttribute("user") Bank user,Model model, @RequestParam("image1") MultipartFile image,HttpServletRequest request) throws IOException, SerialException, SQLException {
		Bank existingUser = bankService.getUserById(accno);
		byte[] bytes = image.getBytes();
		Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
		user.setImage(blob);
		existingUser.setName(user.getName());
		existingUser.setAge(user.getAge());
		existingUser.setGender(user.getGender());
		existingUser.setBalance(user.getBalance());
		existingUser.setAadhar(user.getAadhar());
		existingUser.setImage(user.getImage());
		bankService.updateUser(existingUser);
		return "redirect:/users";
	}
	@GetMapping("users/delete/{accno}")
	public String deleteUser(@PathVariable Long accno) {
		bankService.deleteUserById(accno);
		return "redirect:/users";
	}
	@GetMapping("user/details/{accno}")
	public String userDetails(@PathVariable Long accno,Model model) {
		model.addAttribute("user", bankService.getUserById(accno));
		return "user_details";
	}
	@GetMapping("/display")
    public ResponseEntity<InputStreamResource> displayImage(@RequestParam Long accno) throws IOException, SQLException {
        Bank bank = bankService.getUserById(accno);
        if (bank == null || bank.getImage() == null) {
            // Return a placeholder image or handle the case where no image is found
            return ResponseEntity.notFound().build();
        }

        InputStream imageStream = bank.getImage().getBinaryStream();

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(imageStream));
    }
	@GetMapping("credit/{accno}")
	public String credit(@PathVariable Long accno, @ModelAttribute("user") Bank user,@RequestParam("credit") Float credit,@RequestParam("balance") Float balance,Model model) {
		Bank existingUser = bankService.getUserById(accno);
		existingUser.setBalance(balance+credit);
		bankService.updateUser(existingUser);
		model.addAttribute("user",bankService.getUserById(accno));
		return "user_details";
	}
	@GetMapping("debit/{accno}")
	public String debit(@PathVariable Long accno, @ModelAttribute("user") Bank user,@RequestParam("debit") Float debit,@RequestParam("balance") Float balance,Model model) {
		Bank existingUser = bankService.getUserById(accno);
		existingUser.setBalance(balance-debit);
		if(existingUser.getBalance()<1000)
		{
			return "error";
		}
		bankService.updateUser(existingUser);
		model.addAttribute("user",bankService.getUserById(accno));
		return "user_details";
	}
	@GetMapping("transfer1")
	public String transfer1() {
		return "transfer";
	}
	@PostMapping("transfer")
	public String transfer(@ModelAttribute("user") Bank user, Model model, 
	                       @RequestParam("amount") Float amount,
	                       @RequestParam("credit") Long creditAccno,
	                       @RequestParam("debit") Long debitAccno) {
	    Bank creditUser = bankService.getUserById(creditAccno);
	    Bank debitUser = bankService.getUserById(debitAccno);
	    
	    Float balance1 = creditUser.getBalance();
	    Float balance2 = debitUser.getBalance();
	    debitUser.setBalance(balance2 - amount);
	    creditUser.setBalance(balance1 + amount); 
	    
	    if (debitUser.getBalance() < 1000) {
	        return "error";
	    }
	    
	    bankService.updateUser(creditUser);
	    bankService.updateUser(debitUser);
	    saveTransaction(creditUser.getAccno(),debitUser,new Date(),amount+"/- credited");
	    saveTransaction(debitUser.getAccno(),creditUser,new Date(),amount+"/- debited");
	    return "redirect:/users";
	}

	@GetMapping("transaction")
	public String transc() {
		return "transaction";
	}
	@PostMapping("transaction")
	public String transactionDetails(@RequestParam("accno") Long accno,Model model) {
		List<Transaction> transc = transactionService.getTrById();
		List<Transaction> trList = new ArrayList<>();
		for(Transaction transaction:transc) {
			if(transaction.getAccno()==accno) {
				trList.add(transaction);
			}
		}
		model.addAttribute("transactions",trList);
		return "transaction_details";
	}
	public void saveTransaction(Long accno, Bank bank,Date date,String description) {
		Transaction transc = new Transaction();
		transc.setAccno(accno);
		transc.setAccno1(bank);
		transc.setDate(date);
		transc.setDescription(description);
		transactionService.saveTr(transc);
		return ;
	}
}
