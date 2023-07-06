package proyecto.auth.interfaces;

import java.util.Optional;

import proyecto.auth.models.Usuarios;

public interface UsuariosInterface {

	//Método para poder buscar un usuario mediante su nombre
    Optional<Usuarios> findByUsername(String username);

    //Método para poder verificar si un usuario existe en nuestra base de datos
    Boolean existsByUsername(String username);
}
