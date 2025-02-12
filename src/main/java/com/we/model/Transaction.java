package com.we.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transactionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private TransactionCategory category;

    @Column(nullable = false)
    private double amount;

    @Temporal(TemporalType.DATE)
    private Date transactionDate=new Date();

    private String description;
}
