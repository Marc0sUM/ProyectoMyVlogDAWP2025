package com.myVlog.controller;

import com.myVlog.domain.Usuario;
import com.myVlog.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class EditarPerfilController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @GetMapping("/editar")
    public String mostrarFormularioEdicion(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));

        model.addAttribute("usuario", usuario);
        return "perfil/editarPerfil"; 
    }
    
    @PostMapping("/editar")
    public String actualizarPerfil(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute Usuario usuario) {
        String username = userDetails.getUsername();
        Usuario usuarioExistente = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
        
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setTelefono(usuario.getTelefono());
        
        usuarioRepository.save(usuarioExistente);
        return "redirect:/perfil"; 
    }
}