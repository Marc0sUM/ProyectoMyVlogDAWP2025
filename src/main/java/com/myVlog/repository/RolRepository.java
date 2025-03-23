/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.myVlog.repository;

import com.myVlog.domain.Rol;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Marcos
 */
public interface RolRepository extends JpaRepository<Rol, Long>{
    Optional<Rol>findByNombre(String nombre);
}
