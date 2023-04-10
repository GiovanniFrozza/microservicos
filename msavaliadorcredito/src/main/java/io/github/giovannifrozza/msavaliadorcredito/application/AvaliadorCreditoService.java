package io.github.giovannifrozza.msavaliadorcredito.application;

import feign.FeignException;
import io.github.giovannifrozza.msavaliadorcredito.application.exception.DadosClientNotFoundException;
import io.github.giovannifrozza.msavaliadorcredito.application.exception.ErroComunicacaoMsException;
import io.github.giovannifrozza.msavaliadorcredito.domain.model.*;
import io.github.giovannifrozza.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.github.giovannifrozza.msavaliadorcredito.infra.clients.ClienteResoureClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResoureClient clientsClient;
    private final CartoesResourceClient cartoesResourceClient;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClientNotFoundException, ErroComunicacaoMsException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientsClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesResourceClient.getCartoesByCliente(cpf);

            return SituacaoCliente.builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                .build();
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClientNotFoundException();
            }
            throw new ErroComunicacaoMsException(e.getMessage(), status);
        }
    }

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) throws DadosClientNotFoundException, ErroComunicacaoMsException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientsClient.dadosCliente(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesResourceClient.getCartoesRendaAte(renda);

            List<Cartao> cartoes = cartoesResponse.getBody();
            var listaCartoesAprovados = cartoes.stream().map(cartao -> {

                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosClienteResponse.getBody().getIdade());
                var fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

               CartaoAprovado aprovado = new CartaoAprovado();
               aprovado.setCartao(cartao.getNome());
               aprovado.setBandeira(cartao.getBandeira());
               aprovado.setLimiteAprovado(limiteAprovado);

               return aprovado;
            }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClientNotFoundException();
            }
            throw new ErroComunicacaoMsException(e.getMessage(), status);
        }
    }

}
