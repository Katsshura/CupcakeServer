<h1 align="center">
	Cupcake Server
</h1>

<h3 align="center">
	This project was developed mainly as a source project for a subject (PIT-II) of college. The purpose of this subject was to develop a backend server and frontend application to sell cupcakes and other variants. You can find the frontend application <a target="_blank" rel="noopener noreferrer"  href="https://github.com/Katsshura/CupcakeMobileApp">here</a>.

</h3>

---
<div id='home'/></div>

## Tables of contents  
 - [Project Architecture](#projectarchitecture)
 - [Auth & Security](#authsecurity)
 - [Endpoints](#endpoints)
 - [Tests](#tests)
 - [Deployment: AWS & Github Actions](#deployment)
 - [License](#license)
 - [Get in touch](#contact)
 
---

<div id='projectarchitecture'/></div>

## Project Architecture

The architecture of this project is very simple to understand. As the scope of it is small and there is no perspective of growing up and expand it's domain, I've decided to implement a simplified version of the **Onion Architecture**. The project was divided into two main layers: `API` and `CORE`.

#### API Layer

This layer is responsible for handling and exposing resources to external services through endpoints and also to control the security of these endpoints. It's not responsible for handling business logic or to integrate with external services. It depends on the `CORE` layer.

#### Core Layer

This layer is responsible for the business logic and integration with external services and/or other external resources, as database for example. It does not depend on any other layer.

#### Improvements

For bigger projects we could and should improve this architecture. There are few ways we could do this, if the project grows in terms of external integrations and have to do many conversions and aggregations of information from these services in order to return a single object response to the user, we could implement a new layer called `INFRASTRUCTURE`. This way we would break down the responsabilities between them and segregate it better. This new layer would be responsible only for implementing the integration with external resources/services. The `CORE` layer would have no dependency and would be simply responsible for data manipulation.

Also we could start to think in breaking this down into microservices, since we have a few mixed domains in this project that could be splitted up into new services: `Products`, `Orders`, `Payment` and `User`.

#### Architecture Diagram

![Architecture Diagram](https://i.imgur.com/1DMN8GC.png)

---

<div id='authsecurity'/></div> 

## Auth & Security

This project has implemented a few layers of security using spring features and AWS Secrets Manager and also custom encryption with SHA-256.

#### Password Encryption on Auth

In order to protect the user input for sensitive information that does not need to be known to perform operations, this project has implemented a custom annotation that encrypt nominated fields at controller level.

To achieve this I've created a custom class level annotation [@Encrypted](https://github.com/Katsshura/CupcakeServer/blob/master/core/src/main/java/com/katsshura/cupcake/core/validation/encrypted/Encrypted.java) that accepts an array of properties name that should be encrypted. It implements the `@Constraint` annotation from javax validation, so it's executed when the object annoted with it is validated by spring. It relies on [EncryptService](https://github.com/Katsshura/CupcakeServer/blob/master/core/src/main/java/com/katsshura/cupcake/core/services/encrypt/EncryptService.java) class that uses `SHA-256` encryption.

It has a great level of security beacuse as the encryption occurs at validation level of spring, the field is encrypted before the first instruction of the controller method that was invoked. This way even if we declare a log instruction into the first line to log the password, the result logged in would be the encrypted password and not the raw value. Also all the folowing flow would have the encrypted value, so the user credentials are fully protected.

![Imgur](https://i.imgur.com/WIFQBqx.png)
![Imgur](https://i.imgur.com/I1SCkCO.png)

#### Secrets/Credenitals Management

To protect credentials and secrets from being exposed the projects uses the spring capacity to access environment variables and map into properties that could be injected as value into class properties together with [AWS Secrets Manager](https://aws.amazon.com/secrets-manager/). 

This way the credentials are managed by AWS services and injected in the container as environment variables where the application will be executed, then when spring try to read these values it will have the secret ready to be used. With this we avoid defining sensitive information as database credentials direct into application.properties. To have more information regarding this flow, please refer to the [Deployment: AWS & Github Actions](#deployment) section.

---

<div id='endpoints'/></div> 

## Endpoints

#### User

- [User Registration](https://github.com/Katsshura/CupcakeServer/pull/1)
- [User Login](https://github.com/Katsshura/CupcakeServer/pull/2)

#### Product

- [Product Registration](https://github.com/Katsshura/CupcakeServer/pull/3)
- [List Products](https://github.com/Katsshura/CupcakeServer/pull/4)
- [Highlight Endpoints](https://github.com/Katsshura/CupcakeServer/pull/6)

#### Payment
- [Create Payment](https://github.com/Katsshura/CupcakeServer/pull/7)
- [GET Payment](https://github.com/Katsshura/CupcakeServer/pull/7)
- [Delete Payment](https://github.com/Katsshura/CupcakeServer/pull/7)

#### Order

- [Create Order](https://github.com/Katsshura/CupcakeServer/pull/8)
- [List Orders](https://github.com/Katsshura/CupcakeServer/pull/9)

#### Swagger

- [Localhost](http://localhost:8080/api/v1/swagger-ui/index.html#/)
- [Production](http://ec2-34-203-212-182.compute-1.amazonaws.com:8080/api/v1/swagger-ui/index.html#/)
---

<div id='tests'/></div> 

## Tests

This project has implemented unit testing with Junit 5 and database integration tests using testcontainers. To know more about testing application using Spring + JUnit 5 please read [the article](https://xr-emerson.medium.com/testing-software-with-junit-5-spring-and-pitest-12a1ceb27c7e) I've written regarding this subject and also refer to the [base project](https://github.com/Katsshura/JUnitTestingDemo) of the article.

---

<div id='deployment'/></div> 

## Deployment: AWS & Github Actions


This project uses Github Actions to deploy the application on AWS and also to validate pull requests before merging into `master` branch. 

#### Pull Request Actions

When a pull request is opened with target `master` branch it triggers the github actions defined in `gradle.yml` file. When this file is executed by Github it runs the command `./gradlew test --tests` that executes all the defined tests. If all test cases are successful it automaticaly approves the PR.

#### Deploy on AWS

Github detects when the `master` branch is updated, when this happens it executes the actions defined in `aws.yml` file. This files defines a sequence of steps necessary to succesfully deploy the application into a container and be ready to receive requests. The image bellow explain beter how this flow works. 

Also, during the deploy to AWS this files also defines instructions to execute `flyway migrations`. These migrations are responsible to keep the database up to date with the defined sql scripts.

![Imgur](https://i.imgur.com/A8egGta.png)

---

<div id='license'/></div> 


## License

This project is under the license [GNU General Public License v3.0](./LICENSE).

---

<div id='contact'/></div> 

## Get in touch
[![Linkedin Badge](https://img.shields.io/badge/-LinkedIn-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/katsshura/)](https://www.linkedin.com/in/katsshura/)
[![Gmail Badge](https://img.shields.io/badge/-Gmail-c14438?style=flat-square&logo=Gmail&logoColor=white&link=mailto:xr.emerson@gmail.com)](mailto:xr.emerson@gmail.com)
