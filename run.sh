docker run -d --name database -p 3306:3306 -e MYSQL_ROOT_PASSWORD=alesaudate -e MYSQL_DATABASE=vagas mysql:5.7.24
mvn clean package
