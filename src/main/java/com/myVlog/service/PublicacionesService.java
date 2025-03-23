/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myVlog.service;

import com.myVlog.domain.Publicaciones;
import com.myVlog.repository.PublicacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicacionesService {
    @Autowired
    private PublicacionesRepository publicacionesRepository;
    
    public Publicaciones crearPublicacion(Publicaciones publicacion){
        return publicacionesRepository.save(publicacion);
    }
}
