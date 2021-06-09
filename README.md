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
