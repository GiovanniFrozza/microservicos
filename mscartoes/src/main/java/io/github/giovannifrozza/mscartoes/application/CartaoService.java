package io.github.giovannifrozza.mscartoes.application;

import io.github.giovannifrozza.mscartoes.domain.Cartao;
import io.github.giovannifrozza.mscartoes.infra.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoService {
    private final CartaoRepository repository;

    @Transactional
    public Cartao save(Cartao cartao) {
        return repository.save(cartao);
    }

    public List<Cartao> getCartoesRendaMenorIgual(Long renda) {
        return repository.findByRendaLessThanEqual(BigDecimal.valueOf(renda));
    }
}
