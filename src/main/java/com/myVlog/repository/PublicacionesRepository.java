/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.myVlog.repository;


import com.myVlog.domain.Publicaciones;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PublicacionesRepository extends JpaRepository<Publicaciones, Long>{
    
}
