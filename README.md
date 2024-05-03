# chat-manager
This app handles chat management

# Running Docker Image
1. Run docker-compose.yml
2. 

# Starting the app

1. Start up docker-compose-localstack.yml
2. Publish the ports using:
   docker run -p 4566:4566 localstack/localstack
3. Run init-dynamodb-localstack.sh
4. Start the application by running ChatManagerApplication

# Running Integration Tests

1. Start up docker-compose-localstack.yml
2. Publish the ports using:
   docker run -p 4566:4566 localstack/localstack   
3. Run init-dynamodb-localstack.sh
4. Run the tests

# Documentation

Once app is running in the documentation can be accessed
via: http://localhost:8082/swagger-ui/index.html
