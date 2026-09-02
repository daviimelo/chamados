package projeto.chamados.dto;

import projeto.chamados.model.Prioridade;

public record ChamadoRequest(String titulo, String descricao, Prioridade prioridade) {
}
