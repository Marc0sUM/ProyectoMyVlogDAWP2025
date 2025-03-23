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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/posts")
public class AddPostController {

    @Autowired
    private PublicacionesService publicacionService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @GetMapping("/create")
    public String mostrarFormularioCreacion(Model model) {
        List<Categoria> categorias = categoriaService.obtenerTodasLasCategorias();
        model.addAttribute("categorias", categorias);

        return "posts/addPost"; // Ruta correcta de la plantilla
    }

    @PostMapping("/create")
    public String crearPublicacion(
            @RequestParam String titulo,
            @RequestParam String contenido,
            @RequestParam("image") MultipartFile image,
            @RequestParam Long categoriaId,
            BindingResult result, 
            Model model) throws IOException {
        
        if (titulo.isEmpty() || contenido.isEmpty() || categoriaId == null || image.isEmpty()) {
            model.addAttribute("error", "Todos los campos son obligatorios.");
            return "posts/addPost"; // Ruta correcta de la plantilla
        }

        Categoria categoria = categoriaService.obtenerCategoriaPorId(categoriaId);
    
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();
        
        File file = firebaseStorageService.convertToFile(image);

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();

        String imageUrl = firebaseStorageService.uploadFile(file, "posts", fileName);

        // Crea la publicación
        Publicaciones publicacion = new Publicaciones();
        publicacion.setTitulo(titulo);
        publicacion.setContenido(contenido);
        publicacion.setImagen(imageUrl);
        publicacion.setCategoria(categoria); // Asigna la categoría
        publicacion.setUsuario(usuario); // Asigna el usuario autenticado

        publicacionService.crearPublicacion(publicacion);

        return "redirect:/posts"; 
    }
}
