**Build and Run Instructions:**
* Copy tinyurl.sqlite from tinyurl/src/main/resources/ to some local space and specify that absolute path in DB_PATH variable of tinyurl-rest/tinyurl/src/main/java/me/rahulthakkar/tinyurl/utils/Constants.java class.  (Sorry for this ugly step)
* Run mvn clean install
* Deploy tinyurl.war to Tomcat
* Web client is located at http://localhost:8080/tinyurl/ (There is no authentication or authorization for any of the operations)
