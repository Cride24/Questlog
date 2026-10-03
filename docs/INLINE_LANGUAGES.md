# Traductions dans les définitions Questlog

Révision `3.5.0-tracking-dev.6`, Minecraft 1.21.1, Fabric et NeoForge. Les traductions d’une quête sont réunies dans son JSON ; celles d’un chapitre sont réunies dans le JSON du chapitre. Aucun fichier de textes externe n’est nécessaire pour cette méthode.

## Édition

Le bouton Langue est placé entre l’ID et le titre, dans les deux éditeurs. Il ouvre une liste avec recherche, défilement et les langues proposées par Minecraft et ses ressources. Choisir une langue ne change pas la langue du jeu : cela sélectionne les textes que l’auteur rédige.

À la création, la langue actuellement configurée dans Minecraft est proposée. Une définition contenant des textes dans une seule langue s’ouvre dans cette langue. Si plusieurs langues sont renseignées, l’éditeur choisit la langue du joueur, puis `en_us` si elle manque, puis la première langue renseignée dans l’ordre du JSON. Les champs vides ne comptent pas ; les noms personnalisés des objectifs et récompenses comptent aussi. Les anciennes définitions uniquement sans suffixe s’ouvrent sur « Aucune (champs sans langue) ». Le bouton indique le nom fourni par Minecraft, par exemple « Français (France) » ; un code importé inconnu du jeu reste affiché tel quel. Changer de langue conserve les saisies en mémoire, y compris les paramètres avancés et le nom de l’entrée actuellement éditée. Une langue non renseignée présente des champs vides ; les traductions de repli ne sont pas recopiées dans ces champs. Enregistrer sauvegarde toutes les langues, même celles qui ne sont plus sélectionnées.

Les langues concernent le titre, la description, les détails, les descriptions de réussite/échec, les boutons personnalisés et les noms des objectifs/récompenses, y compris dans les entrées composites. Le nom d’un chapitre utilise `name-fr_fr`, `name-en_us`, etc. L’ID, le chapitre, l’ordre, les icônes, les quantités et les règles restent communs.

## Affichage et compatibilité

Pour chaque champ, l’affichage choisit dans cet ordre :

1. La version renseignée dans la langue du joueur.
2. La version renseignée en `en_us`.
3. La première version non vide de ce champ dans l’ordre des clés du JSON, y compris sa version sans suffixe.

Les valeurs absentes, vides ou composées uniquement d’espaces sont ignorées. Un changement de langue du joueur actualise les textes affichés sans remplacer ses quêtes ni leurs compteurs. Les titres localisés sont utilisés par la recherche, le suivi, les notifications et les références aux autres quêtes.

« Aucune » lit et écrit `title`, `description`, `details`, `name`, etc. Ces champs peuvent coexister avec leurs traductions. Les anciens fichiers et le mode historique `translatable` avec ses clés de fichiers de langue restent acceptés. Les valeurs avec suffixe sont des textes intégrés, même si le mode historique est activé pour les champs sans suffixe.

Le titre et la description restent obligatoires, mais une version renseignée suffit pour chaque champ. Une traduction partielle peut être sauvegardée et activée si la définition est par ailleurs valide. Modifier uniquement les traductions ne remet pas à zéro les objectifs ni les récompenses récupérées. Les textes structurés des descriptions et détails, les liens, infobulles et recettes utilisent les traitements existants.

## Exemple et essais en jeu

Le dossier [examples/inline-languages](../examples/inline-languages/questlog) contient une quête et un chapitre bilingues. Chaque définition contient ses propres traductions. Ces fichiers n’ont pas été installés dans un profil ni dans un monde.

- Créer une quête et un chapitre : la langue sélectionnée doit correspondre à celle de Minecraft et le bouton doit afficher son nom et sa région.
- Rouvrir une définition monolingue puis une définition multilingue en changeant la langue du jeu : vérifier les priorités langue unique, langue du joueur, anglais US, ordre du JSON.
- Dans l’éditeur de quête, rédiger titre/description en français, passer en anglais US, remplir puis revenir au français avant de sauvegarder : les premières saisies doivent rester présentes.
- Faire la même opération pour un nom d’objectif, une récompense, les textes avancés et un chapitre ; sauvegarder puis rouvrir.
- Vérifier en français puis en anglais le journal, le descriptif, les détails, les boutons, le suivi et la recherche.
- Choisir une langue non renseignée : l’anglais US doit apparaître. Retirer ensuite un texte anglais : sa première version renseignée doit apparaître. Les détails de l’exemple sont volontairement uniquement français.
- Éditer une ancienne quête en sélectionnant Aucune, conserver ses textes et ajouter une traduction : les deux modes doivent coexister.
- Avancer dans une quête, modifier seulement une traduction, réactiver : les compteurs et récompenses déjà récupérées doivent être conservés.

Utiliser cette révision côté serveur et client. Le protocole NeoForge est `2.2`. Les tests automatisés contrôlent les définitions, les sélections de texte, les états d’éditeur et la progression ; ils ne prouvent pas le rendu ni les gestes de sélection dans Minecraft.

Construction du 3 octobre 2026 réussie sur Fabric et NeoForge : 49 contrôles multilingues, 36 contrôles d’éditeur, 143 contrôles du suivi/validateur et les suites existantes (19 images, 42 détails, 10 recettes, aperçu des récompenses) réussis. Les essais en jeu restent à effectuer pour cette révision.
