package com.eteration.simplebanking.services;

import com.eteration.simplebanking.model.entity.*;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public Account findAccount(String accountNumber) {
        Optional<Account> account = this.accountRepository.findByAccountNumber(accountNumber);
        if(account.isPresent()) {
            return account.get();
        }
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found for account number:" + accountNumber);
    }

    public void credit(String accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        Transaction transaction = new DepositTransaction(amount);
        transaction.setDate(new Date());
        account.post(transaction);
        transactionRepository.save(transaction);
    }

    public void debit(String accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        Transaction transaction = new WithdrawalTransaction(amount);
        account.post(transaction);
        transaction.setDate(new Date());
        transactionRepository.save(transaction);
    }

    public void debitWithPhoneTransaction(String accountNumber, PhoneBillPaymentTransaction phoneTransaction) {
        Account account = findAccount(accountNumber);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        Transaction transaction = new PhoneBillPaymentTransaction(phoneTransaction.getOperator(), phoneTransaction.getPhoneNumber(), phoneTransaction.getAmount());
        account.post(transaction);
        transaction.setDate(new Date());
        transactionRepository.save(transaction);
    }

    public Account createAccount(Account account) {
        this.accountRepository.save(account);
        account.getTransactions().forEach(data -> {
            data.setAccount(account);
            data.setDate(new Date());
            transactionRepository.save(data);
        });
        return account;
    }
}
