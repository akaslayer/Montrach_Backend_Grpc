package com.montrackjpa.JPASpringBootExercises.wallets.dao;


public interface TransactionSummaryDAO {
    long getIdWallet();
    int getIncomeAmount();
    int getExpenseAmount();
}
