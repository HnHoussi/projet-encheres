package fr.eni.encheres.bo;

import java.util.Objects;

public class Retrait {
    private String rue;
    private String codePostal;
    private String ville;

    private Article article;

    public Retrait() {
    }

    public Retrait(String rue, String codePostal, String ville, Article article) {
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.article = article;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Retrait{");
        sb.append("rue='").append(rue).append('\'');
        sb.append(", codePostal='").append(codePostal).append('\'');
        sb.append(", ville='").append(ville).append('\'');
        sb.append(", article=").append(article);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Retrait retrait = (Retrait) o;
        return Objects.equals(rue, retrait.rue) && Objects.equals(codePostal, retrait.codePostal) && Objects.equals(ville, retrait.ville) && Objects.equals(article, retrait.article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rue, codePostal, ville, article);
    }
}
