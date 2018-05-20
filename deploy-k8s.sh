NAMESPACE="cesar" #chante to your k8s namespace

kubectl apply -n $NAMESPACE -f k8s/party-service.yaml
kubectl apply -n $NAMESPACE -f k8s/friend-service.yaml
kubectl apply -n $NAMESPACE -f k8s/edge-service.yaml

kubectl apply -n $NAMESPACE -f k8s/roles.yaml
