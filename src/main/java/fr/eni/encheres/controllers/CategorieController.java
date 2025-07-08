package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bo.Categorie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategorieController {
    private CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping("/supprimer")
    public String supprimerCategorie(@RequestParam(required = true) Long idCategorie){
        categorieService.supprimerCategorie(idCategorie);
        return "redirect:/";
    }

    @PostMapping
    public String creerCategorie(@ModelAttribute("categorie") Categorie categorie) {
        categorieService.creerCategorie(categorie);
        return "redirect:/";
    }

    @PostMapping("/modifierCategorie")
    public String modifierCategorie(@RequestParam(required = true) Long idCategorie, @ModelAttribute Categorie categorie) {
        Categorie categorieAModifier = categorieService.consulterCategorieParID(idCategorie);
        categorieService.modifierCategorie(categorieAModifier);
        return "redirect:/";
    }

    public String consulterCategories(Model model) {
        List<Categorie> categorieList = categorieService.consulterCategories();
        model.addAttribute("categorieList", categorieList);
        return "categorie";
    }
}
