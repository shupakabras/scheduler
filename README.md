To build project use: 
`mvn clean install -Pdocker`

Run project using docker-compose
`docker-compose up `

Generate multiple schedules run GET request
`http://localhost:8081/register/generate/50/15`
Which will generate 50 schedules to be executed in 15 sec after current moment

Verify jobs balanced between different instances
