package com.eteration.simplebanking.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "phone_transaction")
public class PhoneBillPaymentTransaction extends Transaction {

    @Column(name = "operator")
    private String operator;

    @Column(name = "phone_number")
    private String phoneNumber;

    public PhoneBillPaymentTransaction() {
    }

    public PhoneBillPaymentTransaction(String operator, String phone, double amount) {
        super(amount);
        super.setType("PhoneBillPaymentTransaction");
        this.operator = operator;
        this.phoneNumber = phone;
    }
}


