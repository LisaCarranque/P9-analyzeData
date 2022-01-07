# P9-analyzeData

Ce module est un microservice Rest API dédié à la gestion des diagnostics de test.

Test de l’application :

Tests unitaires :  
Lancer les tests unitaires du module avec l’instruction gradle build.  
Le rapport de couverture de code par les tests est disponible au path :
build/jacocoHtml/index.html
ou directement au path :
test/java/analyzeData/reports/index.html


Tests des microservices :  
Ouvrir Postman et tester les endpoints suivants :

Méthodes Get :

172.23.0.10:8080/assess?id=1  
Résultat attendu : Probabilité None

172.23.0.10:8080/assess?id=2    
Résultat attendu : Probabilité Borderline

172.23.0.10:8080/assess?id=3  
Résultat attendu : Probabilité Early Onset

172.23.0.10:8080/assess?id=4  
Résultat attendu : Probabilité In danger