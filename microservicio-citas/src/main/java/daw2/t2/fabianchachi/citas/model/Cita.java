package daw2.t2.fabianchachi.citas.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Table("cita")
public class Cita {

    @Id
    private Integer id;

    @Column("paciente_id")
    private Integer pacienteId;

    @Column("especialidad")
    private String especialidad;

    @Column("fecha_hora")
    private LocalDateTime fechaHora;

    @Column("profesional_codigo")
    private String profesionalCodigo;

    @Column("estado")
    private String estado;

    @Column("fecha_creacion")
    private LocalDateTime fechaCreacion;
}