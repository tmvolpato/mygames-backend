# mygames-backend
API My Games

# Start application My Games
~~~
$ cd mygames-backend
~~~

~~~
$ mvn package
~~~

### Docker Compose 

~~~
$ docker-compose build --no-cache
~~~

~~~
$ docker-compose up -d
~~~

# Access endpoint of My games
 
 ### Get access token 
~~~
curl --user angular:12345 -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'username=admin@email.com&password=admin123&grant_type=password' "http://localhost:8000/oauth/token"
~~~

### Find games with pagination

1.step
~~~
mygamespagination='http://localhost:8000/api/restrict/games?page=0&size=3'
~~~
2.step
~~~
curl -H "Content-type: application/json" -H "Authorization: Bearer your_token" $mygamespagination | jq '.'
~~~

### Find game by identifier

~~~
curl -H "Content-type: application/json" -H "Authorization: Bearer your_token" http://localhost:8000/api/restrict/games/1 | jq

curl -H "Content-type: application/json" -H "Authorization: Bearer your_token" http://localhost:8000/api/restrict/games/2 | jq

curl -H "Content-type: application/json" -H "Authorization: Bearer your_token" http://localhost:8000/api/restrict/games/3 | jq
~~~

### Error: Not Found or Not Owner

~~~ 
curl -H "Content-type: application/json" -H "Authorization: Bearer your_token" http://localhost:8000/api/restrict/games/4 | jq
~~~

### Create a new user
...

### Create a new game
...


