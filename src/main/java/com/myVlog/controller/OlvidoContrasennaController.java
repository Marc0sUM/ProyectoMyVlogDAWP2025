/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myVlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Marcos
 */
@Controller
public class OlvidoContrasennaController {
    
    @GetMapping("/olvido-password")
    public  String mostrarDashboard(){
        return "olvidoContrasenna";
    }
}