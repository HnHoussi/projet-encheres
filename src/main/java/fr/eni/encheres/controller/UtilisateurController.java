package fr.eni.encheres.controller;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.session.*; // adapte le package ici
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@Controller
@RequestMapping("/utilisateur")
@SessionAttributes("membreSession")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/liste")
    public String consulterUtilisateurs(Model model) {
        List<Utilisateur> utilisateurs = utilisateurService.consulterListeUtilisateurs();
        model.addAttribute("selectAll", utilisateurs);
        return "listeUtilisateur";
    }

    @GetMapping("/afficher")
    public String afficherUtilisateur(@RequestParam("id") long id, Model model) {
        Utilisateur u = utilisateurService.consulterUtilisateur(id);
        model.addAttribute("utilisateur", u);
        return "afficherUtilisateur";
    }

    @GetMapping("/inscription")
    public String formulaireInscription() {
        return "inscription";
    }

    @PostMapping("/inscription")
    public String inscrireUtilisateur(@ModelAttribute Utilisateur utilisateur, Model model) {
        utilisateurService.creerCompte(utilisateur);
        MembreSession membre = new MembreSession(utilisateur);
        model.addAttribute("membreSession", membre);
        return "redirect:/accueil";
    }

    @GetMapping("/modifier")
    public String formulaireModification(Model model, @SessionAttribute("membreSession") MembreSession membre) {
        model.addAttribute("utilisateur", membre.getUtilisateur());
        return "modifier";
    }

    @PostMapping("/modifier")
    public String modifierProfil(@ModelAttribute Utilisateur utilisateur, Model model) {
        utilisateurService.modifierProfil(utilisateur);
        model.addAttribute("membreSession", new MembreSession(utilisateur));
        return "redirect:/monCompte";
    }

    @PostMapping("/supprimer")
    public String supprimerUtilisateur(@RequestParam("id") long id, SessionStatus status) {
        utilisateurService.supprimerCompte(id);
        status.setComplete();
        return "redirect:/accueil";
    }

    @GetMapping("/login")
    public String formulaireLogin() {
        return "seConnecter";
    }

    @PostMapping("/login")
    public String connecterUtilisateur(
            @RequestParam String email,
            @RequestParam String motDePasse,
            Model model) {

        Utilisateur utilisateur = utilisateurService.connexion(email, motDePasse); // à implémenter si ca marche pas

        if (utilisateur != null) {
            model.addAttribute("membreSession", new MembreSession(utilisateur));
            return "redirect:/accueil";
        } else {
            model.addAttribute("error", "Email ou mot de passe incorrect.");
            return "seConnecter";
        }
    }

    @GetMapping("/logout")
    public String deconnexion(SessionStatus status) {
        status.setComplete();
        return "redirect:/accueil";
    }
}
