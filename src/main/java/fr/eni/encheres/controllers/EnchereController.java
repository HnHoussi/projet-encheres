package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.ArticleService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@Controller
@SessionAttributes({ "categoriesEnSession" })
@RequestMapping({"/encheres", "/"})
public class EnchereController {

    private ArticleService articleService;
    private CategorieService  categorieService;
    
   // private long idUtilisateur = 1L; //valeur pour test a la place de utilisateurConnecte

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
        Utilisateur utilisateurConnecte = new Utilisateur(); //valeur pour test : à revoir
        utilisateurConnecte.setIdUtilisateur(1);
        utilisateurConnecte.setRue("1 rue de Paris");
        utilisateurConnecte.setCodePostal("44000");
        utilisateurConnecte.setVille("NANTES");
       
        //initialisation d'une nouvelle vente = nouvel article et ajout dans le model
        //Article nouvelleVente = new Article();
        //nouvelleVente.setUtilisateur(utilisateurConnecte);
        model.addAttribute("article", new Article());

        //initialisation d'un retrait avec adresse utilisateur par défaut
        Retrait retrait = new Retrait();
        retrait.setRue(utilisateurConnecte.getRue());
        retrait.setCodePostal(utilisateurConnecte.getCodePostal());
        retrait.setVille(utilisateurConnecte.getVille());
        
        //ajout dans le model
        model.addAttribute("retrait", retrait);
        //model.addAttribute("utilisateurConnecte", utilisateurConnecte);

        return "page9";
    }


    //ETAPE 3 : lecture des données du formulaire avec @ModelAttribute
    //creation d'une nouvelle vente - nouvel article
    @PostMapping("/vendreArticle")
    public String vendreArticle(@ModelAttribute("article") Article article,
                                @ModelAttribute("retrait") Retrait retrait)
                                //@RequestParam("idCategorie") Long idCategorie,
    {
    	 
    	
        this.articleService.vendreArticle(article);//enregistrement de l'article
        article.setEtatVente("CREEE");
        //retrait.setArticle(article.getArticle());

        return "redirect:/encheres"; //envoie vers la page d'accueil
    }

}
