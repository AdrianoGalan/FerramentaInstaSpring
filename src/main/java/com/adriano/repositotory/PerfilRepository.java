package com.adriano.repositotory;

import java.util.List;

import com.adriano.model.Perfil;
import com.adriano.model.Status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
    
    public Perfil getByUsername(String usurname);
    public List<Perfil> findByStatus(Status status);
    public List<Perfil> findByStatusId(int idStatus);
    public List<Perfil> findByStatusDIfBlo(int idStatus);
    public List<Perfil> findByOrderByNumeroSeguidorDesc();
}
