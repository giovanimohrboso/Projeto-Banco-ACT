package com.giovani.banco.modelos;

import jakarta.persistence.*;

import java.io.Serializable;
@Entity
@Table(name="TB_CONTA")
public class ContaModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ContaModelKey id;
    private double saldo;
    private boolean status;
    @ManyToOne(fetch = FetchType.EAGER)
    private ClienteModel cliente;

    public ContaModelKey getId() {
        return id;
    }

    public void setId(ContaModelKey id) {
        this.id = id;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }

    public Boolean depositar(Double valor){
        setSaldo(getSaldo()+valor);
        return true;
    }

    public Boolean sacar(Double valor){
        if (getSaldo()>=valor) {
            setSaldo(getSaldo() - valor);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Id:"+this.getId()+" Cliente "+this.getCliente()+" saldo:"+this.getSaldo();
    }
}
