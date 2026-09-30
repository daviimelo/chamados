package projeto.chamados.dto;

import projeto.chamados.model.Papel;

import java.util.UUID;

public record UsuarioResponse(UUID id, String nome, String email, Papel papel) {
}
