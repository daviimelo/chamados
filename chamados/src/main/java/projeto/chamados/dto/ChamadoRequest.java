package projeto.chamados.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import projeto.chamados.model.Categoria;
import projeto.chamados.model.Prioridade;

public record ChamadoRequest(
        @NotBlank(message = "O titulo é obrigatório")
        @Size(min = 5, max = 100, message = "O título deve ter entre 5 e 100 caracteres")
        String titulo,

        @NotBlank(message = "A descrição é obrigatória")
        @Size(min = 10, max = 1000, message = "A descrição deve ter entre 10 e 1000 caracteres")
        String descricao,

        @NotNull(message = "A prioridade é obrigatória")
        Prioridade prioridade,

        @NotNull(message = "A categoria é obrigatória")
        Categoria categoria) {
}
