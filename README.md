# example-service

An example restful service based on spray and slick
 
##compile
`sbt compile`

##start the service
`sbt run`

if you run
`curl http://localhost:8084/Echo/Test`

you should get `Test` back in the response


##continuous development
This project has sbt-revolver plugin, which allows sbt to detect code changes and automatically rebuild and restart the server

`sbt` then `~ re-start` to start

##Testing
`sbt test`

##Show all dependencies
`sbt dependencyBrowseGraph`

## jar packaging

`sbt assembly`

## run in production

need to override both log4j config and production.conf. e.g.

`java -cp /etc/example-service/log4j2.properties \ 
    -Dproduction.conf=/etc/example-service/production.conf com.example.service.Boot -Xms512m -Xmx512m`