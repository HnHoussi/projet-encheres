package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
//@RequestMapping("/utilisateur")
public class UtilisateurControllers {

    private UtilisateurService utilisateurService;
    private EnchereService enchereService;
    private CategorieService categorieService;


    public UtilisateurControllers(UtilisateurService utilisateurService, EnchereService enchereService, CategorieService categorieService) {
        this.utilisateurService = utilisateurService;
        this.enchereService = enchereService;
        this.categorieService = categorieService;
    }

//    @GetMapping
//    public String afficherIndex(Model model) {
//
//        List<Utilisateur> utilisateurs=utilisateurService.consulterListeUtilisateurs();
//        List<Enchere> encheres=enchereService.consulterEncheres();
//        List<Categorie> categories=categorieService.consulterCategories();
//
//        model.addAttribute("utilisateurs",utilisateurs);
//        model.addAttribute("encheres",encheres);
//        model.addAttribute("categories",categories);
//        System.out.println("Nombre d'enchères récupérées : " + encheres.size());
//
//        return "index";
//
//    }
//    @GetMapping("/affcherAccueil")
//    public String afficherAccueil() {
//        return "redirect:/encheres";
//    }

    @GetMapping("/connexion")
    public String afficherConnexion(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "view-connexion";
    }


    @GetMapping("/inscription")
    public String afficherCreationProfil(@ModelAttribute Utilisateur utilisateur, Model model) {

        utilisateurService.creerCompte(utilisateur);
        model.addAttribute("utilisateur", utilisateur);
        return "view-inscription";
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
            return "redirect:/encheres";

        }
    }


}