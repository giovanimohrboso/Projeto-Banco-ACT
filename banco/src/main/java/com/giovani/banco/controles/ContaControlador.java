package com.giovani.banco.controles;

import com.giovani.banco.dtos.ContaRecordDto;
import com.giovani.banco.modelos.ContaModel;
import com.giovani.banco.modelos.ContaModelKey;
import com.giovani.banco.servicos.ContaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class ContaControlador {

    @Autowired
    ContaServico contaServico;

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

    @PostMapping("/conta")
    public ResponseEntity<ContaModel>cadastrar(@RequestBody ContaModel contaModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(contaServico.cadastrar(contaModel));
    }

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

    @PutMapping("/pagamento/{agencia}/{numero}")
    public ResponseEntity<Object> pagamento(@PathVariable(value = "agencia") String agencia,@PathVariable(value = "numero") String numero,@RequestBody ContaRecordDto contaRecordDTO){
        ContaModelKey chave = new ContaModelKey();
        chave.setAgencia(contaRecordDTO.agencia());
        chave.setNumero(contaRecordDTO.numero());
     //   return contaServico.sacar(chave,contaRecordDTO.valor());
        return null;
    }


}
