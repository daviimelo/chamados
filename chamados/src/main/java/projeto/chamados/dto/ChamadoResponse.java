package projeto.chamados.dto;

import projeto.chamados.model.Prioridade;
import projeto.chamados.model.StatusChamado;

import java.util.UUID;

public record ChamadoResponse(UUID id, String titulo, String descricao, Prioridade prioridade, StatusChamado status, UUID usuarioId) {
}
