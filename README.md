# DAI Lab - HTTP infrastructure

## Group

* Glodi Domingos
* Dominik Saul

## Step 1: Static Web site

The first step of the lab was to configure a NGINX Docker image for container which serves a static site.

All configurations, content and the Dockerfile for this Docker image are located in the directory 'static-web-server' of
this repository.

As content for our static site we've chosen the Bootstrap template 'Agency', which can be downloaded from this
link: https://startbootstrap.com/theme/agency
In our Dockerfile we've configured to use the official NGINX image. In addition we copy the content and the nginx.conf
into the image.

The specific NGINX configuration parameters we documented directly in the nginx.conf file.

## Step 2: Docker compose

With the second step of this lab we've created our docker-compose.yml file.

The services in the docker compose file can be started by using the following command:
`docker compose up`

The following command is used to rebuild the images first and then start the services in the docker compose file: 
`docker compose up --build`

Command to stop the services in the docker compose file:
`docker compose down`

The configuration of our docker-compose.yml file we directly documented in this file.

## Step 3: HTTP API server

The third step of this lab was to create an API Server with the help of the Framework Javalin.
We've decided to develop an API Server to handle and manage a TODO list.

All configurations, code and the Dockerfile for the API server are located in the directory 'api-server' of this
repository.

Our api server supports the following operations:
Read:
- GET /api/todo/all > get all todos
- GET /api/todo/{id} > get a specific todo

Create:
- POST /api/todo/ > create a todo
(the text of the todo needs to be transmitted in the body of the request: the key name is 'text')

Update:
- PUT /api/todo/{id} > update the text of a specific todo
(the text of the todo needs to be transmitted in the body of the request: the key name is 'text')
- PUT /api/todo/{id}/setDone > mark a specific todo as done
- PUT /api/todo/{id}/setUnone > mark a specific todo as undone

Delete:
- DELETE /api/todo/{id} > delete a specific todo

To be able to run our Javalin API server in a Docker container we need to create a JAR file from our. To do so we use
Maven.
Command to compile our API server (needs to be executed from the api-server folder)
`mvn clean compile assembly:single`

To run this JAR file in a docker container we've created a new Docker image with the Dockerfile 'api-server/Dockerfile'.

Command to docker compose up with rebuilding all images:
`docker compose up --build`

## Step 4: Reverse proxy with Traefik

The fourth step of this lab was to configure a reverse proxy with Traefik.

How we implement the solution:
For this first part we used the docker compose file. We configured commands for the traefik container and labels for the
other containers/services to define how they should be managed by Traefik.
In addition to the explanations below, all the configuration (commands, labels, ...) are documented directly in the
docker compose file.

To establish basic connections, we gave command the following commands:

```
commands:
- "--api.insecure=true"  
- "--providers.docker=true" 
- "--providers.docker.exposedbydefault=false" 
- "--entrypoints.web.address=:80"
- "--entrypoints.websecure.address=:443"

And we mount the following volume
volumes:
- /var/run/docker.sock:/var/run/docker.sock
ports:
- "80:80" 
- "443:443"
- "8080:8080" 
```

Security benefits of a reverse-proxy:

- It is the only element that is exposed directly to the Internet.
- It allows to distribute connections to multiple instances of the same servers to prevent overloads

The Dashboard of Traefik can be accessed via the following address:

```
http://localhost:8080
```

On this dashboard all entrypoint, routers and services are listed that we've defined in the commands above and all HTTP
services that Traefik found thanks to its access to Docker sockets.

## Step 5: Scalability and load balancing

To enable load balancing on our services we add the label:

```
- "traefik.http.services.service-name.loadbalancer.server.port=80"
```

To deploy statically multiples instances we add the following commands to the docker compose files to the different
services:

```
deploy:
  replicas: 5
```

To deploy dynamically we use the following commands in the terminal where the docker compose file is situated:

```
docker compose up -d --scale <service-name>=<number of instance to create>
```

We can keep tracks of how many available servers in the Traefik dashboard by selecting the relevant service and see the
IP address of those servers.

To prove that load-balancing is correctly executed by the reverse-proxy for the nginx servers we can look at the access
logs files. In those logs we can see that each request is redirected to a different server.

## Step 6: Load balancing with round robin and sticky sessions

We add the following labels to enable sticky sessions to the API Java server service in the docker compose file:

```
- "traefik.http.services.api-service.loadBalancer.sticky=true"
- "traefik.http.services.api-service.loadBalancer.sticky.cookie.name=MyBelovedTest"
- "traefik.http.services.api-service.loadBalancer.sticky.cookie.secure=true"
```

To prove that sticky sessions are correctly executed by the reverse-proxy for the API Java server we've added a line in
our Javalin API server to write in the terminal whenever the route '/api/todo/all' is called.
When calling the api server with the same cookie, we can see that the request are redirected to the same server. When
deleting the cookie and calling the api server again, we can see that the request are redirected to another server.
![img.png](img.png)

## Step 7: Securing Traefik with HTTPS

Since we need to define a static configuration file for Traefik to define the dynamic configuration files for the
self-signed certificates, we moved some previously defined command to this file.

The dynamic config file contains the following lines to define the certificates that will be added to the default store
of Traefik:

```
tls:
  certificates:
    - certFile: "/etc/traefik/certs/cert.pem"
      keyFile: "/etc/traefik/certs/key.pem"
```

Now that Traefik has access to the certificates, we can add the following labels to enable tls on the different
services:

```
- "traefik.http.routers.router-name.tls=true" 
```

## Optional step 1: Management UI

As a management UI for our docker environment we've chosen Portainer.
Portainer is a webinterface that runs on a docker container and in which the docker environment can be orchestrated.

The documentation how to use Portainer can be found under https://docs.portainer.io/

To use Portainer we've added the service portainer in our docker compose file.
The webinterface of Portainer can then be accessed via the following url: https://localhost:9443/

## Optional step 2: Integration API

We've implemented a simple website (api_website) which calls the api server to get the todos and shows them.
On this website there is a form-field to create new todos and buttons to delete the existing todos.
