package com.montrackjpa.JPASpringBootExercises.transactiontype;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;




@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="transactiontype")
public class TransactionType {


    public enum TransactionStatus {
        Income,
        Expense
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transactiontype_id_gen")
    @SequenceGenerator(name = "transactiontype_id_gen", sequenceName = "transactiontype_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_types", nullable = false)
    private TransactionStatus transactionTypes;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;



    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;


    @Column(name = "deleted_at")
    private Instant deletedAt;

    @PrePersist
    protected void onCreate(){
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }
}
