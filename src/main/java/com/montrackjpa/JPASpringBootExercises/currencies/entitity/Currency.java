package com.montrackjpa.JPASpringBootExercises.currencies.entitity;


import com.montrackjpa.JPASpringBootExercises.wallets.entity.Wallet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "currencies")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currencies_id_gen")
    @SequenceGenerator(name = "currencies_id_gen", sequenceName = "currencies_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private long id;

    @Column( name = "currency_from_name")
    private String currencyFromName;

    @Column( name = "currency_to_name")
    private String currencyToName;

    @Column(name = "currency_rate_to")
    private int currencyRateTo;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;


    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "currency",cascade = CascadeType.ALL)
    private Set<Wallet> wallets = new LinkedHashSet<>();
}
