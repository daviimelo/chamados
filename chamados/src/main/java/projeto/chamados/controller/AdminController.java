package projeto.chamados.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.chamados.dto.AdminRequest;
import projeto.chamados.dto.AdminResponse;
import projeto.chamados.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<AdminResponse> salvar(@Valid @RequestBody AdminRequest adminRequest) {
        AdminResponse adminSalvo = adminService.salvar(adminRequest);

        // Status 201 Created junto com o corpo da resposta
        return ResponseEntity.status(HttpStatus.CREATED).body(adminSalvo);
    }

    @GetMapping
    public ResponseEntity<List<AdminResponse>> listarAdmins() {
        // Status 200 OK junto com a lista
        return ResponseEntity.ok(adminService.listarAdmins());
    }
}