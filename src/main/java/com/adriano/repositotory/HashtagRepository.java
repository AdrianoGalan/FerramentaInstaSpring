package com.adriano.repositotory;

import java.util.List;

import com.adriano.model.Hashtag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {

    List<Hashtag> byCategoria(int categoria);
    
}
