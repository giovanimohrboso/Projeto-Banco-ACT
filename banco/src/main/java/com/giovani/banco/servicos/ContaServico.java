package com.giovani.banco.servicos;

import com.giovani.banco.modelos.ContaModel;
import com.giovani.banco.modelos.ContaModelKey;
import com.giovani.banco.repositorio.ContaRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ContaServico {

    @Autowired
    ContaRepositorio contaRepositorio;

    @Transactional
    public ContaModel cadastrar(ContaModel contaModel) {
        return contaRepositorio.save(contaModel);
    }

    public Optional<ContaModel> consultar(ContaModelKey id) {
        Optional<ContaModel> contaModel = contaRepositorio.findById(id);
        return contaModel;
    }

    public Optional<ContaModel> depositar(ContaModelKey id, double valor) {
        Optional<ContaModel> contaModel = this.consultar(id);
        ContaModel contaAtual = contaModel.get();
        if (contaModel.isPresent()) {
            contaAtual.depositar(valor);
            contaRepositorio.save(contaAtual);
        }
        return contaModel;
    }

    public Optional<ContaModel> sacar(ContaModelKey id, double valor) {
        Optional<ContaModel> contaModel = this.consultar(id);
        ContaModel contaAtual = contaModel.get();
        if (contaModel.isPresent()) {
            if (contaAtual.sacar(valor)) {
                contaRepositorio.save(contaAtual);
                return contaModel;
            }
        }
        return null;
    }

    public Boolean pagamento(ContaModelKey idSaque,ContaModelKey idDeposito, double valor) {
        if(this.sacar(idSaque,valor) != null) {
            this.depositar(idDeposito,valor);
        }else{
            return false;
        }
        return true;
    }
}