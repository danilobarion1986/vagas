docker run -d --name database -p 3306:3306 -e MYSQL_ROOT_PASSWORD=alesaudate mysql:8.0.13
mvn clean package
