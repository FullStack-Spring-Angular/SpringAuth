package proyecto.auth.dtos;
import lombok.Data;

@Data
public class ResponseMessageDto {

	private String message;
	private Integer statusCode;

	public ResponseMessageDto(String message, int statusCode) {
		this.message = message;
		this.statusCode = statusCode;
	}
}
