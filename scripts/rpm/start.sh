#!/bin/sh
nohup $JAVA_HOME/bin/java  -jar $SERVER_OPTS /opt/bahmni-dhis2-integration-service/lib/bahmni-dhis2-integration-service-0.0.1-SNAPSHOT.jar --spring.config.location="/opt/bahmni-dhis2-integration-service/properties/" >> /var/log/bahmni-dhis2-integration-service/bahmni-dhis2-integration-service.log 2>&1 &
echo $! > /var/run/bahmni-dhis2-integration-service/bahmni-dhis2-integration-service.pid