## Чтобы задеплоить на гелиосе:
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
3. Пробросить порты командой ниже, где 28600 порт для http из `wildfly-preview-26.1.3.Final/standalone/configuration`:
   ```bash
   ssh -L 28600:localhost:28600 s409449@se.ifmo.ru -p 2222
   ``` 
4. Выполнить скрипт `wildfly-preview-26.1.3.Final/bin/standalone.sh`
   ```bash
   ssh -L 28600:localhost:28600 s409449@se.ifmo.ru -p 2222 "bash -c '~/Web/lab3/wildfly-preview-26.1.3.Final/bin/standalone.sh'"
   ``` 
5. Перейти на http://127.0.0.1:28600/interactive-graph-ui-1.0-SNAPSHOT, где interactive-graph-ui-1.0-SNAPSHOT - название `.war` файла


## JConsole
1. Для запуска с профилировщиком выполнить шаги из "Чтобы задеплоить на гелиосе" 1-3
2. Запуск приложения
   ```bash
   ssh -L 28600:localhost:28600 -L 28603:localhost:28603 s409449@se.ifmo.ru -p 2222 "bash -c '~/Web/lab3/wildfly-preview-26.1.3.Final/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0'"
   ```
3. Пробросить порты
   ```bash
   ssh -L localhost:28600:0.0.0.0:28600 -L  localhost:28603:0.0.0.0:28603 s409449@se.ifmo.ru -p 2222
   ```
4. Запустить Jconsole
   ```bash
   jconsole -J-Djava.class.path=/Users/arekalov/Itmo/4/OPI/lab4/jboss-cli-client.jar
   ```
5. Ввести `service:jmx:remote+http://0.0.0.0:28603`
