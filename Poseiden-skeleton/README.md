# spring-boot
## Technical:

1. Framework: Spring Boot v2.0.4 --> updated to 3.0.0
2. Java 8 --> updated to Java 17
3. Thymeleaf
4. Bootstrap v.4.3.1


### Installation

##### Database
* MySql,
* Générée en code first
* Par défaut sur le port 3306, (modifier le port dans les fichiers application.yml et application-test.yml)
* Variables d'environement à setup :
   * DBUSER représente datasource.username
   * DBPASS représente datasource.password
* Les scripts sql de creation des tables ne sont pas nécessaire pour lancer le projet (générées par le code) mais se trouvent sous : doc/data.sql

##### SpringBoot backend
* à partir de la racine du projet :
* mvn install -DDBUSER=<datasource.username> -DDBPASS=<datasource.password>
* par défaut le serveur spring se lance sur le port 8090 (modifier dans application.yml)
* cd target
* java -jar paymybuddy-0.0.1-SNAPSHOT.jar
* A des fins de démonstration la database est réinitialisée à chaque lancement de l'application.

Quatres utilisateurs sont générés avec les credentials suivants :
* testusername/testpassword
* testadmin/testpassword
* les deux autres sont générés en utilisant les données de doc/data.sql (je ne connais pas le mot de passe - il est fourni encrypté)

## Setup with Intellij IDE -- Old
1. Create project from Initializr: File > New > project > Spring Initializr
2. Add lib repository into pom.xml
3. Add folders
    - Source root: src/main/java
    - View: src/main/resources
    - Static: src/main/resource/static
4. Create database with name "demo" as configuration in application.properties
5. Run sql script to create table doc/data.sql

## Implement a Feature
1. Create mapping domain class and place in package com.nnk.springboot.domain
2. Create repository class and place in package com.nnk.springboot.repositories
3. Create controller class and place in package com.nnk.springboot.controllers
4. Create view files and place in src/main/resource/templates

## Write Unit Test
1. Create unit test and place in package com.nnk.springboot in folder test > java

## Security
1. Create user service to load user from  database and place in package com.nnk.springboot.services
2. Add configuration class and place in package com.nnk.springboot.config
