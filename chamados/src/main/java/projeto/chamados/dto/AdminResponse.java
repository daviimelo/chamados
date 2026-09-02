package projeto.chamados.dto;

import java.util.UUID;

public record AdminResponse(UUID id, String nome, String email) {
}
