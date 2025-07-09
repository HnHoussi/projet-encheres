package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.ArticleService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@SessionAttributes({ "categoriesEnSession" })
@RequestMapping({"/encheres", "/"})
public class git checkout mainEnchereController {

    private ArticleService articleService;
    private CategorieService  categorieService;

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
            Model model,
            @ModelAttribute("categoriesEnSession") List<Categorie> categoriesEnSession) {

        List<Article> articles;

        // Recherche par mots clés et catégorie
        if ((motClesEnchere != null && !motClesEnchere.isBlank()) && idCategorie != null) {
            articles = articleService.consulterArticleParCategorieEtMotCles(idCategorie, motClesEnchere);

        // Recherche par mots clés seulement
        } else if (motClesEnchere != null && !motClesEnchere.isBlank()) {
            articles = articleService.consulterArticlesParMotCles(motClesEnchere);

        // Recherche par catégorie seulement
        } else if (idCategorie != null) {
            articles = articleService.consulterArticlesParCategorie(idCategorie);

        // Lister tous les enchéres sans filtre
        } else {
            articles = articleService.consulterArticles();
        }

        model.addAttribute("listArticles", articles);
        model.addAttribute("categoriesEnSession", categoriesEnSession);
        model.addAttribute("motClesEnchere", motClesEnchere);
        model.addAttribute("idCategorie", idCategorie);

        return "index";
    }

}
