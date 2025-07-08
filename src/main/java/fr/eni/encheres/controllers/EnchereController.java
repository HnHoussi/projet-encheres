package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.ArticleService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
@Controller
@SessionAttributes({ "categoriesEnSession" })
@RequestMapping({"/encheres", "/"})
public class EnchereController {

    private ArticleService articleService;
    private CategorieService  categorieService;
    // Simulated connected user ID
    private static final Long CONNECTED_USER_ID = 1L;

    public EnchereController(ArticleService articleService, CategorieService categorieService) {
        this.articleService = articleService;
        this.categorieService = categorieService;
    }

    // Charger la liste des catégories
    @ModelAttribute("categoriesEnSession")
    public List<Categorie> chargerCategoriesEnSession() {
        return this.categorieService.consulterCategories();
    }

    //afficher la page d'acceuil avant la connection d'utilisateur
    @GetMapping
    public String rechercherEncheres(
            @RequestParam(required = false) String motClesEnchere,
            @RequestParam(required = false) Long idCategorie,
            @RequestParam(required = false, defaultValue = "achats") String filtrePrincipal,  // e.g., "achats" or "ventes"
            @RequestParam(required = false) List<String> sousFiltres, // e.g., ["ouvertes", "mesEncheres", "mesVentes"]
            Model model,
            @ModelAttribute("categoriesEnSession") List<Categorie> categoriesEnSession) {

        // Example: if sousFiltres is null, set default (e.g., "ouvertes")
        if (sousFiltres == null || sousFiltres.isEmpty()) {
            sousFiltres = Arrays.asList("ouvertes");
        }

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


}
