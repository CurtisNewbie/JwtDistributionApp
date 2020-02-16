# JWT Token Distribution Server

A JavaEE8 Webapp that authenticates admins with role **"admin"** (using **HTTP BASIC**) and generates JWT tokens for them to communicate with backend servers using RESTful web services. This is used for the backend server in my another repository: <a href="https://github.com/CurtisNewbie/BookStoreApp">BookStoreApp</a>. It is not implemented in such a way that can be used for general purpose.

**! Note: Without HTTPS, BASIC is not safe at all, it's only a BASE64 encoded String**.

**Dependencies**:

- JavaEE 8 or Jakarta EE 8 API
- <a href="https://github.com/jwtk/jjwt">JJWT Library</a> for generating JWT

The Server/Container that you are using must implement the API being used.

## Configuration

### BASIC Authentication

This webapp provides REST enpoint to authenticate users and retrive JWT. It uses BASIC authentication to transfer provided username and password. This is not safe if HTTPs is not used.

For example, if one wants to get a JWT, he/she will need to send a GET request to the server as follows. A header for BASIC authentication is needed.

	curl -v -H "Authorization: Basic QWxhZGRpbjpPcGVuU2VzYW1l" http://localhost:8080/jwt/api/admin

Once the credential is verified, the generated JWT is sent to the clients in the HTTP response of "text/plain" type.

### Json Web Token

JWT is generated and signed using RS256 algorithm. A (asymmetric) private key is required. The private key is specified in **src/main/resources/config.txt** as follows:

`PATH_TO_PRIVATE_KEY=7SGru95I5OEl9+/nx8eihxcSi3RteW7sPP0vD452UZLJURtQx8oQi3TVGnp+VIX6w0i73G/sOFi2Vt8+T3/dQKBgQDtpygYtnzJLj5d4TRG7+p4KXtk9v3WvLPY+sCoDhLXeFcF3iPpm93qxLdQHVQu/An8RfE7q+7+5cxxlGVq08cDKEvGk8whG8d4r4L4TiU4q16LsF+KqNndnN0cpnKw9QJHuWnrN8oazfAr9mTwovZUwawT9N5LAdCp8xy5X77ubQKBgHvIBOeUtJU3KhXRRKLfwvCRTST+GV6DTluB2vemwgi6vM945lkD0Uk8ythZKiY4oCq7Eh5uAVB6R7q6Om3n5U+U01FNvVDJUvsYKC6pSgqNcnH18aZfIC71PgVTrv/9caBF8tps/hDlhG/OQQxEhdC9/nFzPHpCCyqfBS6wQeQVAoGARJ8U4GlyZDkqAz7jn820I8WCZttOsLjXFHW3fdg/vbV9g4yy3EltHqbHhMyZvJjh4DRbJadsBdkFhCsEeHmWrAxkVSIde9BFuecC45F7xzM8XpBxFB+efCzguk8/WoC0ikxDJ6aBCCizpQWws2WYS/c3wh48ETMHm0sRekEl2BE=............`

If you want to customise the claims or payload or the algorithm being used for JWT, you will need to change the code in **class Authenticator** and the **generateJWT()** method.

## Running This Webapp

First, package it into a WAR file using maven.

	mvn clean package

Second, deploy it to any server you want to use, e.g., Wildfly19. Then you are good to go.
