This project works as a Hotel room booking application. A user can have one of three profiles - CUSTOMER, HOTEL_MANAGER, ADMIN. A customer can book rooms, check in and check out. A hotel manager can create update hotel details. The admin can create, delete hotels, extract data about hotels, etc.

The roles are enforced via authentication and authorization by using JWT. Upon successful login, a JWT is generated and shared with the user. Most of the endpoints are private, however there are some public endpoints such for registering and logging in, viewing various hotel information.

Errors have been handled gracefully using Exceptions and logging has been also implemented.

A JAR file has also been generated and it can be used by running the instruction: java -jar build/libs/Stay_Ease_Project-0.0.1-SNAPSHOT.jar
