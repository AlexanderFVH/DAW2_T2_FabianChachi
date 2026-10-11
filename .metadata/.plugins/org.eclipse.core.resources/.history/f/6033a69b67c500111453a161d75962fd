package daw2.t2.fabianchachi.citas.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Table("cita")
public class Cita {
    @Id
    private Integer id;
    private Integer pacienteId;
    private String especialidad;
    private LocalDateTime fechaHora;
    private String profesionalCodigo;
    private String estado;
    private LocalDateTime fechaCreacion;
}