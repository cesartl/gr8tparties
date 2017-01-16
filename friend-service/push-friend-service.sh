REPO="cesartl/friend-service:latest"
docker tag friend-service:0.0.1-SNAPSHOT $REPO
docker push $REPO
