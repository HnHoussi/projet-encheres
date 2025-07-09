package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@SessionAttributes("utilisateurSession")
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

    // 🔐 Affiche la page de connexion personnalisée
    @GetMapping("/login")
    public String afficherConnexion(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "view-connexion";
    }

    /**
     * ✅ Cette méthode est appelée après une connexion réussie (voir defaultSuccessUrl)
     * Elle récupère le pseudo de l'utilisateur connecté,
     * le charge depuis la BDD, puis le place en session (clé : utilisateurSession)
     */
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

    // 🏠 Affiche la page d’accueil (vue index.html)
    @GetMapping("/encheres")
    public String afficherAccueil(Model model,
                                  @SessionAttribute(name = "utilisateurSession", required = false) Utilisateur utilisateurSession) {
        if (utilisateurSession != null) {
            model.addAttribute("utilisateurSession", utilisateurSession);
            System.out.println(utilisateurSession);
        }
        return "index";
    }



    // 📝 Affiche le formulaire d’inscription
    @GetMapping("/inscription")
    public String afficherCreationProfil(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "view-inscription";
    }

    /**
     * 🧾 Crée un compte utilisateur :
     * - encode le mot de passe
     * - enregistre l'utilisateur
     * - redirige vers /encheres
     */
    @PostMapping("/profil-creer")
    public String creationCompte(@ModelAttribute Utilisateur utilisateur,@RequestParam String confirmation, Model model) {
        if (!utilisateur.getMotDePasse().equals(confirmation)) {
            model.addAttribute("messageErreur", "Les mots de passe ne correspondent pas.");
            return "view-inscription";
        }

        try {
            System.out.println("Mot de passe en clair : " + utilisateur.getMotDePasse());
            String motDePasseEncode = passwordEncoder.encode(utilisateur.getMotDePasse());
            utilisateur.setMotDePasse(motDePasseEncode);
            System.out.println("Mot de passe encodé : " + motDePasseEncode);
            utilisateurService.creerCompte(utilisateur);
            return "redirect:/encheres";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("messageErreur", "Erreur lors de la création du compte.");
            return "view-inscription";
        }
    }
}
