package com.adriano.repositotory;

import com.adriano.model.Categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    Categoria getByNome(String nome);
    
}
