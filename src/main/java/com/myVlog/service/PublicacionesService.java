/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myVlog.service;

import com.myVlog.domain.Publicaciones;
import com.myVlog.repository.PublicacionesRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PublicacionesService {

    @Autowired
    private PublicacionesRepository publicacionesRepository;

    @Transactional
    public Publicaciones save(Publicaciones publicacion) {
         return publicacionesRepository.save(publicacion);
    }

    @Transactional(readOnly = true)
    public List<Publicaciones> obtenerTodasLasPublicaciones() {
        return publicacionesRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Publicaciones obtenerPublicacionPorId(Long id) {
        return publicacionesRepository.findById(id).orElse(null);
    }

    @Transactional
    public void eliminarPublicacion(Long id) {
        publicacionesRepository.deleteById(id);
    }

}
