# zhawmensa

This is a prequalification project done by Dennis Briner for the ZHAW School of Engineering

## local setup
tested on a clean Mac running MacOS 10.15

### prerequisites

#### Mandatory
- Java JDK 8
- Node 13.5.0
- Yarn 1.21.0

#### Optional
- PostgreSQL server if you want to run the app with PostgreSQL

Have a DB server running and configure it in applications.yml under `dataSource`

### run server
this builds and runs the project automatically with the provided gradle wrapper
```
./gradlew bootRun
```
App should be running on http://localhost:8080

### run client
```
cd vue-client
yarn
yarn serve
```

### develop
Best develop experience is given using IntelliJ IDEA. The project is contains alread preconfigured run configurations in the `.idea` folder. This includes features such as:
- debugging of client and server code in IntelliJ
- hot reload of client and server code
- db tooling
- ... and much more

