package com.montrackjpa.JPASpringBootExercises.pocket.repository;

import com.montrackjpa.JPASpringBootExercises.pocket.entity.Pocket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PocketRepository extends JpaRepository<Pocket,Long> {
}
