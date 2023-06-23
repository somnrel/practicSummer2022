package com.javatechie.repository;

import com.javatechie.entity.Transaction;
import com.javatechie.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface TransactionalRepository extends JpaRepository<Transaction, Integer> {

    @Query(value = "select * from auth.`transaction` t where users_id =:id", nativeQuery = true)
    Collection<Transaction> test(@Param("id") Integer id);

//    Optional<Transaction> findTransactionByUserId(Integer id);

    @Query(value = "select * from auth.`transaction`", nativeQuery = true)
    Collection<Transaction> getAllTransactions();

}
