package io.github.giovannifrozza.msclietes.application;

import io.github.giovannifrozza.msclietes.domain.Cliente;
import io.github.giovannifrozza.msclietes.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;

    @Transactional
    public Cliente save(Cliente cliente){
        return repository.save(cliente);
    }

    public Optional<Cliente> getByCPF(String cpf) {
        return this.repository.findByCpf(cpf);
    }
}
