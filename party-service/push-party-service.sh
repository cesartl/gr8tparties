REPO="cesartl/party-service:latest"
docker tag party-service:0.0.1-SNAPSHOT $REPO
docker push $REPO
