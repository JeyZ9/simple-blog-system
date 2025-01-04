package com.app.simpleblogsystem.seeders;

import com.app.simpleblogsystem.models.Role;
import com.app.simpleblogsystem.models.RoleName;
import com.app.simpleblogsystem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Autowired
    public DatabaseSeeder(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

//String... args เป็นการใช้งาน Varargs (Variable Arguments) ใน Java ซึ่งหมายถึงการอนุญาตให้ส่งอาร์กิวเมนต์ที่มีจำนวนไม่แน่นอนไปยังเมธอดนั้นๆ โดยที่ไม่จำเป็นต้องระบุจำนวนพารามิเตอร์ที่แน่นอน.
    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0L) {
            List<Role> roles = new ArrayList<>();
            roles.add(new Role(RoleName.USER));
            roles.add(new Role(RoleName.MANAGER));
            roles.add(new Role(RoleName.ADMIN));

            roleRepository.saveAll(roles);
        }
    }
}
