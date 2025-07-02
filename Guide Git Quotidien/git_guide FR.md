# 🛠 Guide Git Quotidien (Équipe de 4)

Ce guide explique quoi faire chaque jour : comment travailler sur ta branche personnelle et comment fusionner ta fonctionnalité dans `develop`.

---

## 📦 Branches
- `main` → code stable et prêt pour la production
- `develop` → branche d’intégration où chacun fusionne ses fonctionnalités terminées
- Ta branche → ex. `houcine`, `aissa`, `jassim`, `milor`

---

## ✅ **Le matin (avant de commencer)**

```bash
# Se placer sur ta branche
git checkout houcine

# Mettre à jour ta branche locale depuis le remote (au cas où tu as déjà poussé hier)
git pull

# Récupérer les dernières modifications de l’équipe depuis develop
git pull origin develop
```

---

## ✏️ **Pendant que tu travailles**
- Modifier les fichiers et ajouter de nouvelles fonctionnalités.
- Sauvegarder tes changements localement :

```bash
git add .
git commit -m "message clair sur ce que tu as fait"
```

- Pousser ton travail pour que tes coéquipiers le voient :

```bash
git push origin houcine
```

> 💡 De petits commits clairs sont mieux qu’un gros commit unique.

---

## 🔄 **Mettre à jour ta branche avec develop**

Quand l’équipe a fusionné de nouvelles fonctionnalités dans `develop` :

```bash
git checkout houcine
git pull origin develop
```

Résoudre les conflits si besoin.

---

## 🚀 **Quand ta fonctionnalité est prête**

Fusionner ta branche dans `develop` :

```bash
# Se placer sur develop
git checkout develop

# Mettre à jour develop depuis le remote
git pull origin develop

# Fusionner ta branche dans develop
git merge houcine

# Pousser develop mis à jour
git push origin develop
```

Ou créer une merge request / pull request de `houcine` → `develop`.

---

## ✅ **Résumé rapide (copier/coller) :**

```bash
# Avant de commencer
git checkout houcine
git pull
git pull origin develop

# Après avoir travaillé
git add .
git commit -m "description"
git push origin houcine
```

```bash
# Quand la fonctionnalité est prête
git checkout develop
git pull origin develop
git merge houcine
git push origin develop
```

---

## 📈 Schéma visuel

```
main
  |
  o---------o---------o   ← versions stables en production
            \
             develop
              o----o----o----o   ← intégration des fonctionnalités
               \    \    \    \
                \    \    \    houcine
                 \    \    o--o--o   ← ta branche personnelle
                  \    aissa
                   \    o--o--o
                    \
                     jassim
                      o--o--o
```

> Chaque membre a sa propre branche. Quand la fonctionnalité est prête, on fusionne dans `develop`. Quand `develop` est stable, on fusionne dans `main`.

---

Si tu veux, je peux aussi exporter ce fichier en `GIT_WORKFLOW.md` pour ton dépôt ! 🚀

