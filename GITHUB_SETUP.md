# GitHub repository setup (follow these steps)

## 1. Create the repository on GitHub
1. Sign in at https://github.com/ and click **+** (top right) > **New repository**.
2. Repository name: `SmartPantryManager`.
3. Description: `Java Android pantry inventory and strict recipe matching app`.
4. Choose **Public** because the assignment asks for a public repository (or follow the lecturer's private-repository collaborator instructions).
5. For the easiest first push, leave **Add a README**, `.gitignore`, and license unchecked because this project folder already has a README.
6. Click **Create repository** and copy the repository HTTPS URL, e.g. `https://github.com/YOUR-USERNAME/SmartPantryManager.git`.

## 2. Link the Android Studio project to GitHub
In Android Studio, open **View > Tool Windows > Terminal** (menu wording may vary). Make sure the terminal is in the `SmartPantryManager` project root—the folder containing `settings.gradle`—then run:

```bash
git init
git add .
git commit -m "Create Android project structure and README"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/SmartPantryManager.git
git push -u origin main
```

Replace the example URL with your own repository URL. If Git asks you to authenticate, use the browser sign-in flow or a GitHub personal access token; do not put passwords or tokens in source code.

If `git remote add origin` says `origin` already exists, run `git remote -v` to inspect it, then use `git remote set-url origin YOUR-REPOSITORY-URL` if it points to the wrong repository.

## 3. Confirm the connection
Refresh your GitHub repository page. You should see your source folders and README. After each meaningful change, use:

```bash
git status
git add .
git commit -m "Describe the actual change"
git push
```

You can also use Android Studio's **Git** or **Commit** tool windows to review changes and commit, but push the commits to GitHub as well.

## 4. Build a genuine development history
The assignment asks for at least 10 meaningful commits made throughout development. Use commits that reflect work you actually complete, for example:
1. `Create Android project structure and README`
2. `Add pantry database schema and recipe seed data`
3. `Implement pantry ingredient model`
4. `Build pantry list XML layout`
5. `Implement RecyclerView pantry adapter`
6. `Add ingredient creation and validation`
7. `Implement editing and deleting pantry items`
8. `Add recipe strict-matching logic`
9. `Build suggested recipes screen`
10. `Add recipe detail and settings screens`
11. `Test persistence and fix validation issues`
12. `Document setup and add demonstration screenshots`

Only use a message when the corresponding change has really been made. Do not manufacture dates or pretend commits were made earlier than they were. Make commits incrementally while you implement, test, and improve the app.
