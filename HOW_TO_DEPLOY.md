### Как поднять лабу
PS: На маке и виндовсе не завелось. На виртуалке с убунтой все норм.

1. Создать новый `.war` файл
   ```bash
   ./gradlew clean build
   ``` 
2. Скопировать .war на гелиос с помощью `deploy.sh`:
   1. Удалить файлы `*.war` из `/wildfly-preview-26.1.3.Final/standalone/deployments`
   2. Cкопировать свой `.war` файл в эту же шапку
   ```bash
   ./deploy.sh
   ```
3. Запуск приложения на сервере
   ```bash
   ~/Web/lab3/wildfly-preview-26.1.3.Final/bin/standalone.sh 
    -b 0.0.0.0 \
    -Dcom.sun.management.jmxremote \
    -Dcom.sun.management.jmxremote.authenticate=false \
    -Dcom.sun.management.jmxremote.ssl=false \
    -Dcom.sun.management.jmxremote.port=28603 \
    -Dcom.sun.management.jmxremote.rmi.port=28603 \
    -Djava.rmi.server.hostname=0.0.0.0 \
    -Djboss.bind.address.management=0.0.0.0 \
    -Djboss.bind.address=0.0.0.0
   ```
4. Пробросить порты
   ```bash
   ssh -L 28600:localhost:28600 -L 28603:localhost:28603 s409449@se.ifmo.ru -p 2222
   ```
5. Запустить Jconsole
   ```bash
   jconsole -J-Djava.class.path=/home/arekalov/IdeaProjects/OPI-lab4/jboss-cli-client.jar
   ```
6. Ввести `service:jmx:remote+http://0.0.0.0:28603`
7. Само приложение доcтупно на http://127.0.0.1:28600/interactive-graph-ui-1.0-SNAPSHOT

### Локально
1. Создать новый `.war` файл
   ```bash
   ./gradlew clean build
   ``` 
2. ```bash
      rm -rf /home/arekalov/IdeaProjects/localWf/wildfly-preview-26.1.3.Final/standalone/deployments/interactive-graph-ui-1.0-SNAPSHOT*
      cp /home/arekalov/IdeaProjects/OPI-lab4/build/libs/interactive-graph-ui-1.0-SNAPSHOT.war /home/arekalov/IdeaProjects/localWf/wildfly-preview-26.1.3.Final/standalone/deployments
   ```
3. Пробросить порты для db
   ```bash
   ssh -L 5432:localhost:5432 s409449@se.ifmo.ru -p 2222
   ```
4. ```bash
      /home/arekalov/IdeaProjects/localWf/wildfly-preview-26.1.3.Final/bin/standalone.sh
      -b 0.0.0.0 \
      -Xms256m \
      -Xmx1024m\
      -Dcom.sun.management.jmxremote \
      -Dcom.sun.management.jmxremote.authenticate=false \
      -Dcom.sun.management.jmxremote.ssl=false \
      -Dcom.sun.management.jmxremote.port=28603 \
      -Dcom.sun.management.jmxremote.rmi.port=28603 \
      -Djava.rmi.server.hostname=0.0.0.0 \
      -Djboss.bind.address.management=0.0.0.0 \
      -Djboss.bind.address=0.0.0.0
   ```
5. Само приложение доcтупно на http://127.0.0.1:28600/interactive-graph-ui-1.0-SNAPSHOT
6. ```bash
   visualvm
   ```
