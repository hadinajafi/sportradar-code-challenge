# sportradar-code-challenge
The code challenge solution for SportRadar

# The solution
I developed the solution with an assumption that the program and business
models contained of entities (models) of a Team and a Match (which I called here Game).

The Game contains of 2 teams and a Score. The Game includes a unique id
to be identifiable.
The Only modifiable factors to a game I assumed is a finish date and score.

Development of this solution is based on TDD and the test coverage is 97%

# How to run the tests

Tests can be run with Maven, via `mvn clean verify` command in terminal.