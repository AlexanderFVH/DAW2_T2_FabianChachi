package daw2.t2.fabianchachi.pacientes.controller;

import daw2.t2.fabianchachi.pacientes.model.Paciente;
import daw2.t2.fabianchachi.pacientes.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteRepository pacienteRepository;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Paciente>> obtenerPorId(@PathVariable Integer id) {
        return pacienteRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Object>> registrarPaciente(@RequestBody Paciente paciente) {
        return pacienteRepository.findByNumeroDocumento(paciente.getNumeroDocumento())
                .flatMap(existente -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body((Object) "Error: El número de documento ya se encuentra registrado.")))
                .switchIfEmpty(
                        pacienteRepository.save(paciente)
                                .map(nuevo -> ResponseEntity.status(HttpStatus.CREATED).body((Object) nuevo))
                );
    }
}