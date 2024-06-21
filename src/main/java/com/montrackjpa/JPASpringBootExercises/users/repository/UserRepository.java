package com.montrackjpa.JPASpringBootExercises.users.repository;


import com.montrackjpa.JPASpringBootExercises.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
