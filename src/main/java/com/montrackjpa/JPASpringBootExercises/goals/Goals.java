package com.montrackjpa.JPASpringBootExercises.goals;


import com.montrackjpa.JPASpringBootExercises.transactions.Transactions;
import com.montrackjpa.JPASpringBootExercises.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "goals")

public class Goals {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "goals_id_gen")
    @SequenceGenerator(name = "goals_id_gen", sequenceName = "goals_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "goal_name", nullable = false)
    private String name;

    @Min(value = 0, message = "amount must be non-negative")
    @Column(name = "amount", nullable = false)
    private int amount;


    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "attachment", nullable = false)
    private String attachment;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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

