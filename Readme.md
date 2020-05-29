docker run -d --name some-rabbit -p 5672:5672 -p 5673:5673 -p 15672:15672 rabbitmq:3-management
docker run -d -p 9411:9411 openzipkin/zipkin
docker pull docker.elastic.co/logstash/logstash:7.7.0