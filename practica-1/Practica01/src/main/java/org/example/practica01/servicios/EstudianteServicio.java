package org.example.practica01.servicios;

import org.example.practica01.encapsulacion.Estudiante;
import org.example.practica01.repositorio.EstudianteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EstudianteServicio {

    @Autowired
    private EstudianteRepositorio estudianteRepositorio;

    // Lista todos los estudiantes
    public List<Estudiante> listarTodos() {
        return estudianteRepositorio.findAll();
    }

    // Encuentra un estudiante por matrícula
    public Optional<Estudiante> buscarPorMatricula(Long matricula) {
        return estudianteRepositorio.findById(matricula);
    }

    // Guarda o actualiza un estudiante
    @Transactional
    public Estudiante guardarOActualizarEstudiante(Estudiante estudiante) {
        return estudianteRepositorio.save(estudiante);
    }

    // Elimina un estudiante por matrícula
    @Transactional
    public void eliminarEstudiante(Long matricula) {
        estudianteRepositorio.deleteById(matricula);
    }

    // Encuentra estudiantes por nombre
    public List<Estudiante> buscarPorNombre(String nombre) {
        return estudianteRepositorio.findByNombre(nombre);
    }



}

