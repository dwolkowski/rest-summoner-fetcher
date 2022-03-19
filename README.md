# REST Summoner Fetcher [![Build Status](https://app.travis-ci.com/dwolkowski/rest-summoner-fetcher.svg?branch=master)](https://app.travis-ci.com/dwolkowski/rest-summoner-fetcher)
Web application, which allows to fetch the data about the user's from the Riot Games API service.

**Application written in:**
- **Language:** Java
- **Technologies and Tools:** Spring, Gradle, Travis CI, Lombok

The available **Endpoints** are described below.
___
## Installation
How to install the application:
```
git clone https://github.com/dwolkowski/rest-summoner-fetcher
cd rest-summoner-fetcher
```

Now to gain access to **Riot Games API** you need to generate yours personal **Token** on [Riot Developer Portal](https://developer.riotgames.com/)
and paste it into the file `riotToken.txt`. The next step is:

For **Windows** users:
```
gradlew.bat build
java -jar build/libs/summoner-fetcher-1.0.0.jar
```
For **Linux** users:
```
./gradlew build 
java -jar ./build/libs/summoner-fetcher-1.0.0.jar
```
After that, the application should launch soon.
___
## Endpoints

Endpoints are available on `http://localhost` on port `8080`.

| GET Method                          | Return type                                                   | Success status codes   | Error status codes                     |
| ------------------------------------| --------------------------------------------------------------| ---------------------  | ---------------------------------------|
| **/summoner/{user}**                | A JSON response with detailed information about user.         | 200                    | 404 (Thrown when user cannot be found) |
| **/mastery/{user}**                 | A JSON response with champion mastery list of provided user.  | 200                    | 404 (Thrown when user cannot be found) |
| **/mastery/{user}/{champion}**      | A JSON response with chosen champion mastery of provided user.| 200                    | 404 (Thrown when user or champion cannot be found) |
| **/mastery/{user}/chest/acquired**  | A JSON response with list of champions where chest was acquired.  | 200                | 404 (Thrown when user cannot be found) |
| **/mastery/{user}/chest/available** | A JSON response with list of champions where chest is available.  | 200                | 404 (Thrown when user cannot be found) |

<sup> ***`user` - Username of the player*** </sup> <br />
<sup> ***`champion` - Id or Name of the champion*** </sup>
___
## Testing
To test the application, simply execute this command:
- For **Windows** users `gradlew.bat test`
- For **Linux** users `./gradlew test`

The tests should run automatically.

___
<div><b> |&nbsp; Dariusz Wołkowski &nbsp;|&nbsp; 2021 &nbsp;| </b> </div>



