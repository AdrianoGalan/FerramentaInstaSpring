package com.adriano.repositotory;

import com.adriano.model.Sobrenomes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SobreNomeRepository extends JpaRepository<Sobrenomes, Integer> {

}
