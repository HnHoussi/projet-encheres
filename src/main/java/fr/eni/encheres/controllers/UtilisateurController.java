package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
        System.out.println("Nombre d'enchères récupérées : " + encheres.size());

        return "index";

    }

    @GetMapping("/connexion")
    public String afficherConnexion(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "view-connexion";
    }

    @PostMapping("/profil-creer")
    public String creationCompte(@ModelAttribute Utilisateur utilisateur, Model model) {
        try {
            utilisateurService.creerCompte(utilisateur);
            model.addAttribute("utilisateur", utilisateur);
            return "redirect:/encheres";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("messageErreur", "Erreur lors de la création du compte");
            return "view-profil-creer";
        }
    }


}
