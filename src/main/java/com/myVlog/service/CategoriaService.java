/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myVlog.service;

import com.myVlog.domain.Categoria;
import com.myVlog.repository.CategoriaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaRepository.findAll();
    }
    
    public Categoria obtenerCategoriaPorId(Long id) {
    return categoriaRepository.findById(id).orElse(null);
}
}
