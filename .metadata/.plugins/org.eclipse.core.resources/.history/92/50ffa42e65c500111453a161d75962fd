package daw2.t2.fabianchachi.pacientes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import java.time.LocalDate;

@Data
@Table("paciente")
public class Paciente {
    @Id
    private Integer id;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String correo;
    private String estado;
}