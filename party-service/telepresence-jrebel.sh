function version_gt() { test "$(printf '%s\n' "$@" | sort -V | head -n 1)" != "$1"; }
telepresence_min=0.85
telepresence_version=$(telepresence --version)
if version_gt $telepresence_min $telepresence_version; then
    echo "Minimum Telepresence 0.85 required but $telepresence_version found; please upgrade"
    exit
fi


NAMESPACE=cesar
JREBEL=$HOME/jrebel-nightly
telepresence --namespace $NAMESPACE --mount=/tmp/known --swap-deployment party-service --docker-run --rm -v=/tmp/known/var/run/secrets:/var/run/secrets -v$(pwd):/build -v $HOME/.m2/repository:/m2 -v $JREBEL:/jrebel -v $JREBEL/jrebel.jar:/jrebel.jar -v $HOME/.jrebel:/root/.jrebel -p 8080:8080 -p 5005:5005 -e MAVEN_OPTS="-agentpath:/jrebel/lib/libjrebel64.so" 738398925563.dkr.ecr.eu-west-2.amazonaws.com/maven-build:jdk10 mvn -Dmaven.repo.local=/m2 -f /build spring-boot:run