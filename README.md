# DAI Lab - HTTP infrastructure

## Group
* Glodi Domingos
* Dominik Saul



## Step 1: Static Web site
The first step of the lab was to configure a NGINX Docker image for container which serves a static site.

All configurations, content and the Dockerfile for this Docker image are located in the directory 'static-web-server' of this repository.

As content for our static site we've chosen the Bootstrap template 'Agency', which can be downloaded from this link: https://startbootstrap.com/theme/agency
In our Dockerfile we've configured to use the official NGINX image. In addition we copy the content and the nginx.conf into the image. 

The specific NGINX configuration parameters we documented directly in the nginx.conf file.


## Step 2: Docker compose
With the second step of this lab we've created our docker-compose.yml file.

The services in the docker compose file can be started by using the following command:
`docker compose up`

Use the following command to start the services in the docker compose file and additionally rebuild the images before:
`docker compose up --build`

Command to stop the docker services in the docker compose file:
`docker compose down`

The configuration of our docker-compose.yml file we directly documented in this file.


## Step 3: HTTP API server
The third step of this lab was to create an API Server with the help of the Framework Javalin.
We've decided to develop an API Server to handle and manage a TODO list.

All configurations, code and the Dockerfile for the API server are located in the directory 'api-server' of this repository.


Our api server supports the following operations:
Read:
GET /api/todo/all > get all todos
GET /api/todo/{id} > get a specific todo

Create:
POST /api/todo/ > create a todo
(the text of the todo needs to be transmitted in the body of the request: the key name is 'text')

Update:
PUT /api/todo/{id} > update the text of a specific todo
(the text of the todo needs to be transmitted in the body of the request: the key name is 'text')
PUT /api/todo/{id}/setDone > mark a specific todo as done
PUT /api/todo/{id}/setUnone > mark a specific todo as undone

Delete:
DELETE /api/todo/{id} > delete a specific todo

To be able to run our Javalin API server in a Docker container we need to create a JAR file from our. To do so we use maven.
Command to compile server api (needs to be executed from thew api-server folder)
`mvn clean compile assembly:single`

To run this JAR file in a docker container we've created a new Docker image with the Dockerfile 'api-server/Dockerfile'.

Command to docker compose up with rebuilding all images:
`docker compose up --build`


## Step 4:

Access the dashboard of Traefik:
http://localhost:8080/dashboard/