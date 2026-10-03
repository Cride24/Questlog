# Quêtes de test du suivi et de la validation

Ces exemples ne sont pas installés automatiquement. Utiliser une instance et un monde de test, avec le même fichier Questlog de développement sur le serveur et les clients.

- `functional/questlog` contient huit quêtes valides et un chapitre.
- `malformed/questlog` contient un autre chapitre et quatre quêtes volontairement incorrectes. **Le fichier `04_syntax.json` est volontairement un JSON invalide.**

Copier séparément les dossiers `chapters` et `quests` de la suite choisie dans `config/questlog` de l’instance de test. Les fichiers utilisent des sous-dossiers dédiés ; ne remplacer aucun fichier personnel. Après sauvegarde du monde et de la configuration, lancer l’instance ou utiliser `/questlog reload`.

En solo, activer les commandes ; sur serveur, utiliser un compte auteur disposant du niveau d’autorisation 2. Activer le mode auteur avec `/questlog edit_mode true`, puis ouvrir le journal avec son raccourci habituel ou `/questlog open`.

## Résultats attendus

| Fichier | Essai |
| --- | --- |
| `01_rewards` | Suivre, lire, observer « Récompense à récupérer », réclamer une seule récompense puis la seconde. Le suivi persiste entre les deux. |
| `02_no_reward` | Suivre puis lire : retrait immédiat du suivi. |
| `03_repeatable` | Suivre, lire, récupérer la récompense puis lancer un nouveau cycle : aucun réépinglage automatique. |
| `04_progress` | Placer trois blocs, suivre, cliquer sur Modifier ; la quête devient grise et désactivée pour tous. Placer d’autres blocs : pas de progression. Réactiver sans modifier l’objectif : reprise à trois sur huit. |
| `05_choice` | Choisir une récompense, puis réclamer : le suivi disparaît après récupération. |
| `06_auto` | Suivre puis lire : récompense automatique, retrait du suivi. |
| `07_disabled` | Inactive au chargement. Activer depuis l’éditeur, suivre, puis cliquer sur Modifier pour vérifier son maintien en gris. |
| `08_scroll` | Avec les autres quêtes non terminées, réduire la fenêtre et essayer la molette ainsi que le réordonnancement par l’icône. |
| `01_required` incorrect | Lors de la tentative d’activation, le rapport indique `objectives[0].block` et `rewards[0].experience`. Retour revient à l’éditeur. La sauvegarde simple est autorisée et ne montre pas le rapport. |
| `02_references` incorrect | Quête et objet absents signalés ; aucune exception ne doit interrompre l’analyse. |
| `03_values` incorrect | Valeur de tri, quantité et type inconnus signalés ; le journal auteur reste utilisable. |
| `04_syntax` incorrect | Quête de secours désactivée, erreur de lecture signalée. Corriger via l’éditeur puis sauvegarder remplace le fichier invalide. |

Voir le [guide complet](../../docs/QUEST_TRACKING_VALIDATION.md) pour la matrice Fabric/NeoForge et les essais avec deux joueurs.
