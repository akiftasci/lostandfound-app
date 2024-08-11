# Lost & Found Application

This is a Spring Boot application for managing lost and found items. The application allows administrators to upload lost item information from a PDF file, while users can view and claim these items.

## Features

- **Admin Endpoints:**
    - Upload and store lost items from a PDF file.
    - Retrieve all lost items claimed by users.

- **User Endpoints:**
    - Retrieve all lost items.
    - Claim lost items by providing item details.

## Technology Stack

- Java 17
- Spring Boot
- Maven
- JPA/Hibernate (H2 Database)
- Docker
- JUnit for testing

## Prerequisites

- **Java 17:** Ensure that Java 17 is installed on your system.
- **Maven:** Maven is required to build the project.
- **Docker:** Docker is required to run the application inside a container.

## Getting Started

### 1. Clone the Repository

bash
git clone https://github.com/akiftasci/lostandfound-app
cd lost-and-found-app


### 2. Build the Project

Use Maven to build the project and generate a JAR file.

bash
./mvnw clean package


### 3. Running the Application

#### Locally

You can run the application locally using Maven:

bash
./mvnw spring-boot:run


The application will start on `http://localhost:8080`.

#### Using Docker

Alternatively, you can run the application inside a Docker container.

1. **Build the Docker Image:**

   bash
   docker build -t your-dockerhub-username/lost-and-found-app:lastest .


2. **Run the Docker Container:**

   bash
   docker run -p 8080:8080 akiftasci/lost-and-found-app:lastest


The application will be accessible at `http://localhost:8080`.

### 4. API Endpoints

#### **Admin Endpoints**

- **Upload Lost Items from PDF:**

  http
  POST /admin/upload
  Content-Type: multipart/form-data


- Description: Upload a PDF file containing lost items.
- Example: Use Postman to upload the PDF file.

- **Get All Claimed Items:**

  http
  GET /admin/get-claims


- Description: Retrieve a list of all claimed items and the users who claimed them.

#### **User Endpoints**

- **Get All Lost Items:**

  http
  GET /user/lost


- Description: Retrieve a list of all lost items.

- **Claim a Lost Item:**

  http
  POST /user/claim
  Content-Type: application/json


- Description: Claim a lost item by providing the item details.
- Example Request Body:

json
{
"lostItemId": 1,
"userId": 1001,
"quantity": 1
}


### 5. Running Tests

You can run unit and integration tests using Maven:

bash
./mvnw test


### 6. Sample PDF File for Testing

A sample PDF file for testing the upload functionality is located in the `src/test/resources/sample.pdf` directory.

### 7. Troubleshooting

- **Common Issues:**
    - Ensure Java 17 is installed and properly configured.
    - Ensure Docker is running before attempting to build or run the Docker container.
    - Check that the `target/` directory contains the JAR file after building.



### Akif Tasci
