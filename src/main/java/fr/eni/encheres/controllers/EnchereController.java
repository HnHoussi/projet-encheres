package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.ArticleService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.UtilisateurService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Controller
@SessionAttributes({ "categoriesEnSession" })
@RequestMapping({"/encheres", "/"})
public class EnchereController {

    private ArticleService articleService;
    private CategorieService  categorieService;
    private UtilisateurService utilisateurService;
   
  //ID UTILISATEUR CONNECTE FORCE A 1 POUR TEST
    private static final long CONNECTED_USER_ID = 1L; //valeur pour test a la place de utilisateurConnecte
    
    //private RetraitService retraitService;
    
    
    public EnchereController(ArticleService articleService, CategorieService categorieService, UtilisateurService utilisateurService) //, RetraitService retraitService)
    {
        this.articleService = articleService;
        this.categorieService = categorieService;
        this.utilisateurService = utilisateurService;
        //this.retraitService = retraitService;
    }

    // Charger la liste des catégories en session
    @ModelAttribute("categoriesEnSession")
    public List<Categorie> chargerCategoriesEnSession() {
        return this.categorieService.consulterCategories();
    }

    //afficher la page d'accueil avant la connection d'utilisateur
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

        return "²";
    }
    
    
    

    /**
     * GESTION PAGE9 - NOUVELLE VENTE
     * @return
     */
    
    //ETAPE 1 : initialiser les données du formulaire de nouvelle vente
    @GetMapping("/vendreArticle")
    public String vendreArticle(Model model,
                                @ModelAttribute("categoriesEnSession") List<Categorie> categoriesEnSession) {
    	
    	System.out.println("appel de la methode GET vendreArticle");//test
    	
        //recuperer l'utilisateur qui crée la vente par son id
       /* Utilisateur utilisateurConnecte = new Utilisateur(); //valeur pour test : à revoir
        System.out.println("creation utilisateurConnecte");
        utilisateurConnecte.setIdUtilisateur(1);
        utilisateurConnecte.setRue("1 rue de Paris");
        utilisateurConnecte.setCodePostal("44000");
        utilisateurConnecte.setVille("NANTES");
       */
    	
   //UTILISATEUR CONNECTE
    	Utilisateur utilisateurConnecte = utilisateurService.consulterUtilisateur(CONNECTED_USER_ID);
        
    	
  //NOUVELLE VENTE
    	//initialisation d'une nouvelle vente = nouvel article et ajout dans le model
        Article nouvelleVente = new Article();
        
        nouvelleVente.setUtilisateur(utilisateurConnecte); //association de la vente à l'utilisateur connecté
    	
        nouvelleVente.setEtatVente("NON DEBUTEE"); // enregistrement etat de la vente : FAIRE METHOD POUR CONDITON DE DATE
        
        System.out.println("affichage de l'article");//test
        
        model.addAttribute("article", nouvelleVente);//passage des donnees dans le model
  
        
  //RETRAIT ASSOCIE
        
        System.out.println("initialisation retrait"); //test
        
        
      //initialisation d'un retrait avec adresse utilisateur par défaut
        Retrait retrait = new Retrait();
        retrait.setRue(utilisateurConnecte.getRue());
        retrait.setCodePostal(utilisateurConnecte.getCodePostal());
        retrait.setVille(utilisateurConnecte.getVille());
        
        //ajout dans le model
        System.out.println("affichage du retrait");//test
        
        model.addAttribute("retrait", retrait);
        
        //garder les donnees utilisateurConnecte pour la method POST
        model.addAttribute("utilisateur", utilisateurConnecte);

        return "page9";
    }


    //ETAPE 3 : lecture des données du formulaire avec @ModelAttribute
    //creation d'une nouvelle vente - nouvel article
    @PostMapping("/vendreArticle")
    public String vendreArticle(@ModelAttribute("article") Article article,
                                @ModelAttribute("retrait") Retrait retrait,
                               	@RequestParam("idCategorie") Long idCategorie,
                               	Model model,
                               	@ModelAttribute("categoriesEnSession") List<Categorie> categoriesEnSession)
    {
    	System.out.println("appel de la methode POST vendreArticle"); //test
    	
    //UTILISATEUR
    	Utilisateur utilisateurConnecte = utilisateurService.consulterUtilisateur(CONNECTED_USER_ID);
    	//recuperer l'utilisateur qui crée la vente par son id
    	
        /*Utilisateur utilisateurConnecte = new Utilisateur(); //valeur pour test : à revoir
         System.out.println("creation utilisateurConnecte");
         utilisateurConnecte.setIdUtilisateur(1);
         utilisateurConnecte.setRue("1 rue de Paris");
         utilisateurConnecte.setCodePostal("44000");
         utilisateurConnecte.setVille("NANTES");
         */
        article.setUtilisateur(utilisateurConnecte);
    	
    //CATEGORIE	
    	//recuperer et associer la categorie
    	Categorie categorie = categorieService.consulterCategorieParID(idCategorie);
    	article.setCategorie(categorie);
    	
    	//gestion etat Vente
    	
    	// récupère la date et l'heure actuelles
       // LocalDateTime datetime = LocalDateTime.now(); A VOIR DANS TRANSACTIONAL
       
   //ARTICLE
    	//enregistrement de l'article et de son retrait
    	this.articleService.vendreArticle(article);
    
        
    //RETRAIT   
        retrait.setArticle(article);
        //this.retraitService.creerRetrait(retrait);
        System.out.println("enregistrement de l'article");
        
        return "redirect:/encheres"; //envoie vers la page d'accueil
    }

}
