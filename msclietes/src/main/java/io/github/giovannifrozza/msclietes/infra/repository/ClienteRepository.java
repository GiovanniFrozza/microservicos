package io.github.giovannifrozza.msclietes.infra.repository;

import io.github.giovannifrozza.msclietes.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCpf(String cpf);
}
