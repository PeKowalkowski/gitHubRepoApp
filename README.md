GitHub Repository APP
===
This is an application that uses the github API. The main purpose of the application is to search for user repositories 
by their name. The program finds only those repositories that are not forks and displays data such as:
- Repository name
- Owner's login
- All branches with name and last commit sha 
- The application returns an error if the user does not exist in github or the answer is in xml format

The first step is to generate your own token on github, here is link :
https://www.youtube.com/watch?v=9lGcbQR4k4Y.
Then paste it into "application.properties"
```
github.api.token = your API token
```

### Example of using the application is presented in the application for testing - Postman.

- To search for user repositories you should enter the path to the GET method
```
http://localhost:8080/api/github/repositories/enterExistingUsername
```
- 404 error if user does not exist
```
http://localhost:8080/api/github/repositories/enterNonExistentUsername
```
- 406 error if answer is in xml format. in the "Headers" tab, in the key enter "Accept" and in the value "application/xml". Then you will get error 406
