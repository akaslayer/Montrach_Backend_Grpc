package com.montrackjpa.JPASpringBootExercises.wallets.entity;


import com.montrackjpa.JPASpringBootExercises.currencies.entitity.Currency;
import com.montrackjpa.JPASpringBootExercises.pocket.entity.Pocket;
import com.montrackjpa.JPASpringBootExercises.transactions.Transactions;
import com.montrackjpa.JPASpringBootExercises.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallets_id_gen")
    @SequenceGenerator(name = "wallets_id_gen", sequenceName = "wallets_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private long id;


    @Column(name = "wallet_name", nullable = false)
    private String name;


    @Min(value = 0, message = "amount must be non-negative")
    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "isactive", nullable = false)
    @ColumnDefault("false")
    private boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;


    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;



    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;


    @Column(name = "deleted_at")
    private Instant deletedAt;

    @OneToMany(mappedBy = "wallet",cascade = CascadeType.ALL)
    private Set<Pocket> pockets = new LinkedHashSet<>();

    @OneToMany(mappedBy = "wallet",cascade = CascadeType.ALL)
    private Set<Transactions> transactions = new LinkedHashSet<>();

    @PrePersist
    protected void onCreate(){
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }
}
