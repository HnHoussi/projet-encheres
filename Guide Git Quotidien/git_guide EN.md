# 🛠 Daily Git Workflow Cheat Sheet (Team of 4)

This guide explains what to do each day, while working on your own branch, and when your feature is ready to merge into `develop`.

---

## 📦 Branches
- `main` → always stable, production-ready code
- `develop` → integration branch where everyone merges finished features
- Your branch → e.g., `houcine`, `aissa`, `jassim`, `milor`

---

## ✅ **Start your day (morning)**
```bash
# Switch to your branch
git checkout houcine

# Update your local branch from the remote (in case you pushed yesterday)
git pull

# Bring latest team changes from develop into your branch
git pull origin develop
```

---

## ✏️ **While working**
- Edit files and add features.
- Commit changes locally:
```bash
git add .
git commit -m "meaningful message about what you did"
```

- Push your work to your remote branch so teammates can see it:
```bash
git push origin houcine
```

> 💡 Small, clear commits are better than one big commit.

---

## 🔄 **Update your branch with latest develop**
If teammates have merged into `develop`, keep your branch updated:
```bash
git checkout houcine
git pull origin develop
```

Resolve any conflicts.

---

## 🚀 **When your feature is ready**
Merge your branch into `develop`:
```bash
# Switch to develop
git checkout develop

# Update local develop from remote
git pull origin develop

# Merge your branch into develop
git merge houcine

# Push updated develop to remote
git push origin develop
```

Or create a merge request / pull request from `houcine` → `develop`.

---

## ✅ **Summary (copy/paste friendly):**
```bash
# Start work
git checkout houcine
git pull
git pull origin develop

# After work
git add .
git commit -m "description"
git push origin houcine
```

```bash
# When feature is ready
git checkout develop
git pull origin develop
git merge houcine
git push origin develop
```

---

## 📈 Visual Diagram
```
main
  |
  o---------o---------o   ← stable production releases
            \
             develop
              o----o----o----o   ← team integrates finished features
               \    \    \    \
                \    \    \    houcine
                 \    \    o--o--o   ← your work (feature branch)
                  \    aissa
                   \    o--o--o
                    \
                     jassim
                      o--o--o
```

> Each teammate has their own branch. When features are ready, merge into `develop`. When `develop` is stable, merge into `main`.

---

If you'd like, I can also export this as `GIT_WORKFLOW.md` for your repo! 🚀

