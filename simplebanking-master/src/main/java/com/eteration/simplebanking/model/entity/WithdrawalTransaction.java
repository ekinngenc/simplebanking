package com.eteration.simplebanking.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "withdrawal_transaction")
public class WithdrawalTransaction extends Transaction {

    public WithdrawalTransaction() {
    }

    public WithdrawalTransaction(double amount) {
        super.setType("WithdrawalTransaction");
        super.setAmount(amount);
    }

}


