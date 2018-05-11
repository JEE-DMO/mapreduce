# Hadoop MapReduce WordCount 
### Analyse du code 
Il y'a trois éléments importants à considerer dans un job MapReduce :
1. Le code du Mapper
2. Le code du Reducer 
3. Le code du Job (son lancement)

### WordCount 
Le wordcount consiste à compter combien de fois apparait un mot dans un texte.

#### Définition de la clé 
La définition de la clé est une étape importante dans la conception d'un job MapReduce, ici nous souhaitons calculer combien de fois un mot apparait,
le mot lui même semble donc une clé pertinante.

#### Mapper 
Le mapper prend en parametre une ligne du texte et la découpe en plusieurs mots et il retourne un couple clé valeur (le mot, 1)

Le découpage se fait sur l'espace, retour à la ligne, tabulation (espace étant le séparateur des mots)

En Java le Mapper prend en parametre un objet clé, le text à traiter, le context
1. La clé : la clé ici ne sera pas utilisée
2. Le texte : c'est la ligne à traiter (celle qui sera découpée)
3. Le context : sera utilisé pour y inserer les resultat du découpage (mapping)

  

 
