package projeto.chamados.core.exception;

import lombok.Data;

@Data
public class APIException extends RuntimeException {
    private APIExceptionType type;

    public APIException(APIExceptionType exceptionType, String message) {
        super(message);
        this.type = exceptionType;
    }
}
