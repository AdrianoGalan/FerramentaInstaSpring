package com.adriano.repositotory;

import java.util.List;

import com.adriano.model.Nomes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NomeRepository extends JpaRepository<Nomes, Integer> {

    List<Nomes> findByGenero(String genero);

}
