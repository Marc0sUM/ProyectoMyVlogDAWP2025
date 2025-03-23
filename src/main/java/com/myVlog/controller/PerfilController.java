package com.myVlog.controller;

import com.myVlog.domain.Usuario;
import com.myVlog.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PerfilController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @GetMapping("/perfil")
    public String mostrarPerfil(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String userName = userDetails.getUsername();
        Usuario usuario = usuarioRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + userName));
        model.addAttribute("usuario", usuario);

        return "perfil/perfil"; 
    }
}