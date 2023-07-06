package proyecto.auth.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.auth.interfaces.UsuariosInterface;
import proyecto.auth.models.Usuarios;
import proyecto.auth.repositories.IUsuariosRepository;

@Service
public class UsuariosService implements UsuariosInterface {
	
	@Autowired
	IUsuariosRepository usuariosRepository;

	@Override
	public Optional<Usuarios> findByUsername(String username) {
		// TODO Auto-generated method stub
		return usuariosRepository.findByUsername(username);
	}

	@Override
	public Boolean existsByUsername(String username) {
		// TODO Auto-generated method stub
		return usuariosRepository.existsByUsername(username);
	}
	
	public void save(Usuarios usuarios) {
		// TODO Auto-generated method stub
		usuariosRepository.save(usuarios);
	}

}
