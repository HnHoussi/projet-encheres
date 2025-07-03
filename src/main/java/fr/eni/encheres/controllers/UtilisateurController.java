package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/encheres")
public class UtilisateurController {

    private UtilisateurService utilisateurService;
    private EnchereService enchereService;

    public UtilisateurController(UtilisateurService utilisateurService, EnchereService enchereService) {
        this.utilisateurService = utilisateurService;
        this.enchereService = enchereService;
    }

    @GetMapping
    public String afficherIndex(Model model) {

        List<Utilisateur> utilisateurs=utilisateurService.consulterListeUtilisateurs();
        List<Enchere> encheres=enchereService.consulterEncheres();

        model.addAttribute("utilisateurs",utilisateurs);
        model.addAttribute("encheres",encheres);
        return "index";

    }
}
