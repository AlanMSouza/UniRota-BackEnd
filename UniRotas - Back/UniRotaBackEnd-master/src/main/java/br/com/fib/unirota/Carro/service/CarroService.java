package br.com.fib.unirota.Carro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fib.unirota.Carro.dto.CarroDTO;
import br.com.fib.unirota.Carro.dto.CreateCarroRequest;
import br.com.fib.unirota.Carro.entity.Carro;
import br.com.fib.unirota.Carro.repository.CarroRepository;
import br.com.fib.unirota.Usuario.entity.Usuario;
import br.com.fib.unirota.Usuario.repository.UsuarioRepository;

@Service
public class CarroService {
    private final CarroRepository carroRepository;
    private final UsuarioRepository usuarioRepository;

    public CarroService(CarroRepository carroRepository, UsuarioRepository userRepository) {
        this.carroRepository = carroRepository;
        this.usuarioRepository = userRepository;
    }

    public CarroDTO createCarro(CreateCarroRequest createCarroRequest) {
        Usuario usuario = usuarioRepository.findById(createCarroRequest.usuarioId()).orElseThrow();
        Carro carro = Carro.builder().placa(createCarroRequest.placa()).cor(createCarroRequest.cor())
                .modelo(createCarroRequest.modelo()).usuario(usuario).build();
        CarroDTO carroDTO = new CarroDTO(carroRepository.save(carro));
        return carroDTO;
    }

    public List<CarroDTO> findAllByUsuarioId(Long usuarioId) {
        return carroRepository.findAllByUsuarioId(usuarioId);
    }
}
