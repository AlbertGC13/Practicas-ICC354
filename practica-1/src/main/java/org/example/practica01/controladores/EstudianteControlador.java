package org.example.practica01.controladores;
import org.example.practica01.encapsulacion.Estudiante;
import org.example.practica01.servicios.EstudianteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteControlador {

    @Autowired
    private EstudianteServicio estudianteServicio;

    // Muestra la lista de estudiantes
    @GetMapping
    public String listarEstudiantes(Model model) {
        model.addAttribute("estudiantes", estudianteServicio.listarTodos());
        return "lista_estudiantes"; // nombre del archivo HTML de la vista
    }

    // Muestra el formulario para agregar un nuevo estudiante
    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoEstudiante(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        return "agregar_estudiante"; // nombre del archivo HTML de la vista
    }

    // Procesa el formulario para agregar un nuevo estudiante
    @PostMapping
    public String guardarEstudiante(@ModelAttribute("estudiante") Estudiante estudiante) {
        estudianteServicio.guardarOActualizarEstudiante(estudiante);
        return "redirect:/estudiantes";
    }

    @GetMapping("/editar/{matricula}")
    public String mostrarFormularioDeEdicion(@PathVariable Long matricula, Model model) {
        Estudiante estudiante = estudianteServicio.buscarPorMatricula(matricula)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula inválida: " + matricula));
        model.addAttribute("estudiante", estudiante);
        return "editar_estudiante"; // nombre del archivo HTML de la vista
    }

    @PostMapping("/editar/{matricula}")
    public String actualizarEstudiante(@PathVariable Long matricula, @ModelAttribute("estudiante") Estudiante estudiante, Model model) {
        Estudiante estudianteExistente = estudianteServicio.buscarPorMatricula(matricula)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula inválida: " + matricula));
        estudianteExistente.setNombre(estudiante.getNombre());
        estudianteExistente.setTelefono(estudiante.getTelefono());
        estudianteServicio.guardarOActualizarEstudiante(estudianteExistente);
        return "redirect:/estudiantes";
    }

    @GetMapping("/eliminar/{matricula}")
    public String eliminarEstudiante(@PathVariable Long matricula) {
        estudianteServicio.eliminarEstudiante(matricula);
        return "redirect:/estudiantes";
    }

}
