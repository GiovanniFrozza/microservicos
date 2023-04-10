package io.github.giovannifrozza.msclietes.application.representation;

import io.github.giovannifrozza.msclietes.domain.Cliente;
import lombok.Data;

@Data
public class ClienteSaveRequest {
    private String cpf;
    private String nome;
    private Integer idade;

    public Cliente toModel() {
        return new Cliente(cpf, nome, idade);
    }
}
