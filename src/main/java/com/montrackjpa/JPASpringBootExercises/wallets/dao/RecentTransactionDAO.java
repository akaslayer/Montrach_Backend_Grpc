package com.montrackjpa.JPASpringBootExercises.wallets.dao;

import java.util.Date;

public interface RecentTransactionDAO {
    long getId();
    String getTransactionName();

    Date getTransactionDate();
}
