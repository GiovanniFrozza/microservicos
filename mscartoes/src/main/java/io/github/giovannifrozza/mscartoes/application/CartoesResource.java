package io.github.giovannifrozza.mscartoes.application;

import io.github.giovannifrozza.mscartoes.application.representation.CartaoSaveRequest;
import io.github.giovannifrozza.mscartoes.application.representation.CartoesPorClienteResponse;
import io.github.giovannifrozza.mscartoes.domain.Cartao;
import io.github.giovannifrozza.mscartoes.domain.ClienteCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class CartoesResource {

    private final CartaoService service;
    private final ClienteCartaoService clienteCartaoService;

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoSaveRequest request) {
        Cartao cartao = request.toModel();
        service.save(cartao);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda) {
        List<Cartao> list = service.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(list);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByCliente(@RequestParam("cpf") String cpf) {
        List<ClienteCartao> lista = clienteCartaoService.listCartaoByCpf(cpf);
        List<CartoesPorClienteResponse> resultList = lista.stream()
                .map(CartoesPorClienteResponse::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultList);
    }
}
