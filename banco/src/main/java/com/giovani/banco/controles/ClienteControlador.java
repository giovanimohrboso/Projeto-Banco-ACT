package com.giovani.banco.controles;

import com.giovani.banco.modelos.ClienteModel;
import com.giovani.banco.servicos.ClienteServico;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class ClienteControlador {

    @Autowired
    ClienteServico clienteServico;

    @PostMapping("/cliente")
    public ResponseEntity<ClienteModel> cadastrarCliente(@RequestBody ClienteModel clienteModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteServico.save(clienteModel));
    }

    @GetMapping("/cliente/{documento}")
    public ResponseEntity<Object> consultarCliente(@PathVariable(value = "documento") String documento){
        return clienteServico.consultarCliente(documento);
    }

}
