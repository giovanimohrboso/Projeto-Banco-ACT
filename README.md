DOCUMENTAÇÂO API: http://localhost:8085/swagger-ui/index.html#/

http://localhost:8085/cliente

Registro 1
{
     "documento":"1"
    ,"tipo":"PF"
    ,"nome":"Giovani"
    ,"email":"giovani.mohr@gmail.com"
    ,"senha":"123"
    ,"logradouro":"Rua teste"
    ,"numero":"1"
    ,"bairro":"Limoeiro"
    ,"cidade":"São Paulo"
    ,"uf":"SP"
}

Registro 2
{
     "documento":"2"
    ,"tipo":"PF"
    ,"nome":"Giovani"
    ,"email":"giovani.mohr@gmail.com"
    ,"senha":"123"
    ,"logradouro":"Rua teste"
    ,"numero":"1"
    ,"bairro":"Limoeiro"
    ,"cidade":"São Paulo"
    ,"uf":"SP"
}

http://localhost:8085/conta

Registro 1
{
    "id":{
        "agencia":"1",
        "numero":"1"
        },
    "saldo":"0",
    "status":true,
    "cliente":{
        "documento":"1"
    }
}

Registro 2
{
    "id":{
        "agencia":"1",
        "numero":"2"
        },
    "saldo":"0",
    "status":true,
    "cliente":{
        "documento":"2"
    }
}

http://localhost:8085/depositar

{
    "agencia":"1",
    "numero":"1",
    "valor":30
}

http://localhost:8085/sacar

{
    "agencia":"1",
    "numero":"2",
    "valor":10
}

http://localhost:8085/pagamento/1/1 (agencia/numero remetente)

{
    "agencia":"1", //(agencia destinatario)
    "numero":"2",  //(numero destinatario)
    "valor":10
}
