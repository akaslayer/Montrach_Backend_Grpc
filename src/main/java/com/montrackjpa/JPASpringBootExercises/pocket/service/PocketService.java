package com.montrackjpa.JPASpringBootExercises.pocket.service;


import com.montrackjpa.JPASpringBootExercises.pocket.entity.Pocket;
import com.montrackjpa.JPASpringBootExercises.pocket.dto.PocketDTO;

import java.util.List;

public interface PocketService {
    public Pocket addNewPocket(PocketDTO pocketDTO);

    public List<Pocket> GetPocketByWalletId(long wallet_id);
}
