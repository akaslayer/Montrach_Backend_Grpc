package com.montrackjpa.JPASpringBootExercises.wallets.controller;


import com.montrackjpa.JPASpringBootExercises.responses.Response;
import com.montrackjpa.JPASpringBootExercises.transactions.Transactions;
import com.montrackjpa.JPASpringBootExercises.wallets.dao.TransactionSummaryDAO;
import com.montrackjpa.JPASpringBootExercises.wallets.dto.TransactionSummaryDTO;
import com.montrackjpa.JPASpringBootExercises.wallets.dto.WalletDTO;
import com.montrackjpa.JPASpringBootExercises.wallets.entity.Wallet;
import com.montrackjpa.JPASpringBootExercises.wallets.services.WalletServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private final WalletServices walletServices;

    public WalletController(WalletServices walletServices) {
        this.walletServices = walletServices;
    }


    @GetMapping
    private ResponseEntity<Response<List<Wallet>>> getAllWallet(){
        return Response.successfulResponse("All the wallet has been fetched", walletServices.getAllWallet());
    }

    @GetMapping("/{id}")
    private ResponseEntity<Response<WalletDTO>> getWalletByID(@PathVariable("id") long id){
        return Response.successfulResponse("Wallet with id " + id + " has been fetched", walletServices.getWalletById(id));
    }

    @PostMapping("")
    private ResponseEntity<Response<WalletDTO>> addWallet(@RequestBody @Validated WalletDTO walletDTO){
        WalletDTO data = walletServices.addNewWallet(walletDTO);
        return Response.successfulResponse(HttpStatus.OK.value(), "Wallet has been added", data);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Response<WalletDTO>> updateWallet(@PathVariable("id") long id, @RequestBody @Validated WalletDTO walletDTO){
        walletDTO.setId(id);
        WalletDTO data = walletServices.updateWallet(walletDTO);
        return Response.successfulResponse(HttpStatus.OK.value(), "Wallet has been added", data);
    }


    @DeleteMapping("/{id}")
    private ResponseEntity<Response<WalletDTO>> deleteWallet(@PathVariable("id") long id){
        WalletDTO data = walletServices.deleteWallet(id);
        return Response.successfulResponse(HttpStatus.OK.value(), "Wallet has been deleted", data);
    }


    @GetMapping("/summary/{id}")
    private ResponseEntity<Response<TransactionSummaryDTO>> getWalletSummary(@PathVariable("id") long id, @RequestParam String dateRange){
        TransactionSummaryDTO data = walletServices.getWalletSummary(id,dateRange);
        return Response.successfulResponse(HttpStatus.OK.value(), "Wallet summary has been fetched", data);
    }

    @GetMapping("/recent/{id}")
    private ResponseEntity<Response<List<Transactions>>> getRecentTransaction(@PathVariable("id") long id){
        List<Transactions> data = walletServices.getRecentTransactions(id);
        return Response.successfulResponse(HttpStatus.OK.value(), "Recent Transaction has been fetched", data);
    }
}
