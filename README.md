# zhawmensa

This is a prequalification project done by Dennis Briner for the ZHAW School of Engineering

This application allows an admin user to import menu plans from various facilities of the providers "SV Group" and "ZFV-Unternehmungen". The admin user interface (UI) offers the ability to view and edit the imported menus. The application provides a simple public application programming interface (API) to query the data.

## about

I have tried to simplify the data model and improve the user flow as much as possible. Thus the new solution will not exactly reseble the existing one. "Keeping it simple" and not be distracted to much from the existing, more complex solution, was one of the challenges I faced in this project. 

### User Flow
- login *(default user credentials are admin/admin, these can and should be changed after first login)*
- importing this weeks menus by clicking the import-button *(this button just exist to have a "semi-automatic" import flow as it was listed in the requirements. Importing could be done every day/week with a cron job)*
  - the imported menus are directly stored into the database
- select a facilitys menu plan
  - menus can be edited anytime without the need to reimport the menu plan again

### Functional Features
- login and user management
- CRUD functionality for gastronomic facilities, menu plans and menus
- multi language on client and server *(some translations might be missing)*
- simple version of a public API by calling `hostname:port/api?year=2019&week=51`

### Non-Functional Features
- quick loading/response time
- multi-user-aware *(checks if an object was manipulated in the meantime)*
- mobile friendly *(except the menu plan table could be improved for mobile)*
- usage with only keyboard is possible

### Technical Sugar
- the amount of code written is minimal and can thus be easily read and understood
- the application was build with maintainablity, extensiblity *(i.e. user and role management)* and scalability in mind

### Tech Specs

#### server
Stack: `Grails Groovy GORM Hibernate PostgreSQL`

The intention was to be as close to the preferred stack of ZHAW as much as possible. Thus, I first started with SpringBoot using JHipster because I wanted to see the first results quickly. JHipster would offer a lot of the needed features such as auth and user management, CRUD features, ...) out of the box. After working with it for some time I found that the amout of code I would have to rewrite/restructure would be bigger than starting with a new project without scaffolding. I feared, that doing that just with SpringBoot would need to much time for simple things so I chose the Grails framework. Grails solves a lot of common web application problems in a smooth way and chooses convention over configuration to enhance development speed. It is build on top of SpringBoot and thus uses the same Stack. Grails is also native to the dynamic and not statically typed Groovy language. This allowed me to very efficiently parse the XML responses from the different services.

Pros:
+ slow build/compile time
+ Groovy for XML parsing
+ little amount of code to write (and thus less error prone)

Cons:
- Outdated or missing documentation
- Small community

#### client
Stack: `Vue.js Buefy`

Since the client of the application would not have to meet SEO criteria I decided to choose a single page application (SPA) javascript framework. I evaluated the top 3 of the most poupular SPA frameworks (Angular, React & Vue.js). I first started with Angular since the matching Grails profile suggests that it is well-supported. After some failing attempts to upgrade to the latest version and struggling with the slow compile time and development speed I have changed my mind and went for Vue.js. Even though I did not have any experience with this framework I was able to progress quickly, so I stuck with it.

Pros:
+ fast build/compile time
+ fast development speed
+ good learning curve
+ easy layout and design features with Buefy & Bulma
+ big community and easy to find solutions for problems

Cons:
- I have not see any

#### authentication
Authentication is done via JSON Web Token (JWT). I chose JWT since it is an industry standard for web APIs and to keep the server stateless. Most of the work is done by the SpringSecurity and the SpringSecurityRest plugin of Grails. Custom code is in the `security` package and configuration is done in `conf/application.groovy`

#### concurrency control
Grails has Hibernates optimistic locking behavior configured by default. It automatically adds a version property to each domain class where it is not excluded manually. The application checks the version property before updating a domain object. Thus, it is not possible to override another users changes.

### Architecture
**server**
```
grails-app
|
└── conf  // project configuration 
|
└── controllers  // all exposed apis and url endpoints
|
└── domain // all domain object classes
|
└── i18n // user facing text and translations
|
└── init // logic that happens on application startup
|
└── services // service layer
    |
    └── domain // for domain access and CRUD logic
    |
    └── exceptions // custom exceptions 
    |
    └── menuimport // import logic
    |
    └── security // user/auth logic
|
└── views // json models sent to the client
|
└── src
    |
    └── integration-test // integration-tests
    |
    └── test // unit-tests
    |
    └── main // other application code
```

**client**
```
src
|
└── assets  // images and fonts
|
└── components  // UI components
|
└── i18n  // user facing text and translations
|
└── models  // models for clarifying structure (just an example usage)
|
└── services  // backend calls
|
└── store  // state logic
```
### TODOs to finalize the application

#### deployment
I would suggest serving client and server from one source. This can be easily achieved using the instructions provided [here](http://guides.grails.org/angular2-combined/guide/index.html). Currently all cross site origin requests (CORS) to the server are allowed, this can then be deactivated. Environment specific settings can easily be added in `application.yml`

#### public API
The current public API (see `ApiController`) was not part of the task and exists just to show how easy it would be to have such an API. 

#### frontend tests
The project does not contain any front end unit or end-to-end tests. I did not prioritize them high since the frontend does not contain any business critical logic.

#### backend tests
Currently only the most critical import logic is being tested by the backend. This would need to be improved.

### Demo
Since I did not deploy the application anywhere I have created a [video](https://www.dropbox.com/s/blhi5tkyoo8twcl/ZHAW-MensaMaster_Preview.mp4?dl=0) showing all features.

This [video](https://www.dropbox.com/s/j2pxe6v3cqho8f3/ZHAW-MensaMaster_API_Preview.mp4?dl=0) shows the usage of the public API.

## local setup
I developed with a Windows machine but tested the setup on a different machine running MacOS 10.15

### Prerequisites

#### mandatory
- Java JDK 8 (important!)
- Node 13.5.0
- Yarn 1.21.0

#### optional
- PostgreSQL server (otherwhise the application will use a filebased H2 database)

Have a DB server running and configure it in `applications.yml` under `dataSource`

### Run Server
this builds and runs the project automatically with the included gradle wrapper
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
Best develop experience is given using IntelliJ IDEA. The project contains already preconfigured run configurations to run and debug in the `.idea` folder.
