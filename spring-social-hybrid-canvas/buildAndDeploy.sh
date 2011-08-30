#!/bin/sh
 
#use mvn -o -D... if you want to run in 'offline mode' 
#which is useful if you can't connect to a repository it will use your local one
mvn -DskipTests=true clean install -U

APP_NAME=spring-social-canvas

echo "Shutting down tomcat..."
$TOMCAT_HOME/bin/shutdown.sh

echo "pausing for 4 seconds"
sleep 4


echo "Deleting  dirs in tomcat"
rm -rf $TOMCAT_HOME/webapps/$APP_NAME.war
rm -rf $TOMCAT_HOME/webapps/$APP_NAME
rm -rf $TOMCAT_HOME/work/Catalina/localhost/$APP_NAME/*
rm -rf $TOMCAT_HOME/logs/*

echo "Deploying war to Tomcat"
cp -f target/$APP_NAME.war $TOMCAT_HOME/webapps/

echo "Starting tomcat"
$TOMCAT_HOME/bin/debug.sh

echo "Build and Deploy to Tomcat Complete"
