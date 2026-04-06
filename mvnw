#!/bin/sh
# ----------------------------------------------------------------------------
# Apache Maven Wrapper startup shell script, version 3.2.0
# ----------------------------------------------------------------------------

MAVEN_PROJECTBASEDIR="${MAVEN_BASEDIR:-$(dirname "$0")}"
MAVEN_WRAPPER_PROPERTIES="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.properties"

DIST_URL=$(grep "distributionUrl" "$MAVEN_WRAPPER_PROPERTIES" | cut -d'=' -f2)
DIST_VERSION=$(echo "$DIST_URL" | sed 's/.*apache-maven-//;s/-bin.zip//')

MAVEN_HOME="$HOME/.m2/wrapper/dists/apache-maven-$DIST_VERSION"

if [ ! -f "$MAVEN_HOME/bin/mvn" ]; then
  echo "Downloading Apache Maven $DIST_VERSION..."
  mkdir -p "$MAVEN_HOME"
  ZIP="$MAVEN_HOME/maven-dist.zip"
  curl -fsSL -o "$ZIP" "$DIST_URL" || wget -q -O "$ZIP" "$DIST_URL"
  unzip -q "$ZIP" -d "$MAVEN_HOME"
  rm -f "$ZIP"
  mv "$MAVEN_HOME"/apache-maven-*/* "$MAVEN_HOME/"
  rmdir "$MAVEN_HOME"/apache-maven-*
fi

exec "$MAVEN_HOME/bin/mvn" "$@"
