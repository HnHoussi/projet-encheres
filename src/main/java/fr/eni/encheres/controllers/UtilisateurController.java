package fr.eni.encheres.controllers;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/enchere")
public class UtilisateurController {

    @GetMapping
    public String afficherIndex() {
        return "index";

    }
}
