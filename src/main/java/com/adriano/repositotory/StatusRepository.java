package com.adriano.repositotory;

import com.adriano.model.Status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

    public Status getByStatus(String status);
    
}
