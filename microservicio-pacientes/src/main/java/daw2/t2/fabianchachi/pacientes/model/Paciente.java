package daw2.t2.fabianchachi.pacientes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import java.time.LocalDate;

@Data
@Table("paciente")
public class Paciente {

    @Id
    private Integer id;

    @Column("tipo_documento")
    private String tipoDocumento;

    @Column("numero_documento")
    private String numeroDocumento;

    @Column("nombres")
    private String nombres;

    @Column("apellidos")
    private String apellidos;

    @Column("fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column("telefono")
    private String telefono;

    @Column("correo")
    private String correo;

    @Column("estado")
    private String estado;
}