package com.giovani.banco.controles;

import com.giovani.banco.modelos.ClienteModel;
import com.giovani.banco.servicos.ClienteServico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ClienteControlador {

    @Autowired
    ClienteServico clienteServico;
    @Operation(summary = "Realiza o cadastro de novos clientes", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Serviço fora"),
    })
    @PostMapping("/cliente")
    public ResponseEntity<ClienteModel> cadastrar(@RequestBody ClienteModel clienteModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteServico.cadastrar(clienteModel));
    }
    @Operation(summary = "Listagem de Clientes", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem de Cliente"),
            @ApiResponse(responseCode = "500", description = "Serviço fora"),
    })
    @GetMapping("/listar")
    public ResponseEntity<?> listar(){
        List<ClienteModel> clientes = clienteServico.listar();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
    @Operation(summary = "Consulta de Cliente por Documento", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno Cliente com Sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Serviço fora"),
    })
    @GetMapping("/cliente/{documento}")
    public ResponseEntity<Object> consultar(@PathVariable(value = "documento") String documento){
        return clienteServico.consultar(documento);
    }

}
