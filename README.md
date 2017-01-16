# gr8tparties
Simple project to showcase a micro service architecture using Spring Boot, Netflix OSS, Docker and Kubernetes

## What is it?
This is a simple application using a micro-services architecture. It aims at demonstrating how popular frameworks and technologies can work together to easily achieve a scalable and reliable micro-services architecture.

## What is it made of?
This project makes use of the following technologies:

- **Spring Boot** to bootstrap each micro-service
- **Netflix Zuul** to provide a Edge Service proxy that routes calls to the appropriate micro-service
- **Netflix Ribbon** to load balance requests
- **Netflix Feign** to create easily flexible and testable REST clients
- **Netflix hystrix** to provide failsafe architecture with fallbacks
- **Spring Configuration** to easily switch between local and cloud deployments
- **Kubeflix Ribbon Discovery** to provide service discovery and load balancer in a Kubernetes ClusterFirst
- **Docker** to containerize each micro-service
- **Kubernetes** to deploy and scale the Docker images

## How to throw a great party

Best parties are attended by friends and that's what this application aims to do!. The application is made of 3 services:

- **edge-service:** The only service exposed to the outside world. Its role is to route requests to the downstream services
- **friend-service:** This service is responsible for creating users and managing friendships
- **party-service:** This service allows you to create parties and see if your friends are attending a given party (by talking to the friend-service)  

## How to use?

### Pre-requisite: Install your Kubernetes Cluster
If you don't have a running Kubernetes (k8s) cluster, you can install one by following the [Kubernetes documentation](https://kubernetes.io/docs/getting-started-guides/).

If you have an AWS account do use [kops](https://github.com/kubernetes/kops) it rocks!

### Step 1: Run locally

Spring configuration provides the concept of profiles to easily group and segregate configurations between environments. The default profile is the `local` profile. It expects the service to run on the following ports:

- friend-service: port 8080
- party-service: port 8081

Let's start the services:

```Shell
cd friend-service
mvn clean spring-boot:run -DPORT=8080
```

```Shell
cd party-service
mvn clean spring-boot:run -DPORT=8081
```

```Shell
cd edge-service
mvn clean spring-boot:run -DPORT=9999
```

Now the services have started you can hit the API using your favourite REST client (mine is POSTMAN!)

create a few friends
```HTTP
POST /friend-service/friends/ HTTP/1.1
Host: localhost:9999
Content-Type: application/json; charset=utf-8
Accept: application/json
{
  "firstName" : "Mark",
  "lastName" : "Knopfler"
}
---
{
  "userId": "0",
  "firstName": "Mark",
  "lastName": "Knopfler"
}
```

```HTTP
POST /friend-service/friends/ HTTP/1.1
Host: localhost:9999
Content-Type: application/json; charset=utf-8
Accept: application/json
{
  "firstName" : "Jimmy",
  "lastName" : "Page"
}
---
{
  "userId": "1",
  "firstName": "Jimmy",
  "lastName": "Page"
}
```

```HTTP
POST /friend-service/friends/ HTTP/1.1
Host: localhost:9999
Content-Type: application/json; charset=utf-8
Accept: application/json
{
  "firstName" : "Eric",
  "lastName" : "Clapton"
}
---
{
  "userId": "2",
  "firstName": "Eric",
  "lastName": "Clapton"
}
```

Then setup some friendships (user 0 and user 1, user 0 and user 2):

```HTTP
GET /friend-service/friends/befriend/0/1 HTTP/1.1
Host: localhost:9999
Content-Type: application/json; charset=utf-8
Accept: application/json
{
  "firstName" : "Eric",
  "lastName" : "Clapton"
}
```

```HTTP
GET /friend-service/friends/befriend/0/2 HTTP/1.1
Host: localhost:9999
Content-Type: application/json; charset=utf-8
Accept: application/json
{
  "firstName" : "Eric",
  "lastName" : "Clapton"
}
```

Now let's great a party:

```HTTP
POST /party-service/ HTTP/1.1
Host: localhost:9999
Content-Type: application/json; charset=utf-8
Accept: application/json
---
{
  "id": "0"
}
```

Let's everyone attend

```HTTP
GET /party-service/attend/0/0 HTTP/1.1
Host: localhost:9999
Content-Type: application/json; charset=utf-8
Accept: application/json
---
{
  "id": "0",
  "invited": [
    {
      "userId": "0"
    }
  ]
}
```

```HTTP
GET /party-service/attend/0/1 HTTP/1.1
Host: localhost:9999
Content-Type: application/json; charset=utf-8
Accept: application/json
---
{
  "id": "0",
  "invited": [
    {
      "userId": "0"
    },
    {
      "userId": "1"
    }
  ]
}
```

```HTTP
GET /party-service/attend/0/2 HTTP/1.1
Host: localhost:9999
Content-Type: application/json; charset=utf-8
Accept: application/json
---
{
  "id": "0",
  "invited": [
    {
      "userId": "0"
    },
    {
      "userId": "1"
    },
    {
      "userId": "2"
    }
  ]
}
```

Let's see if user 1 should go to this party?

```HTTP
GET /party-service/myParties/0 HTTP/1.1
Host: localhost:9999
Content-Type: application/json; charset=utf-8
Accept: application/json
---
{
  "parties": [
    {
      "id": "0",
      "invited": [
        {
          "userId": "0"
        },
        {
          "userId": "1"
        },
        {
          "userId": "2"
        }
      ],
      "friends": [
        {
          "userId": "1",
          "firstName": "Jimmy",
          "lastName": "Page"
        }
      ]
    }
  ]
}
```

### Step 2: Build with Docker

Each project uses the Fabric8 `docker-to-maven` plugin that easily creates docker images for you:

```Shell
cd friend-service
mvn clean package docker:build
```

```Shell
cd party-service
mvn clean package docker:build
```

```Shell
cd edge-service
mvn clean package docker:build
```

If you want you can run all docker images locally by exposing the same ports as Step 1.

### Step 3: Push your docker images

If you don't have an account on [Docker Hub](https://www.docker.com/) create one.

Then create 3 repositories:

- friend-service
- party-service
- party-edge-service

First login to Docker
```Shell
docker login
```

Then tag and push your 3 images

```Shell
cd friend-service
docker tag friend-service:0.0.1-SNAPSHOT "${DOCKER_USERNAME}/friend-service:latest"
docker push "${DOCKER_USERNAME}/friend-service:latest"
```

```Shell
cd party-service
docker tag party-service:0.0.1-SNAPSHOT "${DOCKER_USERNAME}/party-service:latest"
docker push "${DOCKER_USERNAME}/party-service:latest"
```

```Shell
cd edge-service
docker tag party-edge-service:0.0.1-SNAPSHOT "${DOCKER_USERNAME}/party-edge-service:latest"
docker push "${DOCKER_USERNAME}/party-edge-service:latest"
```

### Step 4: Deploy to K8s

Edit the 3 k8s config files in the k8s folder with your Docker username and k8s namespace (you can use default namespace if you want)

```Shell
cd k8s
kubectl -f edge-service.yaml
kubectl -f friend-service.yaml
kubectl -f party-service.yaml
```

The edge service is configured to have an external load balancer. The effect will depend on your cloud provider. For AWS it will create an ELB endpoint. Give it a few minutes to get ready and then run;

```Shell
kubectl describe svc edge-service --namespace cesar | grep Ingress
```

it will give you the public address to your edge service. You can now repeat step 1 by replacing `localhost:9999` with your public enpoint!
