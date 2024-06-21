package com.montrackjpa.JPASpringBootExercises.wallets.services.impl;

import com.montrackjpa.JPASpringBootExercises.currencies.entitity.Currency;
import com.montrackjpa.JPASpringBootExercises.currencies.repository.CurrencyRepository;
import com.montrackjpa.JPASpringBootExercises.exceptions.InputException;
import com.montrackjpa.JPASpringBootExercises.transactions.Transactions;
import com.montrackjpa.JPASpringBootExercises.users.entity.User;
import com.montrackjpa.JPASpringBootExercises.users.repository.UserRepository;
import com.montrackjpa.JPASpringBootExercises.wallets.dao.TransactionSummaryDAO;
import com.montrackjpa.JPASpringBootExercises.wallets.dao.TransactionSummaryPocketAndGoalDAO;
import com.montrackjpa.JPASpringBootExercises.wallets.dto.TransactionSummaryDTO;
import com.montrackjpa.JPASpringBootExercises.wallets.dto.WalletDTO;
import com.montrackjpa.JPASpringBootExercises.wallets.entity.Wallet;

import com.montrackjpa.JPASpringBootExercises.wallets.repository.WalletRepository;
import com.montrackjpa.JPASpringBootExercises.wallets.services.WalletServices;
import io.grpc.StatusRuntimeException;
import lombok.extern.java.Log;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.CurrencyExchangeGrpc;
import org.example.ExchangeRequest;
import org.example.ExchangeResponse;
import org.example.RequestData;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Log
public class WalletServiceImpl implements WalletServices {

    private final WalletRepository walletRepository;

    @GrpcClient("exchange-server")
    private CurrencyExchangeGrpc.CurrencyExchangeBlockingStub currencyExchangeStub;
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;





    public WalletServiceImpl(WalletRepository walletRepository, UserRepository userRepository, CurrencyRepository currencyRepository, UserRepository userRepository1, CurrencyRepository currencyRepository1) {
        this.walletRepository = walletRepository;


        this.userRepository = userRepository1;
        this.currencyRepository = currencyRepository1;
    }


    public List<Wallet> getAllWallet() {
        return walletRepository.findAll();
    }

    @Override
    public WalletDTO getWalletById(long id) {
        Wallet walletData = walletRepository.findAll().stream().filter(data -> data.getId() == id).findFirst().orElse(null);
        if(walletData == null){
            throw new InputException(HttpStatus.NOT_FOUND, "Wallet with ID " + id + " doesn't exist.");
        }
        WalletDTO walletDTOData = new WalletDTO();
        walletDTOData.setId(walletData.getId());
        walletDTOData.setAmount(walletData.getAmount());
        walletDTOData.setName(walletData.getName());
        walletDTOData.setUser_id(walletData.getUser().getId());
        walletDTOData.setCurrency_id(walletData.getCurrency().getId());
        return walletDTOData;
    }


    public WalletDTO addNewWallet(WalletDTO walletDTO) {
        Wallet walletData = walletDTO.toEntity(walletDTO);
        try{
            walletRepository.save(walletData);
        }catch (DataIntegrityViolationException e){
            if(!userRepository.existsById(walletDTO.getUser_id())){
                throw new InputException(HttpStatus.NOT_FOUND, "Wallet with user id " + walletDTO.getUser_id()  + " doesn't exist.");
            }
            if(!currencyRepository.existsById(walletDTO.getCurrency_id())){
                throw new InputException(HttpStatus.NOT_FOUND, "Wallet with currency id " + walletDTO.getCurrency_id()  + " doesn't exist.");
            }
        }
        return walletDTO;
    }

    @Override
    public WalletDTO updateWallet(WalletDTO walletDTO) {
        var existingWallet = walletRepository.findById(walletDTO.getId());
        if (existingWallet.isEmpty()) {
            throw new InputException(HttpStatus.NOT_FOUND, "Wallet with ID " + walletDTO.getId() + " doesn't exist.");
        }
        var updatedWallet = existingWallet.get();
        updatedWallet.setName(walletDTO.getName());
        updatedWallet.setAmount(walletDTO.getAmount());
        User userData = new User();
        userData.setId(walletDTO.getUser_id());
        updatedWallet.setUser(userData);

        Currency currencyData = new Currency();
        currencyData.setId(walletDTO.getCurrency_id());
        updatedWallet.setCurrency(currencyData);

        updatedWallet.setUpdatedAt(Instant.now());

        walletRepository.save(updatedWallet);
        return walletDTO;
    }

    @Override
    public WalletDTO deleteWallet(long id) {
        var walletData = walletRepository.findAll().stream().filter(wallet -> wallet.getId() == id).findFirst().orElse(null);
        if(walletData == null){
            throw new InputException(HttpStatus.NOT_FOUND, "Wallet with ID " + id + " doesn't exist.");
        }
       walletRepository.softWalletDelete(walletData.getId());
        WalletDTO walletDTOData = new WalletDTO();
        walletDTOData.setId(walletData.getId());
        walletDTOData.setAmount(walletData.getAmount());
        walletDTOData.setName(walletData.getName());
        walletDTOData.setUser_id(walletData.getUser().getId());
        walletDTOData.setCurrency_id(walletData.getCurrency().getId());
        return walletDTOData;

    }

    @Override
    public TransactionSummaryDTO getWalletSummary(long id, String dateRange) {
        String date = null;
        String comparisonTimeString = "2024-06-11T03:48:37.141Z";
        Instant comparisonTime = Instant.parse(comparisonTimeString);

        switch (dateRange.toLowerCase()){
            case "year":
                date = "year";
                break;
            case "month":
                date = "month";
                break;
            case "day":
                date = "day";
            default:break;
        }

        List<RequestData> requestData = new ArrayList<>();
        var data = walletRepository.getSummaryTransactionDetail(id,date);
        var goalData = walletRepository.getSummaryTransactionGoalsAndPocket(id);


            RequestData requestData1 = RequestData.newBuilder()
                    .setPair("IDR/USD")
                    .setAmount(Integer.toString(data.getExpenseAmount()))
                    .setDate("2024-08-05")
                    .build();

            requestData.add(requestData1);

            RequestData requestData2 = RequestData.newBuilder()
                    .setPair("IDR/USD")
                    .setAmount(Integer.toString(data.getIncomeAmount()))
                    .setDate("2024-08-05")
                    .build();
            requestData.add(requestData2);


            ExchangeRequest request = ExchangeRequest.newBuilder()
                    .addAllResult(requestData)
                    .build();

            final ExchangeResponse resp = this.currencyExchangeStub.getExchangeAmount(request);


        TransactionSummaryDTO summaryData = new TransactionSummaryDTO();
        summaryData.setId(goalData.getIdWallet());
        summaryData.setExpense(resp.getResult(0));
        summaryData.setIncome(resp.getResult(1));
//        summaryData.setIncome((data != null) ? data.getIncomeAmount() : 0);
        summaryData.setGoalCount(goalData.getGoalCounts());
        summaryData.setPocketCount(goalData.getPocketCounts());
        return summaryData;
    }

    @Override
    public List<Transactions> getRecentTransactions(long id) {
        int i = 0;
        List<RequestData> requestData = new ArrayList<>();
        List<Transactions> transactionList = walletRepository.getRecentTransactions(id);
        for(Transactions transactionData : transactionList){
            RequestData requestData1 = RequestData.newBuilder()
                    .setPair("IDR/USD")
                    .setAmount(Integer.toString(transactionData.getAmount()))
                    .setDate("2024-08-05")
                    .build();
            requestData.add(requestData1);
        }
        ExchangeRequest request = ExchangeRequest.newBuilder()
                .addAllResult(requestData)
                .build();
        final ExchangeResponse resp = this.currencyExchangeStub.getExchangeAmount(request);
        for(Transactions transcations : transactionList) {
            transcations.setAmount(resp.getResult(i));
            i++;
        }
        return transactionList;
    }
}
