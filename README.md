# api-mashup

Combines GET request to Github API and Twitter to return summary of Github repositories and tweets mentioning the repositories.

How to run:<br />
1. Build package:<br />
mvn package spring-boot:repackage

2. Run server (replace 'x's with twitter consumer key and secret):<br />
java -jar target/api-mashup-1.0-SNAPSHOT.jar 
--oauth.consumerKey=xxxxxxxxxxxxxx
--oauth.consumerSecret=xxxxxxxxxxxxxxxxxxxxxxx
--oauth.accessToken=xxxxxxxxxxxxxxxxxxxxxxxx
--oauth.accessTokenSecret=xxxxxxxxxxxxxxxxxxxxx

3. Call endpoint by pasting into web browser URL:<br />
http://localhost:8080/githubTwitter<br />
or<br />
http://localhost:8080/githubTwitter?q=xxxxxx (replace 'x's with search term)
  
  
