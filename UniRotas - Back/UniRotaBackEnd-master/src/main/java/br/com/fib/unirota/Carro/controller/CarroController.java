package br.com.fib.unirota.Carro.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fib.unirota.Carro.dto.CarroDTO;
import br.com.fib.unirota.Carro.dto.CreateCarroRequest;
import br.com.fib.unirota.Carro.service.CarroService;

@RestController
@RequestMapping("/carro")
public class CarroController {
    private final CarroService carroService;

    public CarroController(CarroService carroService) {
        this.carroService = carroService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateCarroRequest createCarroRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carroService.createCarro(createCarroRequest));
    }

    @GetMapping("/{usuarioId}/findAll")
    public ResponseEntity<List<CarroDTO>> findAllByUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(carroService.findAllByUsuarioId(usuarioId));
    }
}
