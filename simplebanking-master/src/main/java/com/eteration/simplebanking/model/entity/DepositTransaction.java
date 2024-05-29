package com.eteration.simplebanking.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "deposit_transaction")
public class DepositTransaction extends Transaction {

    public DepositTransaction() {
    }

    public DepositTransaction(double amount) {
        super.setType("DepositTransaction");
        super.setAmount(amount);
    }
}
