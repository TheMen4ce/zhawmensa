# zhawmensa

This is a prequalification project done by Dennis Briner for the ZHAW School of Engineering

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
- 

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

I first started with SpringBoot using JHipster because I wanted to get off the ground quickly. JHipster offers a lot of my needed features out of the box (auth and user management, CRUD features, ...). 

SpringBoot

Grails

Groovy
#### client

no SEO

angular

vue


#### authentication
Authentication is done via JWT. I chose this since it is an industry standard for web apis, stateless and thus supports any sort of client.

### TODOs before moving to production

#### deployment
I would suggest to serve client and server from one source. This can be easily achieved using the instructions provided [here](http://guides.grails.org/angular2-combined/guide/index.html). CORS can then be deactivated for production usage. Also enviornment specific settings can easily be added in `application.yml`

#### public API
The default `ApplicationController` was left to show how easy it would be to have an `ApiController` that doesn't require any for of authentication. 

### Demo
I have a [video](https://www.dropbox.com/s/blhi5tkyoo8twcl/ZHAW-MensaMaster_Preview.mp4?dl=0) available showing all features in case the setup takes too long

## local setup
tested on a clean Mac running MacOS 10.15

### Prerequisites

#### mandatory
- Java JDK 8
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

