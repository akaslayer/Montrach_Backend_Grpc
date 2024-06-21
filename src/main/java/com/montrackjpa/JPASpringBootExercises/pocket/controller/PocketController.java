package com.montrackjpa.JPASpringBootExercises.pocket.controller;


import com.montrackjpa.JPASpringBootExercises.pocket.entity.Pocket;
import com.montrackjpa.JPASpringBootExercises.pocket.dto.PocketDTO;
import com.montrackjpa.JPASpringBootExercises.pocket.service.PocketService;
import com.montrackjpa.JPASpringBootExercises.responses.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pockets")
public class PocketController {
    private final PocketService pocketService;

    public PocketController(PocketService pocketService) {
        this.pocketService = pocketService;
    }

    @PostMapping
    private ResponseEntity<Response<Pocket>> addNewPocket(@RequestBody PocketDTO pocketDTO){
        Pocket data = pocketService.addNewPocket(pocketDTO);
        return Response.successfulResponse(HttpStatus.OK.value(), "Pocket has been added", data);
    }

    @GetMapping("{wallet_id}")
    private ResponseEntity<Response<List<Pocket>>> getPocketByWalletId(@PathVariable("wallet_id") long wallet_id){
        return Response.successfulResponse(HttpStatus.OK.value(), "All the pocket with wallet id" + wallet_id +  " has been fetched ", pocketService.GetPocketByWalletId(wallet_id));
    }

}
