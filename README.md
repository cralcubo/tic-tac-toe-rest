tic-tac-toe-rest
================

REST+JSON API for playing the game tic tac toe. This API follow many of the good practices (and I even used some of the code style) of the tutorial [Beautiful REST + JSON APIs with JAX-RS and Jersey](https://www.youtube.com/watch?v=ITmcAGvfcJI&list=WL&index=16 "Beautiful REST + JSON APIs with JAX-RS and Jersey") 

For this API were used JAX-RS/Jersey.

There are two controllers that do all the job to play the game: GamesController and PlayersController, so start inspecting this classes in case you are interested to see the way that the API was thought.

Methods
=======

GAMES
======

GET base_url/rest/games[?expand=true]
=====================================

This method will return all the games available in the server.
Response will look something like:

	` 
   {
    "href": "http://localhost:8080/tic-tac-toe-rest/rest/games",
    "offset": 0,
    "limit": 27,
    "items": [
        {
            "href": "http://localhost:8080/tic-tac-toe-rest/rest/games/19"
        },
        {
            "href": "http://localhost:8080/tic-tac-toe-rest/rest/games/17"
        },
        {
            "href": "http://localhost:8080/tic-tac-toe-rest/rest/games/18"
        }
     }
   `
In case you would want to check the content of all the games, you can add the request parameter: expand=true.

GET base_url/rest/games/{id}
============================

This method will get one of the games that already exist in the server.

POST base_url/rest/games
========================

This method will create a new game with two players, one for the Circle shapes and the other for the Cross shapes in the game.

PUT base_url/rest/games/{id} --data "{status:"aStatus"}"
========================================================
Where: id is here the game id and aStatus is one of the followings:
	- STARTED: Use this status to start the game.
	- FINISHED: Use this status to finish the game.
	
This method will either start a created game or will finish a game in progress.

DELETE base_url/rest/games/{id}
===============================
This method will delete an existent game.

PLAYERS
========

GET base_url/rest/players
=========================
This method returns all the players available in the game.

GET base_url/rest/players/{playerId}
====================================
This method retrieve the information of a player.

POST base_url/rest/players/{playerId}/{aCoordinate}
===================================================
Where aCoordinate is one of the 09 coordinates available in the tic tac toe game:
				 A1 | A2 | A3
				--------------
				 B1 | B2 | B3
				--------------
				 C1 | C2 | C3
				 
Exceptions
==========
The exceptions thrown when running the API are intercepted by the class DefaultExceptionMapper to display them in a JSON friendly way.

Testin tool
===========

To test the API I used [rest-shell](https://github.com/spring-projects/rest-shell "rest-shell") 
