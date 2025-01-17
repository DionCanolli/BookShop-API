Project Title: Book Shop API

Description: This is a Book Shop API, used to buy, find, update and delete books (and many other entities) based on book (their)specifications. Books are written by authors and have one genre. Users can buy books and drop reviews for the books he/she bought. 

Installation Instructions: Clone the project and open it in any IDE (IntelliJ for example) and the .json file contains the Postman endpoints for the project, so open that in Postman and run the project. About MongoDB, all you nede to do is have it installed, and tables will hopefully be filled automatically.

Technologies Used: Java, Spring Framework, Spring Boot, Spring Data MongoDB, Spring Data JPA, Project Lombok, Spring Security, JWT, MongoDB and Postman.

Features: This is a Book Shop API, used to buy, find, update and delete books (and many other entities) based on book (their)specifications.
    These are the entities:
        1. Books
        2. Genres
        3. Authors
        4. BookAuthors (a book can have many authors)
        5. Roles
        6. Users
        7. Transactions
        8. Reviews
    Here are some features:
        - Sign up -> using JWT Tokes
        - Login -> also using JWT Tokens
        - Logout -> it blacklists the JWT Token by saving it in a table, then you cant login/signup using that token
        - Insert Book/s, Genre/s, Author/s, Role/s, Transaction, Review, BookAuthor/s
        - Find All Entities (for example all books) or specific Entities by their attributes
        - Update Books/Genres/Authors/BookAuthor by their attributes
        - Delete Entities by their attributes
  Note: When you login, get the JWT Token in response header, and put it in request headers of the requests.
