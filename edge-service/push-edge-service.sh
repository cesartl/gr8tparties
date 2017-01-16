REPO="cesartl/party-edge-service:latest"
docker tag edge-service:0.0.1-SNAPSHOT $REPO
docker push $REPO
