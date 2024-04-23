package io.swagger.service;

import io.swagger.model.Admin;
import io.swagger.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdminById(Integer adminId) {
        return adminRepository.findById(adminId);
    }

    public Admin createOrUpdateAdmin(Admin admin) {
        Integer maxIdFromDatabase = adminRepository.findMaxAdminId();
        int nextId = (maxIdFromDatabase != null) ? maxIdFromDatabase + 1 : 1;

        if (admin.getAdminId() == null) {
            admin.setAdminId(nextId);
        }

        return adminRepository.save(admin);
    }

    public void deleteAdmin(Integer adminId) {
        adminRepository.deleteById(adminId);
    }
}

