# Suivi personnel et validation auteur : version de développement

## Base et périmètre

Branche `codex/quest-tracking-validation-1.21.1`, créée depuis `codex/questlog-3.5.0-1.21.1`, commit `a7f4cb3`. Version des fichiers de test : `3.5.0-tracking-dev.6`, pour Minecraft 1.21.1, Fabric et NeoForge. Ce travail constitue une version à tester en jeu, pas une publication stable.

Les définitions, objectifs, récompenses, écrans, composants défilants, métadonnées d’édition, messages réseau et fichiers de progression existants ont été réutilisés. Aucun port Forge ni dépendance Flower n’a été ajouté. La révision 5 ajoute les [traductions intégrées et leur édition](INLINE_LANGUAGES.md), pour les quêtes et les chapitres.

## Suivre une quête

Le descriptif contient un bouton « Suivre ». L’overlay affiche uniquement les titres et les états. Pendant le jeu, il reste passif. Dans le chat Minecraft :

- Cliquer sur le titre d’une quête ouvre son descriptif général.
- La molette fait défiler les lignes lorsque le pointeur survole la fenêtre.
- Glisser le titre de la fenêtre la déplace ; toute la largeur du cadre marron sur ses quatre côtés et ses coins sert au redimensionnement, avec priorité sur les clics de quête. Le titre est séparé des lignes par la texture utilisée sous les titres de récompenses. La hauteur minimale conserve une ligne complète sous ce séparateur.
- Glisser l’icône d’une quête avant ou après une autre modifie l’ordre personnel au sein de son groupe : la ligne suit verticalement la souris et les autres lignes se décalent pendant le clic maintenu. Un emplacement reste visible pour montrer où elle sera déposée. Le serveur reçoit l’ordre final au relâchement, sans message à chaque mouvement. Fermer le chat ou perdre le focus annule l’aperçu. La molette reste disponible pendant le glissement.

Les quêtes incomplètes apparaissent en premier, puis les quêtes terminées avec des récompenses restantes. Le classement conserve l’ordre choisi dans chaque groupe. Une quête désactivée ou non fonctionnelle reste enregistrée dans le suivi personnel. Elle est visible en gris en mode auteur (`/ql edit_mode true`) et masquée en mode joueur (`/ql edit_mode false`), sans être désépinglée. Changer de mode réaffiche les entrées conservées.

La position, la taille et l’opacité du cadre/fond sont des préférences locales dans la configuration Questlog. Le cadre reprend la texture du journal ; son opacité se règle de 0 à 100 % dans la catégorie « Suivi des quêtes ». Le texte conserve son opacité. Le bouton Suivre utilise les boutons Questlog et la palette du descriptif. La liste et l’ordre sont enregistrés par UUID avec la progression existante du joueur, dans les fichiers `<UUID>.questlog.dat` du monde serveur. Ils suivent le joueur sur un autre ordinateur lorsqu’il rejoint le même serveur avec le même compte. Les récompenses et la progression globales conservent leur comportement existant ; le suivi reste personnel.

Le suivi disparaît lorsque toutes les récompenses sont récupérées ; sans récompense, dès la complétion. Une quête répétable n’est pas réépinglée au cycle suivant. Une définition supprimée est retirée du suivi.

Pour cette première version, l’overlay s’affiche pendant le jeu et dans le chat. L’inventaire et les interfaces JEI/EMI demandent un travail et des essais distincts. Les fonctions de recettes JEI/EMI de la version 3.5.0 restent à vérifier par les essais de régression habituels.

## Créer, modifier et activer

Les champs obligatoires portent un `*` à la fin du libellé ; les champs facultatifs ne portent pas de marqueur. Un champ obligatoire vide apparaît en rouge. Une quête qui échoue aux contrôles apparaît « Non fonctionnelle » dans le journal auteur ; une quête valide simplement suspendue apparaît « Désactivée ». Les valeurs incorrectes et les références absentes sont détaillées lors d’une tentative d’activation.

Le titre et la description sont obligatoires et vides lors de la création. Les descriptions en composants JSON restent acceptées si elles produisent un texte non vide. Les définitions existantes sans description sont désormais signalées comme non fonctionnelles lors de la validation. Certains objectifs acceptent une cible vide : par exemple les objectifs d’obtention d’objet et de meurtre d’entité peuvent utiliser leurs valeurs générales existantes. Une récompense d’objet exige une cible, une récompense d’expérience exige sa quantité. Les quantités facultatives gardent leurs valeurs par défaut.

- Cliquer sur Modifier demande au serveur de désactiver la quête avant d’ouvrir l’éditeur. Annuler laisse la quête désactivée.
- « Enregistrer » sauvegarde une quête inactive, même incomplète. Le serveur valide silencieusement : le journal indique ensuite « Non fonctionnelle » ou « Désactivée ». Aucun écran de rapport n’interrompt cette sauvegarde.
- « Enregistrer et activer » demande explicitement l’activation. Une erreur de validation empêche l’activation mais conserve la sauvegarde inactive ; un simple avertissement ne bloque pas l’activation. Le bouton Retour du rapport revient au même éditeur, avec ses champs conservés, pour corriger les erreurs puis réessayer.
- Les boutons « Analyser les quêtes » ont été retirés du journal et de l’éditeur. La validation se fait automatiquement à la sauvegarde et le rapport reste accessible lors d’une activation incorrecte.

Le chapitre d’une nouvelle quête est prérempli avec celui actuellement sélectionné dans le journal, depuis le bouton de création comme depuis le menu contextuel. L’auteur peut ensuite changer ce choix. Une quête existante conserve son propre chapitre lors de l’édition.

L’ordre proposé est le plus grand numéro des définitions de ce chapitre plus un : `4` donne `5`, `40` donne `41`. Un chapitre vide commence à `0`. Les brouillons désactivés et l’ancien champ `order` sont pris en compte ; les quêtes d’autres chapitres simplement incluses dans la page principale ne le sont pas. Cette proposition s’applique aussi aux nouvelles quêtes issues d’un modèle, d’une duplication ou d’un collage. Le champ reste modifiable et l’édition d’une quête conserve son ordre. À la limite d’un entier (`2147483647`), la proposition reste plafonnée au lieu de devenir négative.

L’identifiant est automatique et non éditable : par exemple `questlog:main_ma-quete_1`. Le chapitre et le titre déterminent la partie lisible ; le serveur attribue un numéro libre à la première sauvegarde pour éviter l’écrasement d’une autre définition. L’identifiant reste stable après cette sauvegarde, même si le titre ou le chapitre est modifié.

Les opérations auteur exigent le niveau d’autorisation 2 côté serveur. Les anciens fichiers sans champ `active` restent actifs par défaut s’ils se chargent et passent les contrôles.

La sauvegarde remplace le JSON via un fichier temporaire et conserve le fichier précédent avec le suffixe `.json.old`. Elle recharge les définitions et synchronise les joueurs connectés. La progression suspendue est conservée même si le brouillon ne contient plus ses objectifs. À la réactivation, les entrées inchangées retrouvent leurs compteurs et leurs récompenses récupérées, y compris après un réordonnancement ; une entrée modifiée repart avec un état neuf. Pour les anciennes progressions sans signature de structure, la première restauration utilise encore l’ordre historique.

## Ce que contrôle le validateur

Champs obligatoires, formes de listes/objets, types enregistrés, valeurs numériques/booléennes et références disponibles : objets, blocs, entités, quêtes, chapitres, statistiques, effets, succès, enchantements, biomes, dimensions, structures et tables de butin. Les entrées composites sont inspectées sans analyser les cycles de dépendances entre quêtes.

Une référence dont les données ne sont pas accessibles produit un avertissement « non vérifiée », pas une déclaration de validité. C’est notamment le cas des tables de butin dans l’analyse locale de l’éditeur et de l’intégration Origins. L’analyse serveur a accès aux tables de butin. Des erreurs de construction plus spécifiques restent possibles et sont signalées par la quête de secours désactivée. Le validateur ne prouve pas toute la jouabilité ou la cohérence narrative du pack.

Un rapport trop volumineux est limité à des lignes complètes et affiche un avertissement invitant à corriger les problèmes puis relancer l’analyse. La sauvegarde et l’export ne sont pas bloqués par les avertissements ; aucun contrôle de publication n’a été ajouté.

## Vérifications automatisées et essais en jeu

La construction complète exécute les vérifications existantes (éditeur, images, détails, recettes et aperçu des récompenses) et les nouvelles vérifications `verifyTrackingValidation`. Elles couvrent validation, références absentes, liste/ordre, persistance NBT, récompenses partielles, désactivation, récupération des compteurs, géométrie et messages réseau. Elles ne lancent pas Minecraft et ne valident pas les interactions visuelles.

Construction du 2 octobre 2026 : première version avec 84 contrôles du suivi/validateur et les suites existantes réussis.

Révision du 3 octobre 2026 : construction complète réussie sur Fabric et NeoForge, 107 contrôles du suivi/validateur et 23 de l’éditeur réussis, ainsi que les suites existantes (19 images, 42 détails, 10 recettes et aperçu des récompenses). Nouvelles vérifications des champs requis, de l’opacité, des identifiants, du filtrage auteur/joueur, du réordonnancement et des gestes répartis sur plusieurs frames. Les suites ne confirment pas le rendu en jeu ni le suivi physique de la souris : ces points restent à retester dans Minecraft.

Les [exemples isolés](../examples/tracking-validation/README.md) séparent les quêtes fonctionnelles des définitions volontairement mal formées. Aucun profil ni monde existant n’est modifié par la préparation de ces exemples.

Révision `tracking-dev.3` : chapitre sélectionné repris à la création et dans l’identifiant automatique ; construction complète Fabric/NeoForge et suites existantes réussies. Le préremplissage depuis les différentes commandes de création reste à confirmer en jeu.

Retour du joueur du 3 octobre 2026 : les fonctions de la révision précédente ont été testées avec succès sur NeoForge 1.21.1. Ce retour ne confirme pas encore les modifications de `tracking-dev.4` : cadre de redimensionnement élargi, séparateur, ordre prérempli et aperçu du réordonnancement pendant le glissement.

Vérifications automatisées de `tracking-dev.4` : 143 contrôles du suivi/validateur et les suites existantes réussis. Les nouveaux cas couvrent les quatre côtés du cadre, la place minimale d’une ligne, l’ordre de création, les déplacements continus dans les deux sens, un pointeur immobile, les groupes d’état, le défilement, les entrées masquées et la persistance de l’ordre final. Le rendu reste à vérifier en jeu.

Utiliser le même fichier de développement côté serveur et client ; le protocole NeoForge est passé à `2.2`. Pour un retour à 3.5.0, restaurer la sauvegarde du monde et de `config/questlog` réalisée avant l’essai.

| Environnement | Essais requis |
| --- | --- |
| Fabric, solo, sans JEI/EMI | Journal et détails 3.5.0, suivi, chat, déplacement, redimensionnement, molette, icônes, retour du descriptif au chat. |
| NeoForge, solo, sans JEI/EMI | Même série, fermeture du chat pendant un glissement et vérification de la position au redémarrage. |
| Fabric, serveur dédié, deux joueurs | Ordres différents, déconnexion/reconnexion, redémarrage serveur, modification auteur visible immédiatement chez l’autre joueur, sauvegarde des compteurs. |
| NeoForge, serveur dédié, deux joueurs | Même série ; refus des opérations auteur pour le joueur sans autorisation. |
| Autre ordinateur, même joueur | Même serveur et compte : liste/ordre retrouvés, position/taille propres au client. |
| Deux plateformes, avec JEI puis EMI | Recettes et infobulles existantes ; absence de capture de clics dans l’inventaire par cet overlay limité au chat. |
| Résolution et échelle de GUI variées | Fenêtre dans l’écran, texte du chat utilisable en dehors de la fenêtre, bordures et défilement accessibles. |

Compléter aussi chaque scénario du tableau des exemples : deux récompenses manuelles, aucune récompense, automatique, choix, répétable, suspension puis réactivation, JSON incorrect. Les essais du 2 octobre ont confirmé la présence du bouton Suivre, le classement des états et la molette. Ils ont révélé l’interruption du glissement et les écarts de style, corrigés dans cette révision ; le résultat en jeu de ces corrections reste à renseigner par le testeur.

### Reprise rapide des essais

1. Utiliser le nouveau JAR `.2` du chargeur concerné. En multijoueur, utiliser cette même révision sur le serveur et les clients.
2. Ouvrir le chat, maintenir le clic sur le titre de la fenêtre plusieurs secondes, puis tester chaque bord/coin et l’icône d’une quête. Relâcher hors de la fenêtre et fermer le chat pendant un glissement. Vérifier que la saisie du chat reste utilisable hors du suivi.
3. Essayer les opacités 0, 40 et 100 % dans la configuration du mod : cadre et fond changent, le texte reste affiché.
4. Passer de `/ql edit_mode true` à `/ql edit_mode false`, puis revenir : les entrées suspendues disparaissent et reviennent ; les quêtes actives conservent leur ordre.
5. Sélectionner un chapitre autre que main, puis créer une quête depuis le bouton et le menu contextuel : chapitre sélectionné prérempli, ordre égal au maximum de ce chapitre plus un, titre et description vides, identifiant automatique fondé sur ce chapitre. Essayer notamment les maxima `4` et `40`, puis un chapitre vide. Cliquer sur Enregistrer et activer sans compléter ; revenir depuis le rapport, compléter les champs, puis activer. Vérifier qu’aucune seconde définition n’a été créée.
6. Créer un autre brouillon incomplet avec Enregistrer : aucun rapport ne s’ouvre. Le journal auteur doit le signaler comme Non fonctionnel.
7. Créer deux quêtes avec le même titre et chapitre : identifiants distincts. Modifier ensuite un titre existant : son identifiant reste stable.

## Sources et réutilisation

Les adaptateurs utilisent les API natives des versions du projet : Fabric Screen API/HudRenderCallback et NeoForge RenderGuiEvent/ScreenEvent. Les signatures ont été vérifiées dans les sources mises en cache des dépendances installées, car les réponses Context7 consultées ne correspondaient pas toujours à Minecraft 1.21.1.

- [Écrans NeoForge 1.21.1](https://docs.neoforged.net/docs/1.21.1/gui/screens/)
- [Sources Fabric API](https://github.com/FabricMC/fabric/tree/1.21.1)
- [Heracles et sa licence MIT](https://github.com/terrarium-earth/Heracles/blob/1.20.x/LICENSE), consultés pour l’organisation du suivi.

Le code ajouté ici utilise les fonctions de Questlog et les API natives ; aucun extrait d’un autre mod n’a été copié. FTB Quests a servi de référence de comportement uniquement. Flower n’est pas nécessaire pour cette fonctionnalité intégrée au cycle de Minecraft. L’agent local a réalisé une lecture bornée ; ses lectures tronquées ont été complétées directement.
