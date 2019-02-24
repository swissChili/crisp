#!/usr/bin/env bash

CRISP_INSTALL_URL="https://gitlab.com/swissChili/crisp/"
VERSION="0.1.1"

if [ ! -f /etc/crisp-standalone.jar ]; then
	echo "Root access required to install `jar`."
	cd /etc/
	sudo git clone "$CRISP_INSTALL_URL"
	cd "$CRISP_INSTALL_URL"
	sudo lein uberjar
	sudo cp "target/uberjar/crisp-$VERSION-standalone.jar" /etc/crisp-standalone.jar
	echo "Installed Crisp"
else
	java -jar /etc/crisp-standalone.jar $*
fi
