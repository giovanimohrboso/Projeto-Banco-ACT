package com.giovani.banco.modelos;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ContaModelKey implements Serializable {
    private String Agencia;
    private String numero;

    public String getAgencia() {
        return Agencia;
    }

    public void setAgencia(String agencia) {
        Agencia = agencia;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
