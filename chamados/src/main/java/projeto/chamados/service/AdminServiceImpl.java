package projeto.chamados.service;

import org.springframework.stereotype.Service;
import projeto.chamados.dao.AdminRepository;
import projeto.chamados.dto.AdminRequest;
import projeto.chamados.dto.AdminResponse;
import projeto.chamados.exception.APIException;
import projeto.chamados.exception.APIExceptionType;
import projeto.chamados.model.Administrador;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public AdminResponse salvar(AdminRequest adminRequest) {
        boolean existeAdminComEsseEmail = adminRepository.existsByEmail(adminRequest.email());
        if (existeAdminComEsseEmail) {
            throw new APIException(APIExceptionType.CONFLICT, "Já existe um administrador com esse email cadastrado!");
        }

        Administrador admin = new Administrador(adminRequest.nome(), adminRequest.email(), adminRequest.senha());
        Administrador adminSalvo = adminRepository.save(admin);
        return new AdminResponse(adminSalvo.getId(), adminSalvo.getNome(), adminSalvo.getEmail());
    }

    @Override
    public List<AdminResponse> listarAdmins() {
        List<Administrador> administradores = adminRepository.findAll();

        return administradores
                .stream()
                .map(adm -> new AdminResponse(adm.getId(), adm.getNome(), adm.getEmail()))
                .toList();
    }
}
