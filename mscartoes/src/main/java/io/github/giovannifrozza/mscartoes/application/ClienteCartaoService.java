package io.github.giovannifrozza.mscartoes.application;

import io.github.giovannifrozza.mscartoes.domain.ClienteCartao;
import io.github.giovannifrozza.mscartoes.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    private final ClienteCartaoRepository repository;

    public List<ClienteCartao> listCartaoByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }
}
