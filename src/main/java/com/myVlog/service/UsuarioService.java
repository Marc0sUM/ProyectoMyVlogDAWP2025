/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myVlog.service;


import com.myVlog.domain.Usuario;
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
    public Usuario registerUser(String username, String email, String password) {
        if (usuarioRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso.");
        }
    if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("El correo electrónico ya está en uso.");
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(password)); 
        return usuarioRepository.save(usuario);
    }
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username).orElse(null);
    }
    public Usuario updateUser(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    public void deleteUser(Long id) {
        usuarioRepository.deleteById(id);
    }
}