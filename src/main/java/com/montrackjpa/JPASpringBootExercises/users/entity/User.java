package com.montrackjpa.JPASpringBootExercises.users.entity;

import com.montrackjpa.JPASpringBootExercises.goals.Goals;
import com.montrackjpa.JPASpringBootExercises.wallets.entity.Wallet;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallets_id_gen")
    @SequenceGenerator(name = "wallets_id_gen", sequenceName = "wallets_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private long id;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Fullname is required")
    @Size(max = 100, message = "Fullname must be less than or equal to 100 characters")
    @Column(name = "fullname")
    private String fullname;

    @NotBlank(message = "Salt is required")
    @Column(name = "salt")
    private String salt;

    @NotBlank(message = "Hash is required")
    @Column(name = "hash")
    private String hash;

    @NotBlank(message = "Salt pin is required")
    @Column(name = "salt_pin")
    private String saltPin;

    @NotBlank(message = "Hash pin is required")
    @Column(name = "hash_pin")
    private String hashPin;

    @Column(name = "quotes")
    private String quotes;

    @Column(name = "user_img")
    private String userImg;

//    @Column(name = "active_currency")
//    private String activeCurrency;
//
//    @NotNull(message = "Currency ID is required")
//    @Column(name = "currency_id")
//    private Long currencyId;
//
//    @NotNull(message = "Language ID is required")
//    @Column(name = "language_id")
//    private Long languageId;


    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;


    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;


    @Column(name = "deleted_at")
    private Instant deletedAt;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Wallet> wallets = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Goals> goals = new LinkedHashSet<>();
}
