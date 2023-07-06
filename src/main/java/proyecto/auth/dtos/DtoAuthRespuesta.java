package proyecto.auth.dtos;

import lombok.Data;

//Esta clase va a ser la que nos devolverá la información con el token y el tipo que tenga este
@Data
public class DtoAuthRespuesta {
    private String accessToken;
    private String tokenType = "Bearer ";
    private String roles;
    private String username;

    public DtoAuthRespuesta(String accessToken, String roles, String username) {
        this.accessToken = accessToken;
        this.roles = roles;
        this.username = username;
    }
}
