#!/bin/sh
java -Dexternal.sys.properties=file:system.properties 
-Dexternal.app.properties=file:application.properties -jar shortme-0.0.1-SNAPSHOT.jar
