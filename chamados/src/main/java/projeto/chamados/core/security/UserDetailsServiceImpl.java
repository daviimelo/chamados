package projeto.chamados.core.security;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import projeto.chamados.dao.UsuarioRepository;
import projeto.chamados.model.Usuario;

import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        Usuario usuario =
                usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return User.withUsername(usuario.getId().toString())
                .password(usuario.getSenha())
                .roles(usuario.getPapel().name())
                .build();
    }
}
