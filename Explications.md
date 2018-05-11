# Hadoop MapReduce WordCount 
### Analyse du code 
Il y'a trois éléments importants à considérer dans un job MapReduce :
1. Le code du Mapper
2. Le code du Reducer 
3. Le code du Job (son lancement)

### WordCount 
Le wordcount consiste à compter combien de fois apparait un mot dans un texte.

#### Définition de la clé 
La définition de la clé est une étape importante dans la conception d'un job MapReduce, ici nous souhaitons calculer combien de fois un mot apparait,
le mot lui-même semble donc une clé pertinente.

#### Mapper 

`public void map(Object key, Text value, Context context) {}`

Le mapper prend en paramètres une ligne du texte et la découpe en plusieurs mots et il retourne un couple clé valeur (le mot, 1)

Le découpage se fait sur l'espace, retour à la ligne, tabulations.

En Java le Mapper prend en paramètres un **objet clé**, le **texte** à traiter, le **contexte**

1. La **clé** : la clé ici ne sera pas utilisée
2. Le **texte** : c'est la ligne à traiter (celle qui sera découpée)
3. Le **contexte** : sera utilisé pour y insérer les résultats du découpage (mapping)

#### Reducer   
  
`public void reduce(Text key, Iterable<IntWritable> values,Context context){}`


Le reducer reçoit une **clé** et l’ensemble des **valeurs** de cette **clé** et fait-en sorte de calculer la somme des valeurs.

En Java le Reducer prend en paramètres un **objet clé**, la liste des **valeurs** pour la **clé** et le **contexte**

 

