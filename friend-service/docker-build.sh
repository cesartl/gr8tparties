IMAGE_NAME=friend-service
TAG=jdk10
mvn clean package
docker build -t $IMAGE_NAME:$TAG .