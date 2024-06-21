package com.montrackjpa.JPASpringBootExercises.wallets.repository;

import com.montrackjpa.JPASpringBootExercises.transactions.Transactions;
import com.montrackjpa.JPASpringBootExercises.wallets.dao.TransactionSummaryDAO;
import com.montrackjpa.JPASpringBootExercises.wallets.dao.TransactionSummaryPocketAndGoalDAO;
import com.montrackjpa.JPASpringBootExercises.wallets.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;


@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {


    @Modifying
    @Transactional
    @Query(value = "UPDATE wallets SET deleted_at = CURRENT_TIMESTAMP WHERE id = :id", nativeQuery = true)
    void softWalletDelete(@Param("id") Long id);

    public static final String ExpenseAndIncomeQuery = "SELECT w.id AS idWallet, COALESCE(SUM(CASE WHEN tp.transactionTypes = 'Income' THEN t.amount ELSE 0 END), 0) AS incomeAmount, COALESCE(SUM(CASE WHEN tp.transactionTypes = 'Expense' THEN t.amount ELSE 0 END), 0) AS expenseAmount FROM Wallet w LEFT JOIN Transactions t ON w.id = t.wallet.id LEFT JOIN TransactionType tp ON tp.id = t.transactionType.id WHERE tp.transactionTypes IN ('Income', 'Expense') AND w.id = :id AND t.createdAt >=  date_trunc(:currentDateTimeStamp, CURRENT_DATE) GROUP BY w.id";
    public static final String pocketAndGoalsQuery = "SELECT w.id as idWallet, COALESCE(count(p.id), 0) as pocketCounts,  (SELECT COALESCE(count(*), 0) FROM Goals) as goalCounts FROM Wallet w LEFT JOIN Pocket p ON p.wallet.id = w.id where w.id = :id GROUP BY w.id";

    public static final String recentTransactionQuery = "SELECT t FROM Transactions t Where t.wallet.id = :id ORDER BY transactionDate DESC LIMIT 20";

    @Transactional
    @Query(value = ExpenseAndIncomeQuery)
    TransactionSummaryDAO getSummaryTransactionDetail(@Param("id") Long id, @Param("currentDateTimeStamp") String currentDateTimeStamp);


    @Transactional
    @Query(value = pocketAndGoalsQuery)
    TransactionSummaryPocketAndGoalDAO getSummaryTransactionGoalsAndPocket(@Param("id") Long id);


    @Transactional
    @Query(value = recentTransactionQuery)
    List<Transactions> getRecentTransactions(@Param("id") Long id);
}
