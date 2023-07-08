package proyecto.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import proyecto.auth.dtos.DtoAuthRespuesta;
import proyecto.auth.dtos.DtoLogin;
import proyecto.auth.dtos.DtoRegistro;
import proyecto.auth.dtos.ResponseMessageDto;
import proyecto.auth.models.Roles;
import proyecto.auth.models.Usuarios;
import proyecto.auth.security.JwtGenerador;
import proyecto.auth.services.RolesService;
import proyecto.auth.services.UsuariosService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RestController
@Validated
@RequestMapping("/api/auth/")
public class RestControllerAuth {
	@Autowired
    private AuthenticationManager authenticationManager;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
    private RolesService rolesService;
    private UsuariosService usuariosService;
    private JwtGenerador jwtGenerador;
    
    private static Set<String> tokenBlacklist = new HashSet<>();

    public RestControllerAuth(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RolesService rolesService, UsuariosService usuariosService, JwtGenerador jwtGenerador) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.rolesService= rolesService;
        this.usuariosService = usuariosService;
        this.jwtGenerador = jwtGenerador;
    }
    //Método para poder registrar usuarios con role "user"
    @PostMapping("register")
    public ResponseEntity<String> registrar(@RequestBody DtoRegistro dtoRegistro) {
        if (usuariosService.existsByUsername(dtoRegistro.getUsername())) {
            return new ResponseEntity(new ResponseMessageDto("Ya existe usuario con ese nombre de usuario", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
        Usuarios usuarios = new Usuarios();
        usuarios.setUsername(dtoRegistro.getUsername());
        usuarios.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));
        usuarios.setEmail(dtoRegistro.getEmail());
        Roles roles = rolesService.findByName("USER").get();
        usuarios.setRoles(Collections.singletonList(roles));
        usuariosService.save(usuarios);
        return new ResponseEntity(new ResponseMessageDto("Usuario registrado correctamente", HttpStatus.OK.value()), HttpStatus.OK);
    }

    //Método para poder guardar usuarios de tipo ADMIN
    @PostMapping("register/admin")
    public ResponseEntity<String> registrarAdmin(@RequestBody DtoRegistro dtoRegistro) {
        if (usuariosService.existsByUsername(dtoRegistro.getUsername())) {
        	return new ResponseEntity(new ResponseMessageDto("Ya existe usuario con ese nombre de usuario", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
        Usuarios usuarios = new Usuarios();
        usuarios.setUsername(dtoRegistro.getUsername());
        usuarios.setEmail(dtoRegistro.getEmail());
        usuarios.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));
        Roles roles = rolesService.findByName("ADMIN").get();
        usuarios.setRoles(Collections.singletonList(roles));
        usuariosService.save(usuarios);
        return new ResponseEntity(new ResponseMessageDto("Administrador registrado correctamente", HttpStatus.OK.value()), HttpStatus.OK);
    }

    //Método para poder logear un usuario y obtener un token
    @PostMapping("login")
    public ResponseEntity<DtoAuthRespuesta> login(@RequestBody DtoLogin dtoLogin) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dtoLogin.getUsername(), dtoLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerador.generarToken(authentication);
        
        //Captura el nombre de usaurio
        String username = authentication.getName();
        //Buscamos por nombre de usuario ya que debe exisitr un unico nombre de usuario
        Usuarios usuario = usuariosService.findByUsername(username).get();

        return new ResponseEntity<>(new DtoAuthRespuesta(token, usuario), HttpStatus.OK);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<DtoAuthRespuesta> logout(HttpServletRequest request) {
    	System.out.println(request);
        String token = request.getHeader("Authorization");
        // Add the token to the blacklist
        tokenBlacklist.add(token);

        return new ResponseEntity<>(new DtoAuthRespuesta(null,null), HttpStatus.OK);
    }

    private boolean isTokenBlacklisted(String token) {
        return tokenBlacklist.contains(token);
    }

}
