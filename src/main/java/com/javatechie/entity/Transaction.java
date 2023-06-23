package com.javatechie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "users_id", foreignKey = @ForeignKey(name = "FK_USER_TRANSACTION"))
    private UserInfo userId;
    private int amount;
    @Column(name = "transaction_date")
    private Date transactionDate;

}
