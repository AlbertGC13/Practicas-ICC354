package org.example.practica01.repositorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.practica01.encapsulacion.Estudiante;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstudianteRepositorio extends JpaRepository<Estudiante, Long> {
    List<Estudiante> findByNombre(String nombre);
    List<Estudiante> findAllByTelefonoIsNotNull();
    Estudiante findByNombreAndTelefono(String nombre, String telefono);
    List<Estudiante> findAllByNombreStartingWithIgnoreCase(String nombre);

    @Query("select e from Estudiante e where e.matricula = ?1")
    Estudiante consultaPorMatricula(Long matricula);

    @Query(value = "select * from estudiante e where e.nombre = :nombre", nativeQuery = true)
    List<Estudiante> consultaPorNombre(@Param("nombre") String nombre);
}
