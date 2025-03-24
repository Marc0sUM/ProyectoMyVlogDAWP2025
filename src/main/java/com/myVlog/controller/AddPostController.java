/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myVlog.controller;

import com.myVlog.domain.Categoria;
import com.myVlog.domain.Publicaciones;
import com.myVlog.domain.Usuario;
import com.myVlog.service.CategoriaService;
import com.myVlog.service.FirebaseStorageService;
import com.myVlog.service.PublicacionesService;
import com.myVlog.service.UsuarioService;
import java.io.File;
import java.io.IOException;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/posts")
public class AddPostController {

    @Autowired
    private PublicacionesService publicacionService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @GetMapping("/create")
    public String mostrarFormularioCreacion(Model model, Authentication authentication) {
        // Obtener el usuario autenticado
        String username = authentication.getName();
        Usuario usuario = usuarioService.findByUsername(username);

        // Agregar el usuario al modelo
        model.addAttribute("usuario", usuario);

        // Agregar las categorías al modelo
        List<Categoria> categorias = categoriaService.obtenerTodasLasCategorias();
        model.addAttribute("categorias", categorias);

        return "posts/addPost"; // Ruta de la vista del formulario
    }

    @GetMapping
    public String listarPublicaciones(Model model) {
        List<Publicaciones> publicaciones = publicacionService.obtenerTodasLasPublicaciones();
        model.addAttribute("publicaciones", publicaciones);
        return "posts/posts";
    }

    @PostMapping("/guardar")
public String guardar(
        Publicaciones publicacion,
        @RequestParam("imagenFile") MultipartFile imagenFile,
        @RequestParam("usuario.id") Long usuarioId, 
        RedirectAttributes redirectAttributes
) {
    try {
        Usuario usuario = usuarioService.findById(usuarioId);
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado.");
            return "redirect:/posts/create";
        }

        publicacion.setUsuario(usuario);

        // Asignamos un valor temporal para evitar el error en la base de datos
        publicacion.setImagen("pendiente");

        // Guardamos la publicación sin imagen (ya que el campo no permite NULL)
        publicacion = publicacionService.save(publicacion);

        // Subir la imagen si el usuario seleccionó un archivo
        if (!imagenFile.isEmpty()) {
            String rutaImagen = firebaseStorageService.cargaImagen(imagenFile, "publicaciones", publicacion.getId());
            
            if (rutaImagen == null || rutaImagen.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Error al subir la imagen.");
                return "redirect:/posts/create";
            }

            // Asignamos la URL real de la imagen y actualizamos la publicación
            publicacion.setImagen(rutaImagen);
            publicacionService.save(publicacion);
        }

        return "redirect:/posts";
    } catch (Exception e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("error", "Ocurrió un error al guardar la publicación.");
        return "redirect:/posts/create";
    }
}
}
