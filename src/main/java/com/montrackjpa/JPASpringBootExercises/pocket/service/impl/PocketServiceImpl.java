package com.montrackjpa.JPASpringBootExercises.pocket.service.impl;

import com.montrackjpa.JPASpringBootExercises.exceptions.InputException;
import com.montrackjpa.JPASpringBootExercises.pocket.entity.Pocket;
import com.montrackjpa.JPASpringBootExercises.pocket.dto.PocketDTO;
import com.montrackjpa.JPASpringBootExercises.pocket.repository.PocketRepository;
import com.montrackjpa.JPASpringBootExercises.pocket.service.PocketService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PocketServiceImpl implements PocketService {

    private final PocketRepository pocketRepository;

    public PocketServiceImpl(PocketRepository pocketRepository) {
        this.pocketRepository = pocketRepository;
    }

    @Override
    public Pocket addNewPocket(PocketDTO pocketDTO) {
        Pocket pocketData = pocketDTO.toEntity(pocketDTO);
        try{
            pocketRepository.save(pocketData);;
        }catch (DataIntegrityViolationException e){
            throw new InputException(HttpStatus.NOT_FOUND, "wallet id " + pocketDTO.getWallet_id() + " doesn't exist.");
        }
        return pocketRepository.save(pocketData);
    }

    @Override
    public List<Pocket> GetPocketByWalletId(long wallet_id) {
        var existingPocket = pocketRepository.findAll().stream().filter(data -> data.getWallet().getId() == wallet_id).collect(Collectors.toList());
        if (existingPocket.isEmpty()) {
            throw new InputException(HttpStatus.NOT_FOUND, "Pocket with wallet id " + wallet_id + " doesn't exist.");
        }
        return existingPocket;
    }
}
