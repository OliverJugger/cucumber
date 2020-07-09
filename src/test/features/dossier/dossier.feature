# language: fr
Fonctionnalité: Démonstration Cucumber 
    Scénario: Test sur les indemnités
    	Etant donné que je suis sur l'environnement "Qualification"
        Et que je suis connecté en tant que "xtaraud" avec le mot de passe "mnmn13"
        Et que mon rôle est "Publieur"
       	Lorsque je veux chercher une indemnité avec l'état "Cohérent"
       	Et que je veux selectionner la première indemnité que je vois
        Et que je veux "valider" l'indemnité "courante"
       	Et que je veux remplir les champs de validation
       	Alors l'indemnite est a l'état "Validé"
       	Et il n'y a plus d'indemnites validés
       	