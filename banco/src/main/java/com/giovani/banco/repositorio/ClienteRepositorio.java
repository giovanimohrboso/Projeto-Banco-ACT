package com.giovani.banco.repositorio;

import com.giovani.banco.modelos.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio extends JpaRepository<ClienteModel,String>{
}
