package com.montrackjpa.JPASpringBootExercises.transactions;


import com.montrackjpa.JPASpringBootExercises.currencies.entitity.Currency;
import com.montrackjpa.JPASpringBootExercises.pocket.entity.Pocket;
import com.montrackjpa.JPASpringBootExercises.transactiontype.TransactionType;
import com.montrackjpa.JPASpringBootExercises.wallets.entity.Wallet;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Data
@Table(name = "transactions")
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transactions_id_gen")
    @SequenceGenerator(name = "transactions_id_gen", sequenceName = "transactions_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private long id;


    @Column(name = "transaction_name", nullable = false)
    private String name;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "transaction_date")
    private Instant transactionDate;
    @Min(value = 0, message = "amount must be non-negative")
    @Column(name = "amount", nullable = false)
    private int amount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_type_id", referencedColumnName = "id")
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "pocket_id", nullable = false)
    private Pocket pocket;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "attachment", nullable = false)
    private String attachment;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;


    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;



    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;


    @Column(name = "deleted_at")
    private Instant deletedAt;


    @PrePersist
    protected void onCreate(){
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }
}
