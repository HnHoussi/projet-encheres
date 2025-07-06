package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@SessionAttributes({ "categoriesEnSession" })
@RequestMapping("/encheres")
public class EnchereController {

    private EnchereService enchereService;
    private CategorieService  categorieService;

    public EnchereController(EnchereService enchereService, CategorieService categorieService) {
        this.enchereService = enchereService;
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

        List<Enchere> encheres;

        // Recherche par mots clés et catégorie
        if ((motClesEnchere != null && !motClesEnchere.isBlank()) && idCategorie != null) {
            encheres = enchereService.consulterEnchereparCategorieEtMotCles(idCategorie, motClesEnchere);

        // Recherche par mots clés seulement
        } else if (motClesEnchere != null && !motClesEnchere.isBlank()) {
            encheres = enchereService.consulterEnchereparMotCles(motClesEnchere);

        // Recherche par catégorie seulement
        } else if (idCategorie != null) {
            encheres = enchereService.consulterEnchereparCategorie(idCategorie);

        // Lister tous les enchéres sans filtre
        } else {
            encheres = enchereService.consulterEncheres();
        }

        model.addAttribute("listEncheres", encheres);
        model.addAttribute("categoriesEnSession", categoriesEnSession);
        model.addAttribute("motClesEnchere", motClesEnchere);
        model.addAttribute("idCategorie", idCategorie);

        return "index";
    }

}
