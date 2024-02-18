package org.example.practica01.encapsulacion;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Estudiante {
    @Id
    private long matricula;
    private String nombre;
    private String telefono;

}
