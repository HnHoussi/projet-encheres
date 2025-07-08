package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.ArticleService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@SessionAttributes({ "categoriesEnSession" })
@RequestMapping({"/encheres", "/"})
public class EnchereController {

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

    /**
     * Gestion page9 - nouvelle vente
     * @return
     */
    //ETAPE 1 : initialiser les données du formulaire de nouvelle vente
    @GetMapping("/vendreArticle")
    public String vendreArticle(Model model,
                                @ModelAttribute("categoriesEnSession") List<Categorie> categoriesEnSession) {

        //recuperer l'utilisateur qui crée la vente par son id
        //Utilisateur utilisateurConnecte =

        //initialisation d'une nouvelle vente = nouvel article
        Article nouvelleVente = new Article();
        model.addAttribute("article", nouvelleVente);

        //initialisation d'un retrait avec adresse utilisateur par défaut
        Retrait lieuRetrait = new Retrait();
        lieuRetrait.setRue(UtilisateurConnecte.getRue());
        lieuRetrait.setCodePostal(UtilisateurConnecte.getCodePostal());
        lieuRetrait.setVille(UtilisateurConnecte.getVille());

        model.addAttribute("retrait", lieuRetrait);
        model.addAttribute("utilisateurConnecte", utilisateurConnecte);

        return "page9";
    }


    //ETAPE 3 : lecture des données du formulaire avec @ModelAttribute
    //creation d'une nouvelle vente - nouvel article
    @PostMapping("/vendreArticle")
    public String vendreArticle(@Valid @ModelAttribute("article") Article article, BindingResult bindingResult,
                                @ModelAttribute("retrait") Retrait lieuRetrait,
                                @RequestParam("idCategorie") Long idCategorie,
                                Model model
    ) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("article", article);
            model.addAttribute("retrait", lieuRetrait);
            model.addAttribute("idCategorie", idCategorie);
            model.addAttribute("utilisateurConnecte", utilisateurConnecte);

            return "page9";//reste sur la meme page si erreur
        }

        this.articleService.vendreArticle(article);//enregistrement de l'article

        lieuRetrait.setArticle(article.getArticle());

        return "page10"; //envoie vers la page nouvelle vente avec bouton annuler la vente
    }

}
