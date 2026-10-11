package daw2.t2.fabianchachi.citas.controller;

import daw2.t2.fabianchachi.citas.config.RabbitMQConfig;
import daw2.t2.fabianchachi.citas.model.Cita;
import daw2.t2.fabianchachi.citas.repository.CitaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaRepository citaRepository;
    private final WebClient.Builder webClientBuilder;
    private final RabbitTemplate rabbitTemplate;

    @PostMapping
    public Mono<ResponseEntity<Object>> registrarCita(@RequestBody Cita cita) {
        // 1. Comunicación síncrona con microservicio-pacientes vía Eureka
        return webClientBuilder.build().get()
                .uri("http://MICROSERVICIO-PACIENTES/api/pacientes/" + cita.getPacienteId())
                .retrieve()
                .bodyToMono(Object.class)
                .flatMap(pacienteExistente -> {
                    // 2. Lógica de negocio y persistencia reactiva
                    cita.setEstado("CONFIRMADA");
                    cita.setFechaCreacion(LocalDateTime.now());
                    return citaRepository.save(cita)
                            .doOnSuccess(citaGuardada -> {
                                // 3. Comunicación asíncrona
                                try {
                                    rabbitTemplate.convertAndSend(
                                            RabbitMQConfig.EXCHANGE,
                                            RabbitMQConfig.ROUTING_KEY,
                                            citaGuardada
                                    );
                                } catch (Exception e) {
                                    System.err.println("Aviso: RabbitMQ no está disponible, pero la cita fue guardada.");
                                }
                            })
                            .map(c -> ResponseEntity.status(HttpStatus.CREATED).body((Object) c));
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: El paciente con ID " + cita.getPacienteId() + " no existe en el sistema.")));
    }
}