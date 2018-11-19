## Creating docker image
```bash
mvn clean install
docker build -f Dockerfile -t cleaning-service .
```
## pushing docker image
```
docker commit e7e97ddf6e6b marcinus/cleaning-service:0.1
docker push marcinus/cleaning-service:0.1
```

## pulling docker image
```
docker pull marcinus/cleaning-service:0.1
```

## running downloaded image
```
docker run -p 8081:8081 imageId 
```