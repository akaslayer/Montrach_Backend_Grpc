package com.montrackjpa.JPASpringBootExercises.wallets.dao;

public interface TransactionSummaryPocketAndGoalDAO {
    Long getIdWallet();
    int getPocketCounts();
    int getGoalCounts();
}
