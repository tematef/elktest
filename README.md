# Elastic api calls test solution
Positive tests for post, update and search call are implemented.
Results for testing could be found in allure report.
For getting test result execute command from project folder:
docker-compose up 

Report will be available in elktest/mnt folder

For running tests locally execute command (check elastic is running on your localhost):
mvn clean test -Delk.host=localhost