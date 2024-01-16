package com.giovani.banco.controles;

import com.giovani.banco.dtos.ContaRecordDto;
import com.giovani.banco.dtos.NotificacaoDTO;
import com.giovani.banco.modelos.ContaModel;
import com.giovani.banco.modelos.ContaModelKey;
import com.giovani.banco.servicos.ContaServico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "*")
public class ContaControlador {

    @Autowired
    ContaServico contaServico;
    @Operation(summary = "Consulta de Conta por Agencia e Numero", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno de Conta com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "404", description = "Conta não Encontrada"),
            @ApiResponse(responseCode = "500", description = "Serviço fora"),
    })
    @GetMapping("/conta/{agencia}/{numero}")
    public ResponseEntity<Object> consultar(@PathVariable(value = "agencia") String agencia,@PathVariable(value = "numero") String numero){
        ContaModelKey chave = new ContaModelKey();
        chave.setAgencia(agencia);
        chave.setNumero(numero);
        if (contaServico.consultar(chave).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(contaServico.consultar(chave).get());
    }
    @Operation(summary = "Cadastra uma nova Conta", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conta criada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Serviço fora"),
    })
    @PostMapping("/conta")
    public ResponseEntity<ContaModel>cadastrar(@RequestBody ContaModel contaModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(contaServico.cadastrar(contaModel));
    }
    @Operation(summary = "Realiza um deposito em uma Conta", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Deposito Realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "404", description = "Conta não Encontrada"),
            @ApiResponse(responseCode = "500", description = "Serviço fora"),
    })
    @PutMapping("/depositar")
    public ResponseEntity<Object> depositar(@RequestBody ContaRecordDto contaRecordDTO){
        ContaModelKey chave = new ContaModelKey();
        chave.setAgencia(contaRecordDTO.agencia());
        chave.setNumero(contaRecordDTO.numero());
        if (contaServico.consultar(chave).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(contaServico.depositar(chave,contaRecordDTO.valor()).get());
    }
    @Operation(summary = "Realiza um saque em uma Conta", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Saque Realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "404", description = "Conta não Encontrada"),
            @ApiResponse(responseCode = "500", description = "Serviço fora"),
    })
    @PutMapping("/sacar")
    public ResponseEntity<Object> sacar(@RequestBody ContaRecordDto contaRecordDTO){
        ContaModelKey chave = new ContaModelKey();
        chave.setAgencia(contaRecordDTO.agencia());
        chave.setNumero(contaRecordDTO.numero());
        if (contaServico.consultar(chave).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada!");
        }
        if (contaServico.sacar(chave,contaRecordDTO.valor()) != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(contaServico.consultar(chave).get());
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Não foi Possível realizar o Saque.");
    }
    @Operation(summary = "Realiza uma trnasferencia em uma Conta", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transferencia Realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "404", description = "Conta não Encontrada"),
            @ApiResponse(responseCode = "500", description = "Serviço fora"),
    })
    @PutMapping("/pagamento/{agencia}/{numero}")
    public ResponseEntity<Object> pagamento(@PathVariable(value = "agencia") String agencia,@PathVariable(value = "numero") String numero,@RequestBody ContaRecordDto contaRecordDTO){
        ContaModelKey chaveSaque = new ContaModelKey();
        chaveSaque.setAgencia(agencia);
        chaveSaque.setNumero(numero);
        ContaModelKey chaveDeposito = new ContaModelKey();
        chaveDeposito.setAgencia(contaRecordDTO.agencia());
        chaveDeposito.setNumero(contaRecordDTO.numero());

        if (!contaServico.pagamento(chaveSaque, chaveDeposito, contaRecordDTO.valor())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi Possível realizar a Transferencia");
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<NotificacaoDTO> notificacao = restTemplate.getForEntity("https://run.mocky.io/v3/9769bf3a-b0b6-477a-9ff5-91f63010c9d3", NotificacaoDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(notificacao);
    }


}
