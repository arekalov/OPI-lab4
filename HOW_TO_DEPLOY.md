### Чтобы задеплоить на гелиосе:
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