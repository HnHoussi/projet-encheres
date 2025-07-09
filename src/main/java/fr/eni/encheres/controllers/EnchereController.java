package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.ArticleService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes({"categoriesEnSession", "utilisateurSession"})
@RequestMapping({"/encheres", "/"})
public class EnchereController {

    private final ArticleService articleService;
    private final CategorieService categorieService;

    public EnchereController(ArticleService articleService, CategorieService categorieService) {
        this.articleService = articleService;
        this.categorieService = categorieService;
    }

    // Injecte les catégories en session
    @ModelAttribute("categoriesEnSession")
    public List<Categorie> chargerCategoriesEnSession() {
        return this.categorieService.consulterCategories();
    }

    @GetMapping
    public String rechercherEncheres(
            @RequestParam(required = false) String motClesEnchere,
            @RequestParam(required = false) Long idCategorie,
            @SessionAttribute(name = "utilisateurSession", required = false) Utilisateur utilisateurSession,
            Model model,
            @ModelAttribute("categoriesEnSession") List<Categorie> categoriesEnSession) {

        // Ajouter l'utilisateur dans le modèle s'il est en session
        if (utilisateurSession != null) {
            model.addAttribute("utilisateurSession", utilisateurSession);
        }

        List<Article> articles;

        // Recherche combinée : mot-clé + catégorie
        if ((motClesEnchere != null && !motClesEnchere.isBlank()) && idCategorie != null) {
            articles = articleService.consulterArticleParCategorieEtMotCles(idCategorie, motClesEnchere);

            // Recherche uniquement par mot-clé
        } else if (motClesEnchere != null && !motClesEnchere.isBlank()) {
            articles = articleService.consulterArticlesParMotCles(motClesEnchere);

            // Recherche uniquement par catégorie
        } else if (idCategorie != null) {
            articles = articleService.consulterArticlesParCategorie(idCategorie);

            // Pas de filtre : tous les articles
        } else {
            articles = articleService.consulterArticles();
        }

        // Injecter les données dans le modèle
        model.addAttribute("listArticles", articles);
        model.addAttribute("motClesEnchere", motClesEnchere);
        model.addAttribute("idCategorie", idCategorie);

        return "index";
    }
}
