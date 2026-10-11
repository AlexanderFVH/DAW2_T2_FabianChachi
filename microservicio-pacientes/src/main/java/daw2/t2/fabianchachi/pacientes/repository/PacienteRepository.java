package daw2.t2.fabianchachi.pacientes.repository;

import daw2.t2.fabianchachi.pacientes.model.Paciente;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface PacienteRepository extends ReactiveCrudRepository<Paciente, Integer> {
    Mono<Paciente> findByNumeroDocumento(String numeroDocumento);
}