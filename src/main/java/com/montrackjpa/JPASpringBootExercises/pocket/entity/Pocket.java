package com.montrackjpa.JPASpringBootExercises.pocket.entity;


import com.montrackjpa.JPASpringBootExercises.currencies.entitity.Currency;
import com.montrackjpa.JPASpringBootExercises.transactions.Transactions;
import com.montrackjpa.JPASpringBootExercises.users.entity.User;
import com.montrackjpa.JPASpringBootExercises.wallets.entity.Wallet;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "pockets")
public class Pocket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pockets_id_gen")
    @SequenceGenerator(name = "pockets_id_gen", sequenceName = "pockets_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private long id;


    @Column(name = "pocket_name", nullable = false)
    private String name;

    @Min(value = 0, message = "amount must be non-negative")
    @Column(name = "amount", nullable = false)
    private int amount;


    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "emoji", nullable = false)
    private String emoji;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;


    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;



    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;


    @Column(name = "deleted_at")
    private Instant deletedAt;

    @OneToMany(mappedBy = "pocket",cascade = CascadeType.ALL)
    private Set<Transactions> transaction = new LinkedHashSet<>();

    @PrePersist
    protected void onCreate(){
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }
}
