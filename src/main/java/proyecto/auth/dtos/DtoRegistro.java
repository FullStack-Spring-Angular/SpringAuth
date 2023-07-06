package proyecto.auth.dtos;

import lombok.Data;

@Data
public class DtoRegistro {
    private String username;
    private String password;
    private String email;
}
