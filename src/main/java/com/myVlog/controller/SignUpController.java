package com.myVlog.controller;

import com.myVlog.domain.Usuario;
import com.myVlog.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {

    private final UsuarioService usuarioService;

    @Autowired
    public SignUpController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/signup")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "signup"; 
    }

    @PostMapping("/signup")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {
        try {
            usuarioService.registerUser(usuario.getUsername(), usuario.getEmail(), usuario.getPassword());
            return "redirect:/login"; 
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "signup"; 
        }
    }
}