/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myVlog.service;


import com.myVlog.domain.Rol;
import com.myVlog.domain.Usuario;
import com.myVlog.repository.RolRepository;
import com.myVlog.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RolRepository rolRepository;
    public Usuario registerUser(String userName, String correo, String password) {
        if (usuarioRepository.findByUsername(userName).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso.");
        }
    if (usuarioRepository.findByCorreo(correo).isPresent()) {
            throw new RuntimeException("El correo electrónico ya está en uso.");
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(userName);
        usuario.setCorreo(correo);
        usuario.setPassword(passwordEncoder.encode(password)); 
        // Asignar un rol por defecto (por ejemplo, "usuario")
        Rol rolUsuario = rolRepository.findByNombre("usuario")
                .orElseThrow(() -> new RuntimeException("Rol 'usuario' no encontrado."));
        usuario.setRol(rolUsuario);

        // Guardar el usuario en la base de datos
        return usuarioRepository.save(usuario);
    }
        // Buscar un usuario por nombre de usuario
    public Usuario findByUsername(String userName) {
        return usuarioRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
    }

    // Actualizar un usuario existente
    public Usuario updateUser(Usuario usuario) {
        // Verificar si el usuario existe
        if (!usuarioRepository.existsById(usuario.getId())) {
            throw new RuntimeException("Usuario no encontrado.");
        }
        return usuarioRepository.save(usuario);
    }

    // Eliminar un usuario por ID
    public void deleteUser(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado.");
        }
        usuarioRepository.deleteById(id);
    }
}