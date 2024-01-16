package com.giovani.banco.controles;

import com.giovani.banco.modelos.ClienteModel;
import com.giovani.banco.servicos.ClienteServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ClienteControlador {

    @Autowired
    ClienteServico clienteServico;

    @PostMapping("/cliente")
    public ResponseEntity<ClienteModel> cadastrar(@RequestBody ClienteModel clienteModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteServico.cadastrar(clienteModel));
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listar(){
        List<ClienteModel> clientes = clienteServico.listar();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
    @GetMapping("/cliente/{documento}")
    public ResponseEntity<Object> consultar(@PathVariable(value = "documento") String documento){
        return clienteServico.consultar(documento);
    }

}
