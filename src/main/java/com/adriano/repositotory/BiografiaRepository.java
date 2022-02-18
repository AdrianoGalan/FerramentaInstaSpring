package com.adriano.repositotory;

import com.adriano.model.Biografia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BiografiaRepository extends JpaRepository<Biografia, Integer> {

}
