#!/bin/bash

echo "Deploying to Helios"

## Remove existing deployment
ssh -p 2222 s409449@se.ifmo.ru "rm -rf /home/studs/s409449/Web/lab3/wildfly-preview-26.1.3.Final/standalone/deployments/interactive-graph-ui-1.0-SNAPSHOT*"
# add new deployment
scp -P 2222  /home/arekalov/IdeaProjects/OPI-lab4/build/libs/interactive-graph-ui-1.0-SNAPSHOT.war s409449@se.ifmo.ru:/home/studs/s409449/Web/lab3/wildfly-preview-26.1.3.Final/standalone/deployments
