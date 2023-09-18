
# Weather Data API

## Table of Contents

- [Introduction](#introduction)
- [Application Requirements](#application-requirements)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
    - [API Endpoints](#api-endpoints)
    - [Example Queries](#example-queries)
- [Improvements](#improvements)


## Introduction

The Weather Data API is a RESTful service designed to receive and query weather metrics from various sensors. It allows users to submit new metric values and retrieve aggregated data based on specific queries. This document provides an overview of the application, its requirements, and instructions on how to get started.

## Application Requirements

The Weather Data API serves as a tool for managing weather metrics from sensors. Here are the primary requirements of the application:

- **Metric Submission**: The application can receive new metric values from sensors via API calls.

- **Data Querying**: Users can query sensor data based on various criteria, including:
    - Selection of one or more sensors (or all sensors).
    - Specification of the metrics (e.g., temperature and humidity) for which the application should return the average value.
    - Choice of statistic for the metric, such as minimum, maximum, sum, or average.
    - Selection of a date range (between one day and a month). If no date range is specified, the latest data is queried.

- **Example Query**: The application supports queries like "Give me the average temperature and humidity for sensor 1 in the last week."


## Getting Started

### Prerequisites

To simplify application running, the repository contains a docker-compose file to run application and database containers
Before running the Weather Data API, make sure you have the following prerequisites installed:

- Docker and docker-compose
- Java 17 for building the jar file

### Installation

Follow these steps to install and run the Weather Data API:

1. Clone the repository:

   ```bash
   git clone https://github.com/your-repo-url.git
   ```
2. Build jar file:

   ```bash
   cd ./weather_service
   ./gradlew clean build 
   ```

3. To run tests :

   ```bash
   cd ./weather_service
   ./gradlew clean test 
   ```  
   
4. Run docker compose :

   ```bash
   cd ..
   docker-compose up 
   ```
5. After container running you can run a script to populate/querying  the database via the application

   ```bash
   source populate_db.sh 
   ```
    
## Usage

### API Endpoints

The Weather Data API provides the following endpoints:

- GET /weather/metrics: Query sensor data based on specified criteria.
  For detailed API documentation, refer to API Documentation (http://localhost:8528/doc/swagger-ui/index.html) .
- GET /weather/metrics/statistics: Query sensor data statistics based on specified criteria.
For detailed API documentation, refer to API Documentation (http://localhost:8528/doc/swagger-ui/index.html).
- POST /weather/metrics:  Submit new metric values from sensors.


You can view database data in mongo express :
```bash
http://localhost:8048/db/weather_db/
```

### Example Queries
Here are some example queries you can perform using the API:

- Example : Retrieve the average temperature and humidity for sensor (ID = "f0d17b31-ea1b-49a1-9d47-d4efdb5e9ea9")  between ("2023-06-01T01:00:00Z" and "2023-06-30T01:00:00Z" ) :

```bash
curl -X GET http://localhost:8528/weather/metrics/statistics?metricNames=humidity,temperature&start_date=2023-09-01T01:00:00Z&end_date=2023-09-10T01:00:00Z&statisticNames=average&sensorIds=8802b29e-6cf6-406e-9bb5-2378ccca1de5
  ```



## Improvements
- Add verification to avoid duplicated metrics saving , and to verify metrics values range 
- Add package assembler to isolate and clarify the build of resources in api responses (the assember help also if we had to check user permissions or hide attributes in responses)
- Add a resource for every metric
- Due to time constraints, I've tried to set up test examples for each brick: 'controller', 'repository', 'service'. So adding more tests: 
  - testing all service, controller, service functions 
  - coverage testing
- For a security matter it's better to hide database user/password :
  - setting a role if we connect to a db in aws 
  - setting  user/password in env variables when building image

