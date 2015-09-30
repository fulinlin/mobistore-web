scp -r www-dist/* 101.200.189.57:/home/aaron/server/apache-tomcat-8.0.26/webapps/mobistore/client

gulp build-web
http-server www-dist -a 127.0.0.1 -p 8100
