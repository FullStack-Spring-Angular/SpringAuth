package proyecto.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proyecto.auth.models.Roles;

import java.util.Optional;

@Repository
public interface IRolesRepository extends JpaRepository<Roles, Long> {
    //Método para buscar un role por su nombre en nuestra base de datos
    Optional<Roles> findByName(String name);
}
