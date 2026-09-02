package projeto.chamados.service;

import projeto.chamados.dto.AdminRequest;
import projeto.chamados.dto.AdminResponse;

import java.util.List;

public interface AdminService {
    AdminResponse salvar(AdminRequest adminRequest);
    List<AdminResponse> listarAdmins();
}
