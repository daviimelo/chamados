package projeto.chamados.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // 400 - Bean Validation (@Valid no @RequestBody)
    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Um ou mais campos são inválidos");
        problem.setTitle("Validation Error");

        var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", String.valueOf(error.getDefaultMessage())
                ))
                .toList();

        problem.setProperty("errors", errors);

        return ResponseEntity.badRequest().body(problem);
    }

    // 404 / 422 / 409 - Erros de negócio mapeados no Enum
    @ExceptionHandler(APIException.class)
    public ResponseEntity<ProblemDetail> handleApiException(APIException e) {
        HttpStatus status = switch (e.getType()) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case BUSINESS_ERROR -> HttpStatus.UNPROCESSABLE_CONTENT;
            case CONFLICT -> HttpStatus.CONFLICT;
            case FORBIDDEN -> HttpStatus.FORBIDDEN;
        };
        return problem(status, e.getMessage());
    }

    // 401 - Login com credenciais erradas
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ProblemDetail> handleAuthentication(AuthenticationException e) {
        return problem(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos");
    }

    // 403 - Acesso negado por falta de permissão
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ProblemDetail> handleAccessDenied(AccessDeniedException e) {
        return problem(HttpStatus.FORBIDDEN, e.getMessage());
    }

    // 500 - Qualquer outro erro não previsto
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException(Exception e) {
        log.error("Erro inesperado", e);
        return problem(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado no servidor!");
    }

    // Metodo utilitário para montar o ProblemDetail
    private ResponseEntity<ProblemDetail> problem(HttpStatus status, String detail) {
        return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(status, detail));
    }
}