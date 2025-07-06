package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.ArticleService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/encheres")
public class UtilisateurController {

    private UtilisateurService utilisateurService;
    private EnchereService enchereService;
    private CategorieService categorieService;
    private ArticleService articleService;


    public UtilisateurController(UtilisateurService utilisateurService, EnchereService enchereService, CategorieService categorieService, ArticleService articleService) {
        this.utilisateurService = utilisateurService;
        this.enchereService = enchereService;
        this.categorieService = categorieService;
        this.articleService = articleService;
    }

    @GetMapping
    public String afficherIndex(Model model) {

        List<Utilisateur> utilisateurs=utilisateurService.consulterListeUtilisateurs();
        List<Categorie> categories=categorieService.consulterCategories();
        List<Article> articles=articleService.consulterArticles();

        model.addAttribute("utilisateurs",utilisateurs);
        model.addAttribute("articles", articles);
        model.addAttribute("categories",categories);
        System.out.println("Nombre d'articles récupérées : " + articles.size());

        return "index";

    }
    @GetMapping("/affcherAccueil")
    public String afficherAccueil() {
        return "redirect:/encheres";
    }

    @GetMapping("/connexion")
    public String afficherConnexion(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "view-connexion";
    }



    @GetMapping("/creer-profil")
    public String afficherCreationProfil(@ModelAttribute Utilisateur utilisateur, Model model) {

        utilisateurService.creerCompte(utilisateur);
        model.addAttribute("utilisateur", utilisateur);
        return "view-creer-profil";
    }

    @GetMapping("/detail")
    public String detailArticleParParametre(
            @RequestParam(name = "id", required = true) long idArticle, Model model){
        Article article = articleService.consulterArticle(idArticle);
        model.addAttribute("article", article);
        return "view-article-detail";
    }
    @GetMapping("/vendeur")
    public String afficherVendeur(@RequestParam(name = "id", required = true) long idVendeur, Model model) {
        Utilisateur utilisateur = utilisateurService.consulterUtilisateur(idVendeur);
        model.addAttribute("utilisateur", utilisateur);
        return "view-profil-vendeur";
    }


    @PostMapping("/profil-creer")
    public String creationCompte(@ModelAttribute Utilisateur utilisateur, Model model) {
        try {
            utilisateurService.creerCompte(utilisateur);
            model.addAttribute("utilisateur", utilisateur);
            return "redirect:/encheres/connexion";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("messageErreur", "Erreur lors de la création du compte");
            return "redirect:/encheres/connexion";

        }
    }


}
