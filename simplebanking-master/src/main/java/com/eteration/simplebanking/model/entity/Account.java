package com.eteration.simplebanking.model.entity;

import com.eteration.simplebanking.model.InsufficientBalanceException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class Account implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "owner")
    private String owner;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "balance")
    private double balance;

    @OneToMany(mappedBy = "account", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public Account(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) throws InsufficientBalanceException {
        if (this.balance < amount) {
            throw new InsufficientBalanceException("Insufficient balance!");
        }
        this.balance -= amount;
    }

    public void post(Transaction transaction) {
        this.transactions.add(transaction);
        if (transaction instanceof DepositTransaction) {
            this.deposit(transaction.getAmount());
        } else if (transaction instanceof WithdrawalTransaction || transaction instanceof PhoneBillPaymentTransaction) {
            try {
                this.withdraw(transaction.getAmount());
            } catch (InsufficientBalanceException e) {
                System.out.println("Insufficient balance!" +e.getMessage());
            }
        }
    }
    public List<Transaction> getTransactions() {
        return transactions;
    }
}
