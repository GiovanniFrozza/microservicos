package io.github.giovannifrozza.msavaliadorcredito.application.exception;

public class DadosClientNotFoundException extends Exception {
    public DadosClientNotFoundException() {
        super("Dados do cliente nao encontrados para o CPF informado.");
    }
}
