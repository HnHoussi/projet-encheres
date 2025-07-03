package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.ArticleService;
import fr.eni.encheres.bo.Article;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ArticlesController {
    private ArticleService articleService;

    public ArticlesController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public String articles(Model model) {
        List<Article> articles = articleService.consulterArticles();
        articles.forEach(System.out::println);

        model.addAttribute("articles", articles);

        return "view-articles";
    }
}
