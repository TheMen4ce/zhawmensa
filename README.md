# zhawmensa

This is a prequalification project done by Dennis Briner for the ZHAW School of Engineering

With this application, you can import menu plans from different facilites of the providers "SV Group" and "ZFV-Unternehmungen". The application offers the ability to view and edit the menus and provides a public API to query the data.

## about

I have tried to simplify the data model and improve the user flow as much as possible. Thus the new solution will not exactly reseble the existing one.

### User Flow
- login (default user credentials are admin/admin, these can and should be changed after first login)
- click on the import button (this button just exist to have a "semi-automatic" import flow as it was listed in the requirements. Importing could be done every day/week with a cron job)
  - the imported menus are directly stored in the database
- click on a menu plan
  - menus can be edited anytime without the need to reimport the whole menu plan

### Functional Features
- login and user management
- CRUD functionality for gastronomic facilities, menu plans and menus
- multilanguage on client and server (some translations might be missing)
- very simple API by calling /api?year=2019&week=51

### Non-Functional Features
- the app is fast!
- usage with only keyboard is possible
- multi-user-aware (checks if an object was manipulated in the meantime)
- mobile friendly (except the menu plan table could be imporved for mobile)

### technical suggar
- the amount of code written is minimal and can thus be easily read and understood
- the application was build with maintainablity, extensiblity (i.e. user and role management) and scalability in mind

### Tech Specs

#### server
Stack: `Grails Groovy GORM Hibernate PostgreSQL`

My intention was to be as close to the preferred stack of ZHAW as much as possible. Thus, I first started with SpringBoot using JHipster because I wanted to get off the ground quickly. JHipster offers a lot of my needed features out of the box (auth and user management, CRUD features, ...). I then found that the amout of code I would have to rewrite/restructure would still be bigger than starting from scratch. I feared, that I would spend to much time for simple things using just SpringBoot and then settled with Grails. Grails solves a lot of common web application problems in a smooth way and chooses convention over configuration to enhance development speed. It is also native to the Groovy language. The Groovy language is dynamic and doesn't require static typing. This allowed me to very efficiently parse the XML responses from the different services.

Pros:
+ slow build/compile time
+ Groovy for XML parsing
+ little amout of code to write (and thus less error prone)

Cons:
- Outdated or missing documentation
- Small community

#### client
Stack: `Vue.js Buefy`

Since the client of the application would not have to meet SEO criteria I decided to choose a single page application (SPA) javascript framework. I first started with Angular since the matching Grails profile suggests that it is well supported. After some failing attempts to upgrade to the latest version and struggeling with the slow compile time and development speed I have changed my mind and went for Vue.js. Eventhough I didn't have any experience with this framework I was able to progress quickly so I stuck with it.

Pros:
+ fast build/compile time
+ fast development speed
+ good learning curve
+ easy layout and design features with Buefy & Bulma
+ big community and easy to find solutions for problems

Cons:
- I don't see any

#### authentication
Authentication is done via JWT. I chose this since it is an industry standard for web apis, stateless and thus supports any sort of client.

### TODOs to finalize the application

#### deployment
I would suggest to serve client and server from one source. This can be easily achieved using the instructions provided [here](http://guides.grails.org/angular2-combined/guide/index.html). CORS can then be deactivated for production usage. Also enviornment specific settings can easily be added in `application.yml`

#### public API
The current public API (see `ApiController`) exists just to show how easy it would be to. 

#### frontend tests
The project does not containt any front end unit or end-to-end tests. I didn't prioritize them high since the frontend doesn't contain any business critical logic.

#### backend tests
Currently only the most critcal import logic is being tested by the backend.

### Demo
I have a [video](https://www.dropbox.com/s/blhi5tkyoo8twcl/ZHAW-MensaMaster_Preview.mp4?dl=0) available showing all features in case the setup takes too long

## local setup
tested on a clean Mac running MacOS 10.15

### Prerequisites

#### mandatory
- Java JDK 8 (important!)
- Node 13.5.0
- Yarn 1.21.0

#### optional
- PostgreSQL server if you want to run the app with PostgreSQL

Have a DB server running and configure it in applications.yml under `dataSource`

### Run Server
this builds and runs the project automatically with the provided gradle wrapper
```
./gradlew bootRun
```
server will run on http://localhost:8080

### Run Client
```
cd vue-client
yarn
yarn serve
```
client will run on http://localhost:4200

### Tests
this command will run all tests. 
```
./gradlew check
```
use `test` for unit tests or `integrationTest` for integration tests

### Develop
Best develop experience is given using IntelliJ IDEA. The project is contains alread preconfigured run configurations in the `.idea` folder. This includes features such as:
- debugging of client and server code in IntelliJ
- hot reload of client and server code
- db tooling
- ... and much more

