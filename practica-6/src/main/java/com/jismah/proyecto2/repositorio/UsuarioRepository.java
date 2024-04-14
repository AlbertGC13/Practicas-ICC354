package com.jismah.proyecto2.repositorio;

import com.jismah.proyecto2.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByUsername(String username);

    boolean existsUsuarioByUsername(String username);

    Usuario findUsuarioById(int id);

}
