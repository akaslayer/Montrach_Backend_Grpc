package com.montrackjpa.JPASpringBootExercises.wallets.services;


import com.montrackjpa.JPASpringBootExercises.transactions.Transactions;
import com.montrackjpa.JPASpringBootExercises.wallets.dao.TransactionSummaryDAO;
import com.montrackjpa.JPASpringBootExercises.wallets.dto.TransactionSummaryDTO;
import com.montrackjpa.JPASpringBootExercises.wallets.dto.WalletDTO;
import com.montrackjpa.JPASpringBootExercises.wallets.entity.Wallet;

import java.util.List;

public interface WalletServices {

    public List<Wallet> getAllWallet();

    public WalletDTO getWalletById(long id);

    public WalletDTO addNewWallet(WalletDTO walletDTO);


    public WalletDTO updateWallet(WalletDTO walletDTO);


    public WalletDTO deleteWallet(long id);
    public TransactionSummaryDTO getWalletSummary(long id, String dateRange);

    public List<Transactions> getRecentTransactions(long id);
}
