# Gas & Water usage monitoring app

### Description
Application intended to persist/retrieve gas/water usage data of subscriber.

### Technologies

The following technologies are used:

Spring Boot(web,data), Hsqldb, modelMapper,  Javax validation, Swagger 2

### Installation
You should have Java 8 and maven installed on your machine to run this app.

Steps:

Install:

`mvn clean install`

Run app:

`mvn spring-boot:run`

App endpoints:

Add subscriber:

`http://localhost:8080/api/subscriber/create
 {
     "clientId": 1,
     "firstName": "Abo",
     "lastName": "paaaa"
 }`

Submit gas usage data:

`http://localhost:8080/api/update/gas/1
 {"usedGasContent":76, "submittedTime":"2020-06-25T15:56:03"}`
 

Get gas data(of subscriber):

 `http://localhost:8080/api/get/recent/gas?id=1&limit=2
  http://localhost:8080/api/get/recent/gas?id=1`
  
Submit water data:

`http://localhost:8080/api/update/water/1
 {"waterAmount":12, "submittedTime":"2020-08-25T15:56:03", "waterType":"HOT"}`
 
 
Get water data(of subscriber):

`http://localhost:8080/api/get/recent/water?id=1&type=COLD
 http://localhost:8080/api/get/water?id=1&type=COLD
 http://localhost:8080/api/get/water?id=1&type=COLD&limit=1`

Go to Swagger page to see all endpoints:

`http://localhost:8080/swagger-ui.html`
