package com.montrackjpa.JPASpringBootExercises.pocket.dto;

import com.montrackjpa.JPASpringBootExercises.pocket.entity.Pocket;
import com.montrackjpa.JPASpringBootExercises.wallets.entity.Wallet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PocketDTO {
    private long id;

    @NotNull
    @NotBlank(message = "pocket name is required")
    private String name;

    @Positive(message = "Amount must be positive")
    private int amount;


    private String description;

    @NotNull(message = "Emoji is required")
    private String emoji;

    @NotNull(message = "Wallet ID is required")
    private long wallet_id;


    public Pocket toEntity(PocketDTO pocketDTO){
        Pocket pocket = new Pocket();
        pocket.setId(pocketDTO.getId());
        pocket.setName(pocketDTO.getName());
        pocket.setAmount(pocketDTO.getAmount());
        pocket.setEmoji(pocketDTO.getEmoji());
        pocket.setDescription(pocketDTO.getDescription());
        Wallet walletData = new Wallet();
        walletData.setId(pocketDTO.getWallet_id());
        pocket.setWallet(walletData);
        return pocket;
    }
}
