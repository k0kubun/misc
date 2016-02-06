# Notes

```bash
docker pull training/sinatra
docker run -it training/sinatra /bin/bash

docker build -t k0kubun/sinatra:v2 .
docker tag 5db5f8471261 k0kubun/sinatra:latest
docker login
docker push k0kubun/sinatra
```
