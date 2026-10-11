package daw2.t2.fabianchachi.citas.repository;

import daw2.t2.fabianchachi.citas.model.Cita;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CitaRepository extends ReactiveCrudRepository<Cita, Integer> {
}