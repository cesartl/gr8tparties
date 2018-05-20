REPO="cesartl" #Change to your Docker repository
PARTY_SERVICE="party-service"
FRIEND_SERVICE="friend-service"
EDGE_SERVICE="edge-service"
TAG="jdk10"

docker tag $PARTY_SERVICE:$TAG $REPO/$PARTY_SERVICE:$TAG
docker tag $FRIEND_SERVICE:$TAG $REPO/$FRIEND_SERVICE:$TAG
docker tag $EDGE_SERVICE:$TAG $REPO/$EDGE_SERVICE:$TAG

docker push $REPO/$PARTY_SERVICE:$TAG
docker push $REPO/$FRIEND_SERVICE:$TAG
docker push $REPO/$EDGE_SERVICE:$TAG
