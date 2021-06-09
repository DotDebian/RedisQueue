# RedisQueue
Système de queue universel utilisant Java et la technologie Redis.

Usage
------
Ce projet est divisé en deux modules distincts:

#### Independant
Ce module constitue le coeur du système est du projet. Il s'agit d'un .JAR indépendant à lancer via `java -jar`. Pour le bon fonctionnement de celui-ci, une connexion à un serveur Redis doit être disponible.
Au premier lancement, la configuration suivante est créée et doit être éditée:
```Properties
redisHost:127.0.0.1 #Adresse IP du serveur Redis à utiliser
redisPort:6379 #Port du serveur Redis à utiliser
queueSpeed:2000 #Vitesse d'envoi de la queue en Millisecondes (MS)
```

Une fois l'application redémarrée, il est nécessaire d'y relier un serveur de jeu et un serveur limbo (lobby) pour la mettre en fonctionnement.

------
#### BungeeExtension
Ce module constitue une première extension exemple destinée au jeu vidéo Minecraft. Il s'agit d'un .JAR de type plugin à installer via Craftbukkit/Spigot. Pour le bon fonctionnement de celui-ci, une connexion à un serveur Redis doit être disponible.
Au premier lancement, la configuration suivante est créée et doit être éditée:
```Properties
{
  "isHub": false, # Le serveur de jeu est-il un serveur Limbo
  "redisHost": "127.0.0.1", # Adresse IP du serveur Redis à utiliser
  "redisPort": 6379, # Port du serveur Redis à utiliser
  "redisPassword": null, # Mot de passe du serveur Redis à utiliser
  "serverId": "hub" # Identifiant unique du serveur
}
```

Une fois l'application redémarrée, il est nécessaire d'y relier un second serveur de jeu pour le mettre en fonctionnement.

------
Une fois l'Independant et les deux serveurs de jeu lancés, vous pouvez effectuer /hub sur le serveur Limbo pour vous envoyer vers le serveur de jeu.

Documentation technique
------
Le fonctionnement technique de ce projet s'axe autour de l'utilisation de deux canals de messaging distincts: `server_data` pour la communication vers l'Independant depuis une extension, et `Extension` pour la communication depuis l'extension BungeeCord.

Différents messages sérializés à l'aide de la librairie GSON sont envoyés sur ces canaux:
| Type du message        | Identifiant           | Description  |
|:-------------:|:-------------:|:-----:|
| SERVER      | ADD_TO_QUEUE | Message envoyé au serveur pour demander l'ajout d'un joueur à une queue. |
| SERVER      | REMOVE_FROM_QUEUE | Message envoyé au serveur pour demander la suppression d'un joueur à une queue. |
| SERVER      | CLIENT_UPDATE | Message envoyé au serveur pour indiquer l'état de l'instance de jeu. |
| CLIENT      | PLAYER_OUTPUT | Message envoyé à l'instance de jeu pour communiquer avec le joueur (chat). |
| CLIENT      | CONSOLE_OUTPUT | Message envoyé à l'instance de jeu pour communiquer avec la console (chat). |
| CLIENT      | PLAYER_SEND | Message envoyé à l'instance de jeu indiquant qu'un joueur doit être envoyé sur une autre instance. |

Chacun de ses messages possède sa propre class, destinée ensuite à la serialization via Gson.

### Créer son extension

Pour créer sa propre extension, il est nécessaire d'écouter les messages entrant sur `Extension` via Redis, et de les déserializer  pour traiter correctement `PLAYER_OUTPUT`, `CONSOLE_OUTPUT` et `PLAYER_SEND`.

Selon les besoins, vous pouvez publier sur `server_data` les messages `ADD_TO_QUEUE`, `REMOVE_FROM_QUEUE` et `CLIENT_UPDATE`.
