package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/utilisateur")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final EnchereService enchereService;
    private final CategorieService categorieService;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurController(UtilisateurService utilisateurService,
                                 EnchereService enchereService,
                                 CategorieService categorieService,
                                 PasswordEncoder passwordEncoder) {
        this.utilisateurService = utilisateurService;
        this.enchereService = enchereService;
        this.categorieService = categorieService;
        this.passwordEncoder = passwordEncoder;
    }

    // 🎯 Affiche le formulaire de connexion
    @GetMapping("/login")
    public String afficherConnexion(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "view-connexion";
    }


    // 🎯 Affiche le formulaire d'inscription
    @GetMapping("/inscription")
    public String afficherCreationProfil(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "view-inscription";
    }

    // ✅ Crée un nouveau compte en encodant le mot de passe
    @PostMapping("/profil-creer")
    public String creationCompte(@ModelAttribute Utilisateur utilisateur, Model model) {
        try {
            String motDePasseEncode = passwordEncoder.encode(utilisateur.getMotDePasse());
            utilisateur.setMotDePasse(motDePasseEncode);
            utilisateurService.creerCompte(utilisateur);
            return "redirect:/encheres";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("messageErreur", "Erreur lors de la création du compte.");
            return "view-inscription";
        }
    }
}
