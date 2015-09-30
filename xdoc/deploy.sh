sudo ps -ef | grep tomcat | awk '{print $2}' | xargs sudo kill -9
sudo rm -rf /home/aaron/server/apache-tomcat-7.0.63/logs/catalina.out

cd project/wolai-web/
git pull
mvn clean package
sudo rm -rf /home/aaron/server/apache-tomcat-7.0.63/webapps/wolai-web
sudo rm -rf /home/aaron/server/apache-tomcat-7.0.63/webapps/wolai-web.war

sudo rm target/epark/WEB-INF/classes/config.properties
sudo cp target/epark/WEB-INF/classes/config-production.properties target/epark/WEB-INF/classes/c
onfig.properties
sudo cp -r target/epark /home/aaron/server/apache-tomcat-7.0.63/webapps/wolai-web

sudo /home/aaron/server/apache-tomcat-7.0.63/bin/startup.sh