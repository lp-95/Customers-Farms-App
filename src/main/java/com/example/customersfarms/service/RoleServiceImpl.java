package com.example.customersfarms.service;

import com.example.customersfarms.model.Role;
import com.example.customersfarms.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleDao;

    @Override
    public Role findByName( String name ) {
        Role role = roleDao.findRoleByName( name );
        return role;
    }
}
