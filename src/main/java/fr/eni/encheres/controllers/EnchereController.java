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

    @GetMapping("/")
    public String afficherEncheres(
            Model model,
            @ModelAttribute("categoriesEnSession") List<Categorie> categoriesEnSession) {

        List<Enchere> encheres = enchereService.consulterEncheres();
        encheres.forEach(System.out::println);

        model.addAttribute("listEncheres", encheres);
        model.addAttribute("categoriesEnSession", categoriesEnSession);
        return "view-encheres";
    }

    @GetMapping("/recherche")
    public String rechercheEncheres(
            @RequestParam String motClesEnchere,
            Model model,
            @ModelAttribute("categoriesEnSession") List<Categorie> categoriesEnSession) {

        List<Enchere> encheres = enchereService.consulterEnchereparMotCles(motClesEnchere);

        model.addAttribute("motClesEnchere", motClesEnchere);
        model.addAttribute("categoriesEnSession", categoriesEnSession);
        model.addAttribute("listEncheres", encheres);

        return  "view-encheres";
    }

    @GetMapping("/categorie")
    public String afficherEncheresParCategorie(
            @RequestParam long idCategorie,
            Model model,
            @ModelAttribute("categoriesEnSession") List<Categorie> categoriesEnSession) {

        List<Enchere> encheres = enchereService.consulterEnchereparCategorie(idCategorie);

        model.addAttribute("categoriesEnSession", categoriesEnSession);
        model.addAttribute("listEncheres", encheres);

        return "view-encheres";

    }
}
