package projeto.chamados.dto;

import jakarta.validation.constraints.NotBlank;
import projeto.chamados.model.Prioridade;

public record ChamadoRequest(
        @NotBlank(message = "O titulo é obrigatório")
        String titulo,
        @NotBlank(message = "A descrição é obrigatória")
        String descricao,
        @NotBlank(message = "A prioridade é obrigatória")
        Prioridade prioridade ) {
}
