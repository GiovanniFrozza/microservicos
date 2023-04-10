package io.github.giovannifrozza.msavaliadorcredito.application.exception;

import lombok.Getter;

public class ErroComunicacaoMsException extends Exception {
    @Getter
    private Integer status;

    public ErroComunicacaoMsException(String mensagem, Integer status) {
        super(mensagem);
        this.status = status;
    }
}
