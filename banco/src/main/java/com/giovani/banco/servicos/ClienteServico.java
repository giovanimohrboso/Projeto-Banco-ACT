package com.giovani.banco.servicos;

import com.giovani.banco.modelos.ClienteModel;
import com.giovani.banco.repositorio.ClienteRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServico {
    @Autowired
    ClienteRepositorio clienteRepositorio;

    @Transactional
    public ClienteModel save(ClienteModel clienteModel){
        return clienteRepositorio.save(clienteModel);
    }

    public ResponseEntity<Object> consultarCliente(String documento){
        Optional<ClienteModel> clienteModel = clienteRepositorio.findById(documento);
        if (clienteModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(clienteModel.get());
    }

}
