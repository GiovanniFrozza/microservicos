package io.github.giovannifrozza.msavaliadorcredito.application;

import io.github.giovannifrozza.msavaliadorcredito.AvaliadorCreditoApplication;
import io.github.giovannifrozza.msavaliadorcredito.application.exception.DadosClientNotFoundException;
import io.github.giovannifrozza.msavaliadorcredito.application.exception.ErroComunicacaoMsException;
import io.github.giovannifrozza.msavaliadorcredito.application.exception.ErroSolicitacoCartaoException;
import io.github.giovannifrozza.msavaliadorcredito.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

    private final AvaliadorCreditoService avaliadorCreditoService;

    @GetMapping(value = "situacao-cliente", params = "cpf")
    public ResponseEntity consultarSituacaoCliente(@RequestParam("cpf") String cpf) {
        try {
            SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        } catch (DadosClientNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMsException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dados) {
        try {
            RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorCreditoService.realizarAvaliacao(dados.getCpf(), dados.getRenda());
            return ResponseEntity.ok(retornoAvaliacaoCliente);
        } catch (DadosClientNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMsException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping("solicitacoes-cartao")
    public ResponseEntity solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartao dadosSolicitacaoEmissaoCartao) {
        try{
            ProtocoloSolicitacaoCartao protocoloSolicitacaoCartao = avaliadorCreditoService.solicitarEmissaoCartao(dadosSolicitacaoEmissaoCartao);
            return ResponseEntity.ok(protocoloSolicitacaoCartao);
        } catch (ErroSolicitacoCartaoException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
