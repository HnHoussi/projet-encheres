package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.ArticleService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
@Controller
@SessionAttributes({"categoriesEnSession", "utilisateurSession"})
@RequestMapping({"/encheres", "/"})
public class EnchereController {

    private ArticleService articleService;
    private CategorieService  categorieService;
    private UtilisateurService utilisateurService;
    // Simulated connected user ID
    private static final Long CONNECTED_USER_ID = 1L;

    public EnchereController(ArticleService articleService, CategorieService categorieService, UtilisateurService utilisateurService) {
        this.articleService = articleService;
        this.categorieService = categorieService;
        this.utilisateurService = utilisateurService;
    }

    // Charger la liste des catégories
    @ModelAttribute("categoriesEnSession")
    public List<Categorie> chargerCategoriesEnSession() {
        return this.categorieService.consulterCategories();
    }

    @GetMapping
    public String rechercherEncheres(
            @RequestParam(required = false) String motClesEnchere,
            @RequestParam(required = false) Long idCategorie,
            @RequestParam(required = false, defaultValue = "achats") String filtrePrincipal,  // e.g., "achats" or "ventes"
            @RequestParam(required = false) List<String> sousFiltres, // e.g., ["ouvertes", "mesEncheres", "mesVentes"]
            @SessionAttribute(name = "utilisateurSession", required = false) Utilisateur utilisateurSession,
            Model model,
            @ModelAttribute("categoriesEnSession") List<Categorie> categoriesEnSession) {

        // Example: if sousFiltres is null, set default (e.g., "ouvertes")
        if (sousFiltres == null || sousFiltres.isEmpty()) {
            sousFiltres = Arrays.asList("ouvertes");
        }
        // Ajouter l'utilisateur dans le modèle s'il est en session
        if (utilisateurSession != null) {
            model.addAttribute("utilisateurSession", utilisateurSession);
        }

        List<Article> articles;

        // Recherche combinée : mot-clé + catégorie
        if ((motClesEnchere != null && !motClesEnchere.isBlank()) && idCategorie != null) {
            articles = articleService.consulterArticleParCategorieEtMotCles(idCategorie, motClesEnchere);

        // Call the service with dynamic filters
        List<Article> articles = articleService.findArticlesWithDynamicFilters(
                CONNECTED_USER_ID,
                motClesEnchere,
                idCategorie,
                filtrePrincipal,
                sousFiltres
        );

        // Add to model
        model.addAttribute("listArticles", articles);
        model.addAttribute("categoriesEnSession", categoriesEnSession);
        model.addAttribute("motClesEnchere", motClesEnchere);
        model.addAttribute("idCategorie", idCategorie);
        model.addAttribute("filtrePrincipal", filtrePrincipal);
        model.addAttribute("sousFiltres", sousFiltres);

        return "index";
    }

    @GetMapping("/utilisateur")
    public String afficherProfilVendeur(@RequestParam("idUtilisateur") Long idUtilisateur, Model model) {
        Utilisateur vendeur = utilisateurService.consulterUtilisateur(idUtilisateur);
        model.addAttribute("vendeur", vendeur);
        return "view-vendeur-details"; // The Thymeleaf view to show vendeur details
    }

    @GetMapping("/article")
    public String afficherDetailsArticle(@RequestParam("idArticle") Long idArticle, Model model) {
        // Fetch the article from the service
        Article article = articleService.consulterArticleById(idArticle);

        if (article == null) {
            // handle the case where article is not found
            return "redirect:/encheres";
        }

        // Add the article to the model
        model.addAttribute("article", article);

        return "view-detail-article";
    }



    @GetMapping("/vendre-article")
    public String showNouvelleVenteForm(Model model,
                                        @ModelAttribute("categoriesEnSession") List<Categorie> categoriesEnSession) {
        Article article = new Article();

        // Simulated connected user
        Utilisateur utilisateurConnecte = utilisateurService.consulterUtilisateur(CONNECTED_USER_ID);

        // Create default retrait object with user's address
        Retrait retrait = new Retrait();
        retrait.setRue(utilisateurConnecte.getRue());
        retrait.setCodePostal(utilisateurConnecte.getCodePostal());
        retrait.setVille(utilisateurConnecte.getVille());

        article.setRetrait(retrait);

        model.addAttribute("article", article);
        model.addAttribute("categories", categoriesEnSession);
        model.addAttribute("utilisateur", utilisateurConnecte);

        return "view-nouvelle-vente";
    }




}
