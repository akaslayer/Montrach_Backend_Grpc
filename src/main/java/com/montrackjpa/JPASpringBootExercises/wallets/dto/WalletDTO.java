package com.montrackjpa.JPASpringBootExercises.wallets.dto;


import com.montrackjpa.JPASpringBootExercises.currencies.entitity.Currency;
import com.montrackjpa.JPASpringBootExercises.users.entity.User;
import com.montrackjpa.JPASpringBootExercises.wallets.entity.Wallet;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Data
public class WalletDTO {
    private long id;

    @NotNull
    @NotBlank(message = "Wallet name is required")
    private String name;

    @Positive(message = "Amount must be positive")
    private int amount;

    @NotNull(message = "User ID is required")
    private long user_id;

    @NotNull(message = "Currency ID is required")
    private long currency_id;

    public Wallet toEntity(WalletDTO walletDTO) {
        var data = new Wallet();
        data.setId(walletDTO.getId());
        data.setName(walletDTO.getName());
        data.setAmount(walletDTO.getAmount());

        Currency currencyData = new Currency();
        currencyData.setId(walletDTO.getCurrency_id());
        data.setCurrency(currencyData);

        User userData = new User();
        userData.setId(walletDTO.getUser_id());
        data.setUser(userData);
        data.setUpdatedAt(Instant.now());
        data.setCreatedAt(Instant.now());
        return data;
    }
}
