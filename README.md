# keycloak custom validation endpoint

[![N|Solid](https://www.loginradius.com/authenticate/assets/img/providers/keycloak.png)](https://nodesource.com/products/nsolid)

Add custom REST endpoints.

in general, the tokens validation is done by an a default introspect endpoint of keycloak, but this endpoint returns more information than necessary,

then the purpose of this project plugin is to create a personalized endpoint on the keycloak side in order to validate the Token and retrieve the associated scopes for this token .

* example call to endpoint
```sh
GET /auth/realms/applications/token/IssuedFor/testclient/validation HTTP/1.1
Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5c ...
```

* response if the token is valid
```sh
Scopes :profile email
```

* response in case the token is invalid
```sh
{
    "error": "HTTP 401 Unauthorized"
}
```

* response in case the client is invalid
```sh
GET /auth/realms/applications/token/IssuedFor/invalidClient/validation HTTP/1.1
Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5c ...
```
```sh
{
    "error": "Token is not properly issued for invalidClient"
}
```


don't hesitate to contribute :)

Thanks
M-AMAIRI
