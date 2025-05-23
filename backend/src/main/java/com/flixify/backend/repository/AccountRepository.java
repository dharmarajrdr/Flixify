package com.flixify.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flixify.backend.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

}
