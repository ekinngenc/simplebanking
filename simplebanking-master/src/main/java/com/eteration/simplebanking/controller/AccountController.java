package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.entity.Account;
import com.eteration.simplebanking.model.entity.DepositTransaction;
import com.eteration.simplebanking.model.entity.WithdrawalTransaction;
import com.eteration.simplebanking.model.entity.PhoneBillPaymentTransaction;
import com.eteration.simplebanking.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/account/v1")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/accountNumber/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(this.accountService.findAccount(accountNumber));
    }

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(@PathVariable String accountNumber, @RequestBody DepositTransaction depositTransaction) {
        accountService.credit(accountNumber, depositTransaction.getAmount());
        return ResponseEntity.ok(new TransactionStatus("OK", UUID.randomUUID().toString()));
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(@PathVariable String accountNumber, @RequestBody WithdrawalTransaction withdrawalTransaction) {
        accountService.debit(accountNumber, withdrawalTransaction.getAmount());
        return ResponseEntity.ok(new TransactionStatus("OK", UUID.randomUUID().toString()));
	}

    @PostMapping("/debitWithPhone/{accountNumber}")
    public ResponseEntity<TransactionStatus> debitWithPhoneTransaction(@PathVariable String accountNumber, @RequestBody PhoneBillPaymentTransaction paymentTransaction) {
        accountService.debitWithPhoneTransaction(accountNumber, paymentTransaction);
        return ResponseEntity.ok(new TransactionStatus("OK", UUID.randomUUID().toString()));
    }

    @PostMapping("/createAccount")
    public ResponseEntity<Account> createAccount() {
        Account account = new Account();
        account.setOwner("Kerem Karaca");
        account.setBalance(950);
        account.setAccountNumber("D-565");
        account.setTransactions(new ArrayList<>());
        account.getTransactions().add(new DepositTransaction(413));
        account.getTransactions().add(new PhoneBillPaymentTransaction("Vodafone", "555656589", 313));
        account.getTransactions().add(new WithdrawalTransaction(323));
        return ResponseEntity.ok(this.accountService.createAccount(account));
    }
}