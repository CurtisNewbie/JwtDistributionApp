# JWT Token Distribution Server

A JavaEE8 Webapp that authenticates admins with role **"admin"** (using **HTTP BASIC**) and generates JWT tokens for them to communicate with backend servers using RESTful web services. This is used for the backend server in my another repository: <a href="https://github.com/CurtisNewbie/BookStoreApp">BookStoreApp</a>. It is not implemented in such a way that can be used for general purpose.

**! Note: Without HTTPS, BASIC is not safe at all, it's only a BASE64 encoded String**.

**_If you are using this webapp for <a href="https://github.com/CurtisNewbie/BookStoreApp/">BookstoreApp</a>, their private/public keys are matched intentionally, which works out-of-the-box for demonstration purpose. Nonetheless, you will need to setup the data source for it. For security reson, you should change the keys._**

### Dependencies

- JavaEE 8 or Jakarta EE 8 API
- Microprofile (<a href="https://github.com/eclipse/microprofile-config">MP-Config</a>)
- <a href="https://github.com/jwtk/jjwt">JJWT Library</a> for generating JWT

The Server/Container that you are using must implement the API being used.

## Configuration

### DBMS/ Database

Admin credentials are stored in a DBMS. This program will create a new table for storing admin credentials if not exists. By default, the username is stored as plaintext, but only the hash of the password and the salt being used are stored in the database. No password is stored in plain text.

To use the database, you must configure the **persistence.xml** as follows:

    <jta-data-source> your jta data source </jta-data-source>

Note: **You will need to manually add your admin credentials into your database, this webapp only retrieves existing credentials and verifies them for you.**

To create credentials:

1. Add salt to your password (concatenation)
2. Hash them using SHA256
3. Insert them to the table

e.g., this is only an example, don't use it for security reason

    ---------------
    name: apple
    password: juice
    --------------

    +--------+------------------------------------------------------------------+------+
    | name   | keyHash                                                          | salt |
    +--------+------------------------------------------------------------------+------+
    | apple  | acdb4ae487227ba3ac329e58a2c250673951e6ce7994c4c4bceeaa7ad6d42cb6 | 1234 |
    +--------+------------------------------------------------------------------+------+

### Json Web Token

JWT is generated and signed using **RS256 algorithm**, which utilises asymmetric cryptography for digital signature (<a href="https://en.wikipedia.org/wiki/JSON_Web_Token">More on wiki</a>). A private key is required. The private key is specified in **./jwttokendistrib/src/main/resources/META-INF/microprofile-config.properties** as follows:

**jwt_private_key=**`MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDGkfbaIIRnBwygY9K/jZi1xZs6py4V7UIUelp1agl+p+gxQeKSYbsvwimSX+xpdYQFw3xVib2FILPDLc3sqXrtdysfb7XaPIhCLd33K1kgTJDSkMuxU0If0mIbx+aMegvX5Ekv0TH3u6gBA/O3JtNlDnzIUPtGFrOuNbe7hC0NiY6ZyvTtMz2a+PcyLicE5Kyiahq2v95Yp9w/86tfJRvMwl7garem4gVUJJPJ+/aT1J564VbuIZHvrCCkNZ9RU2iG413lBYJIGnq707FMmzFNpsGidyZRlyGBWLsdz..........`

**_If you are using this webapp for <a href="https://github.com/CurtisNewbie/BookStoreApp/">BookstoreApp</a>, their private/public keys are matched intentionally, which works out-of-the-box for demonstration purpose. You should change them for security reason._**

If you want to customise the claims or payload or the algorithm being used for JWT, you will need to change the code in **class Authenticator** and the **generateJWT()** method.

## Deployment

First, package it into a WAR file using maven.

    mvn clean package

Second, deploy it to any server you want to use, e.g., Wildfly19. Then you are good to go.

## Using This App

### GET Request and BASIC Authentication

This webapp provides REST enpoint to authenticate users and retrive JWT. It uses BASIC authentication to transfer provided username and password. This is not safe if HTTPs is not used.

For example, if one wants to get a JWT, he/she will need to send a GET request to the server as follows. A header for BASIC authentication is needed.

    curl -v -H "Authorization: Basic YXBwbGU6anVpY2U=" http://localhost:8080/jwt/api/admin

The authorization header above uses the credential (username: apple, password: juice). Once the credential is verified, the generated JWT is sent to the clients in the HTTP response ("text/plain").
