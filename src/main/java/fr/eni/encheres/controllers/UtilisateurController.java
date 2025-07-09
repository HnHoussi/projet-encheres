package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bll.UtilisateurServiceImpl;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@Controller
@RequestMapping("/utilisateur")
@SessionAttributes("utilisateurSession")

public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final EnchereService enchereService;
    private final CategorieService categorieService;
    private final UtilisateurServiceImpl utilisateurServiceImpl;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurController(UtilisateurService utilisateurService,EnchereService enchereService, CategorieService categorieService,
                                 UtilisateurServiceImpl utilisateurServiceImpl, PasswordEncoder passwordEncoder) {

        this.utilisateurService = utilisateurService;
        this.enchereService = enchereService;
        this.categorieService = categorieService;
        this.utilisateurServiceImpl = utilisateurServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute("utilisateurSession")
    public Utilisateur utilisateurSession() {
        return new Utilisateur();
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
    public String formulaireInscription(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "view-inscription";
    }

    @PostMapping("/inscription")
    public String inscrireUtilisateur(@ModelAttribute Utilisateur utilisateur, Model model) {
        try {
            String motDePasseEncode = passwordEncoder.encode(utilisateur.getMotDePasse());
            utilisateur.setMotDePasse(motDePasseEncode);
            utilisateurService.creerCompte(utilisateur);
            return "redirect:/utilisateur/login";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("messageErreur", "Erreur lors de la création du compte.");
            return "view-inscription";
        }
    }

    @GetMapping("/modifier")
    public String formulaireModification(Model model, @SessionAttribute("utilisateurSession") Utilisateur utilisateur) {
        model.addAttribute("utilisateur", utilisateur);
        return "modifierProfil";
    }

    @PostMapping("/modifier")
    public String modifierProfil(@ModelAttribute Utilisateur utilisateur, Model model) {
        utilisateurService.modifierProfil(utilisateur);
        model.addAttribute("utilisateurSession", utilisateur);
        return "redirect:/utilisateur/mon-profil";
    }

    @GetMapping("/logout")
    public String deconnexion(SessionStatus status) {
        status.setComplete();
        return "redirect:/accueil";
    }

    @GetMapping("/utilisateursession")
    public String chargerUtilisateurSession(@ModelAttribute("utilisateurSession") Utilisateur utilisateurSession,
                                            @RequestParam(name = "email", required = false, defaultValue = "jtrillard@campus-eni.fr") String email) {

        Utilisateur aCharger = utilisateurServiceImpl.charger(email);

        if (aCharger != null) {
            utilisateurSession.setIdUtilisateur(aCharger.getIdUtilisateur());
            utilisateurSession.setNom(aCharger.getNom());
            utilisateurSession.setPrenom(aCharger.getPrenom());
            utilisateurSession.setPseudo(aCharger.getPseudo());
            utilisateurSession.setAdministrateur(aCharger.isAdministrateur());
        } else {
            utilisateurSession.setIdUtilisateur(0);
            utilisateurSession.setNom(null);
            utilisateurSession.setPrenom(null);
            utilisateurSession.setPseudo(null);
            utilisateurSession.setAdministrateur(false);
        }

        return "mon-profil";
    }

    @GetMapping("/mon-profil")
    public String afficherMonProfil(Model model, @SessionAttribute("utilisateurSession") Utilisateur utilisateur) {
        model.addAttribute("utilisateur", utilisateur);
        return "mon-profil";
    }

    @PostMapping("/supprimer")
    public String supprimerUtilisateur(@RequestParam("id") long id, SessionStatus status) {
        utilisateurService.supprimerCompte(id);
        status.setComplete();
        return "redirect:/accueil";
    }

    @GetMapping("/login")
    public String formulaireLogin(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "view-connexion";
    }

    @GetMapping("/after-login")
    public String postConnexion(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String pseudo = authentication.getName();
        Utilisateur utilisateur = utilisateurService.consulterUtilisateurParPseudo(pseudo);

        if (utilisateur != null) {
            model.addAttribute("utilisateurSession", utilisateur);
        }

        return "redirect:/encheres";
    }

    @GetMapping("/encheres")
    public String afficherAccueil(Model model,
                                  @SessionAttribute(name = "utilisateurSession", required = false) Utilisateur utilisateurSession) {
        if (utilisateurSession != null) {
            model.addAttribute("utilisateurSession", utilisateurSession);
        }
        return "index";
    }
}
