#!/bin/sh
mvn install:install-file -Dfile=./lib/activemq-all-5.4.1.jar -DgroupId=activemq -DartifactId=activemq -Dversion=5.4.1 -Dpackaging=jar
