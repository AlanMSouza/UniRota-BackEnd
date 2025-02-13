package br.com.fib.unirota.Carona.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fib.unirota.Carona.dto.CaronaDTO;
import br.com.fib.unirota.Carona.dto.CreateCaronaRequest;
import br.com.fib.unirota.Carona.dto.RemoverPassageiroRequest;
import br.com.fib.unirota.Carona.service.CaronaService;
import br.com.fib.unirota.Usuario.dto.CaronasDisponiveisDTO;

@RestController
@RequestMapping("/carona")
public class CaronaController {
    private final CaronaService caronaService;

    public CaronaController(CaronaService caronaService) {
        this.caronaService = caronaService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateCaronaRequest createCaronaRequest) {
        caronaService.createCarona(createCaronaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<CaronasDisponiveisDTO>> listAll() {
        return ResponseEntity.ok(caronaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaronaDTO> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(caronaService.findById(id));
    }

    @GetMapping("/{usuarioId}/listAll")
    public ResponseEntity<?> getAllByUserId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok().body(caronaService.findAllByUserId(usuarioId));
    }

    @GetMapping("/listByCity")
    public ResponseEntity<?> listByCity(@RequestParam String cidade) {
        return ResponseEntity.ok(caronaService.findAllByCity(cidade));
    }

    @PostMapping("/removerPassageiro")
    public ResponseEntity<?> postMethodName(@RequestBody RemoverPassageiroRequest request) {
        caronaService.removerPassageiro(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/finalizar/{caronaId}")
    public ResponseEntity<?> finalizarCarona(@PathVariable Long caronaId) {
        caronaService.finalizarCarona(caronaId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
