## template
Sample template spring boot reactive api project which fetches github job data from api and stores on mongdb 

## Tech/framework used
Spring Boot , mongoDB , FreeMarker

<b>Built with</b>
- [Maven](https://maven.apache.org/)

## Installation

Maven have to be installed
MongoDB install on local (or dont if docker installed)


"maven install" for build
java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=devel -jar target/template-0.0.1-SNAPSHOT.jar 

or 

docker-compose build

docker-compose up

## Testing
For both unit and integration test

mvn test -Dmaven.test.skip=false


© [Volkan Cetin]()
