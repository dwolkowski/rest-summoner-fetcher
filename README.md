# REST Summoner Fetcher [![Build Status](https://app.travis-ci.com/dwolkowski/rest-summoner-fetcher.svg?branch=master)](https://app.travis-ci.com/dwolkowski/rest-summoner-fetcher)
Web application, which allows to fetch the data about the user's from the Riot Games API service. 

<br /><b>Written in: </b>
 - <b>Language:</b> Java 
 - <b>Technologies and Tools:</b> Spring, Gradle, Travis CI, Lombok

The available Endpoints are described below.

## Installation
<div><b>How to install the application: </b></div>

```
git clone https://github.com/dwolkowski/rest-summoner-fetcher
cd rest-summoner-fetcher
```

<br />

<div><b>For Windows users: </b></div>

```
gradlew.bat build
java -jar repository-fetcher-0.0.1.jar
```
<div><b>For Linux users: </b></div>

```
./gradlew build (or , while using Windows.)
./java -jar repository-fetcher-0.0.1.jar
```

After that, the application should launch soon.

## Endpoints

Endpoints are available on `http://localhost` on port `8080`.

| GET                                         | Return type                                                   | Success status codes   | Error status codes                     |
| --------------------------------------------| --------------------------------------------------------------| ---------------------  | ---------------------------------------|
| **/summoner/{user}**                        | A JSON response with detailed information about user.         | 200                    | 404 (Thrown when user cannot be found) |
| **/mastery/{user}**                         | A JSON response with champion mastery list of provided user.  | 200                    | 404 (Thrown when user cannot be found) |
| **/mastery/{user}/{championId}**            | A JSON response with chosen champion mastery of provided user.| 200                    | 404 (Thrown when user cannot be found) | 

## Testing
Work in progress...

<hr />
<div><b> |&nbsp; Dariusz Wołkowski &nbsp;|&nbsp; 2021 &nbsp;| </b> </div>
