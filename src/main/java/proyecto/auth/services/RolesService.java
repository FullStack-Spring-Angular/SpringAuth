package proyecto.auth.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.auth.interfaces.RolesInterface;
import proyecto.auth.models.Roles;
import proyecto.auth.repositories.IRolesRepository;

@Service
public class RolesService implements RolesInterface {
	
	@Autowired
	IRolesRepository rolesRepository;

	@Override
	public Optional<Roles> findByName(String name) {
		// TODO Auto-generated method stub
		return rolesRepository.findByName(name);
	}

}
