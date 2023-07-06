package proyecto.auth.interfaces;

import java.util.Optional;

import proyecto.auth.models.Roles;

public interface RolesInterface {

	//Método para buscar un role por su nombre en nuestra base de datos
    Optional<Roles> findByName(String name);
}
