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
- **Protocol Buffer** to define message structure for communicating with the micro-services
- **Docker** to containerize each micro-service
- **Kubernetes** to deploy and scale the Docker images

## How to throw a great party

Best parties are attended by friends and that's what this application aims to do!. The application is made of 3 services:

- **edge-service:** The only service exposed to the outside world. Its role is to route requests to the downstream services
- **friend-service:** This service is responsible for creating users and managing friendships
- **party-service:** This service allows you to create parties and see if your friends are attending a given party (by talking to the friend-service)  

## Pre-requisite

### Java

This project require Java 10 to build - that's a good excuse to upgrade ;)

### Pre-requisite: Install your Kubernetes Cluster
If you don't have a running Kubernetes (k8s) cluster, you can install one by following the [Kubernetes documentation](https://kubernetes.io/docs/getting-started-guides/).

If you have an AWS account do use [kops](https://github.com/kubernetes/kops) it rocks!

## How to use?

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

```bash
curl -d "@json/mark.json" -H "Content-Type: application/json" -H "Accept: application/json" -X POST localhost:9999/friend-service/friends
```

```json
{
  "id": "5b0d768008ad890007202e6f",
  "username": "mark",
  "firstName": "Mark",
  "lastName": "Knopfler"
}
```

```bash
curl -d "@json/tracy.json" -H "Content-Type: application/json" -H "Accept: application/json" -X POST localhost:9999/friend-service/friends
```

```json
{
  "id": "5b0d768008ad890007202e70",
  "username": "tracy",
  "firstName": "Tracy",
  "lastName": "Chapman"
}
```

```bash
curl -d "@json/eric.json" -H "Content-Type: application/json" -H "Accept: application/json" -X POST localhost:9999/friend-service/friends
```

```json
{
  "id": "5b0d768008ad890007202e71",
  "username": "eric",
  "firstName": "Eric",
  "lastName": "Clapton"
}
```

Then setup some friendships (`mark` <-> `tracy` and `mark` <-> `eric`):

```bash
curl -H "Accept: application/json" -X GET localhost:9999/friend-service/friends/befriend/mark/tracy
```

```bash
curl -H "Accept: application/json" -X GET localhost:9999/friend-service/friends/befriend/mark/eric
```

Now let's great a party:

```bash
curl -H "Accept: application/json" -X POST localhost:9999/party-service/
```

```json
{
  "id": "5b0d7937fc938500072fb3a5"
}
```

Let's everyone attend

```bash
curl -H "Accept: application/json" -X GET localhost:9999/party-service/attend/5b0d7937fc938500072fb3a5/mark
```

```bash
curl -H "Accept: application/json" -X GET localhost:9999/party-service/attend/5b0d7937fc938500072fb3a5/tracy
```

```bash
curl -H "Accept: application/json" -X GET localhost:9999/party-service/attend/5b0d7937fc938500072fb3a5/eric
```

Let's see if user mark should go to this party?

```bash
curl -H "Accept: application/json" -X GET localhost:9999/party-service/myParties/eric
```

```json
{
    "parties": [
        {
            "id": "5b0d7937fc938500072fb3a5",
            "invited": [
                {
                    "username": "mark"
                },
                {
                    "username": "tracy"
                },
                {
                    "username": "eric"
                }
            ],
            "friends": [
                {
                    "id": "5b0d768008ad890007202e6f",
                    "username": "mark",
                    "firstName": "Mark",
                    "lastName": "Knopfler"
                }
            ]
        }
    ]
}
```

If the friend service is not available or becomes too slow, the request gracefully fallbacks to a reduced answer.

For example if you stop the `friend-service` process the response will be:

```json
{
    "parties": [
        {
            "id": "5b0d7937fc938500072fb3a5",
            "invited": [
                {
                    "username": "mark"
                },
                {
                    "username": "tracy"
                },
                {
                    "username": "eric"
                }
            ]
        }
    ]
}
```

### Step 2: Build with Docker

Each project has a `Dockerfile` which will build a Docker image. The `docker-build.sh` script will build the project using Maven and then run the `docker build` command.

```Shell
cd friend-service
./docker-build.sh
```

```Shell
cd party-service
./docker-build.sh
```

```Shell
cd edge-service
./docker-build.sh
```

The `Dockerfile`s use Jlink to create a small JDK that can be used at runtime.

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

In `push-all.sh` change this line to point to your Docker repo:
```shell
REPO="cesartl"
```

Then simply run `push-all.sh`

At this point you have 3 Docker images in your Docker repository which are ready to be used by Kubernetes.

### Step 4: Deploy to K8s

In `deploy-k8s.sh` change the following line to point to your k8s namespace:

```Shell
NAMESPACE="cesar"
```
Then run `deploy-k8s.sh`


The edge service is configured to have an external load balancer. The effect will depend on your cloud provider. For AWS it will create an ELB endpoint. Give it a few minutes to get ready and then run;

```Shell
kubectl describe svc edge-service --namespace cesar | grep Ingress
```

it will give you the public address to your edge service. You can now repeat step 1 by replacing `localhost:9999` with your public endpoint!

## TODOS

- Add javadoc and comments in the code
- Add comment to maven.xml to explain each import
- Add unit tests for controller
- Replace Zuul with Spring Cloud gateway and get reactive!
