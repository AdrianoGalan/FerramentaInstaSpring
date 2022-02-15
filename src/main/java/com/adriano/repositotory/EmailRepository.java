package com.adriano.repositotory;

import java.util.List;

import com.adriano.model.Email;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    public List<Email> findByOrderByEmail();
    
}
