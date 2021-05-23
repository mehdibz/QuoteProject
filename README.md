# Terminology
* Symbol: The identifier for something that can be traded.
* Bid: The price a trader offers to buy a symbol.
* Ask: The price a trader offers to sell a symbol.
* Quote: The highest bid and lowest ask.

# Instructions
A web app that reads the latest quote for a symbol from an in-memory database.

## Database
You'll need to retrieve the latest quotes from the in-memory H2 database.

Database initialized by `init.sql` in the `resources` folder.
`application.properties` already has the Spring Boot configuration to connect to this database.

The database only has one table, `QUOTE`, which stores each day's quotes for the symbols, 

e.g.:

| SYMBOL |     Day    |  BID |  ASK |
| ------ | ---------- | ---- | ---- |
|  MSFT  | 2020-01-01 | 1.23 | 4.56 |
|  ...   |    ...     | ...  | ...  |

The table is keyed by the combination of `SYMBOL` and `DAY`.

## API
The web app has two endpoints: 

1- First one accepts any symbol (e.g., `MSFT`) and returns the most recent quote for that symbol.

2- The second endpoint, retrieve the symbol with the highest ask for a given day.

Example:

    GET http://localhost:8080/quoteMedia/rest/quoteservices/symbols/MSFT/quotes/latest
    {"bid": 1.23, "ask": 4.56}
    
    GET  http://localhost:8080/quoteMedia/rest/quoteservices/symbols/2020-01-05/asks/highest
    {"bid": GOOG, "ask": 5.85}

-The symbol length is at least 2 characters and at most 6 which is validate through the service.
-If the symbol is valid and data exists, the endpoint return a 200 OK response code and a JSON body with `bid` and `ask` fields.
-If the symbol is valid, but no data exists, the endpoint return a 404 Not Found response code.
-If the symbol is not valid,the endpoint should return a 400 Bad Request response code.
