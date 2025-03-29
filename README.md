# Spring-Boot-application
The objective of this assignment is to assess your proficiency in Next.js and Spring Boot development. You will be tasked with creating a tiny Spring Boot application with a filter that performs custom authentication based on the presence of a specific header in the HTTP request. 

# Spring Boot Authentication API

**Name:** Macharla Bala Rangarao  
**Email:** balunani25@gmail.com

## Version
- Spring Boot: 3.4.4
- Java: 21

## Setup

```bash
cd backend
./mvnw spring-boot:run
```

API will run at `http://localhost:8080`

## Endpoints
- `POST /post` - Create post (Requires header `PinggyAuthHeader`)
- `GET /list` - List all posts

---
