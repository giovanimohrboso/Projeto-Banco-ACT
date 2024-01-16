package com.giovani.banco.repositorio;

import com.giovani.banco.modelos.ContaModel;
import com.giovani.banco.modelos.ContaModelKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepositorio extends JpaRepository<ContaModel, ContaModelKey> {


}
