#!/bin/bash

echo "Hello in weather app!"


echo "Populating database..."


requests=(
    '{"device_id": "08f21dfd-3b6c-4b9a-b98f-0451f6e1a887", "timestamp": "2023-06-15T12:30:00Z", "temperature": 25, "humidity": 50, "windSpeed": 10}'
    '{"device_id": "b0d593f9-220e-407a-9ca3-6a2207043ac2", "timestamp": "2023-07-05T15:45:00Z", "temperature": 28, "humidity": 45, "windSpeed": 15}'
    '{"device_id": "e5c15c73-172c-4f7e-b83b-8ee7d6df8b54", "timestamp": "2023-08-20T09:15:00Z", "temperature": 22, "humidity": 60, "windSpeed": 8}'
    '{"device_id": "79ab9d7d-59b7-4c56-a4c5-70177d1890d3", "timestamp": "2023-06-30T18:20:00Z", "temperature": 30, "humidity": 40, "windSpeed": 12}'
    '{"device_id": "c37ec7ae-1ae5-42eb-b3d2-8f03e0b1c82d", "timestamp": "2023-09-10T14:00:00Z", "temperature": 27, "humidity": 55, "windSpeed": 14}'
    '{"device_id": "a8f68f6b-17cb-4f32-8dca-2516b2e86a85", "timestamp": "2023-07-12T17:30:00Z", "temperature": 26, "humidity": 48, "windSpeed": 11}'
    '{"device_id": "d2a32a2f-9eaa-4f23-bc8b-5c6a91fc1f03", "timestamp": "2023-08-05T08:45:00Z", "temperature": 23, "humidity": 65, "windSpeed": 9}'
    '{"device_id": "f0d17b31-ea1b-49a1-9d47-d4efdb5e9ea9", "timestamp": "2023-06-25T13:55:00Z", "temperature": 29, "humidity": 42, "windSpeed": 13}'
    '{"device_id": "f0d17b31-ea1b-49a1-9d47-d4efdb5e9ea9", "timestamp": "2023-06-25T13:55:00Z", "temperature": 32, "humidity": 42, "windSpeed": 13}'
    '{"device_id": "f0d17b31-ea1b-49a1-9d47-d4efdb5e9ea9", "timestamp": "2023-06-26T13:55:00Z", "temperature": 15, "humidity": 42, "windSpeed": 13}'
    '{"device_id": "f0d17b31-ea1b-49a1-9d47-d4efdb5e9ea9", "timestamp": "2023-06-27T13:55:00Z", "temperature": 16, "humidity": 42, "windSpeed": 13}'
    '{"device_id": "f0d17b31-ea1b-49a1-9d47-d4efdb5e9ea9", "timestamp": "2023-06-28T13:55:00Z", "temperature": 18, "humidity": 42, "windSpeed": 13}'
    '{"device_id": "f0d17b31-ea1b-49a1-9d47-d4efdb5e9ea9", "timestamp": "2023-07-28T13:55:00Z", "temperature": 11, "humidity": 42, "windSpeed": 13}'
    '{"device_id": "7c04e702-2c0a-4374-8999-769df85e89c0", "timestamp": "2023-07-20T10:10:00Z", "temperature": 24, "humidity": 58, "windSpeed": 7}'
    '{"device_id": "93740fb6-8dbd-45a7-87d5-472a6d2eae02", "timestamp": "2023-08-15T16:20:00Z", "temperature": 31, "humidity": 38, "windSpeed": 16}'
    '{"device_id": "e56d8d17-7519-4da0-9a5c-04ed0c1c7f04", "timestamp": "2023-06-10T11:40:00Z", "temperature": 27, "humidity": 47, "windSpeed": 14}'
    '{"device_id": "6c735ec2-19f9-4b45-8775-89372d85297b", "timestamp": "2023-07-28T19:00:00Z", "temperature": 23, "humidity": 62, "windSpeed": 10}'
    '{"device_id": "1bfcd27a-0e36-4ef6-b97c-81d5b7764f84", "timestamp": "2023-08-25T07:15:00Z", "temperature": 30, "humidity": 44, "windSpeed": 12}'
    '{"device_id": "62e0e6eb-4305-4371-9a17-7b10e66a61a7", "timestamp": "2023-09-05T14:50:00Z", "temperature": 26, "humidity": 53, "windSpeed": 9}'
    '{"device_id": "3e2ecf4b-9ad1-47a0-8705-7c09546a3e5e", "timestamp": "2023-06-20T16:05:00Z", "temperature": 28, "humidity": 46, "windSpeed": 11}'
    '{"device_id": "ac8d2926-7e2a-4aa5-9573-e1e123625c9f", "timestamp": "2023-07-15T22:25:00Z", "temperature": 25, "humidity": 51, "windSpeed": 13}'
)


# API endpoint
api_url="http://localhost:8528/weather/metrics"

# Loop through the request payloads and send requests
for ((i = 0; i < ${#requests[@]}; i++)); do
    request_data=${requests[$i]}
    
    # Send the POST request and capture the response
    response=$(curl -X POST $api_url -H 'Content-Type: application/json' -d "$request_data")

    # Print the response
    echo "Response #$((i+1)):"
    echo "$response"
    echo
done



echo -e "\nDatabase population ends...\n"



echo -e "\n--- Example 1 ----\n"
echo "Request :  Retrieve the average temperature and humidity for sensor (ID = "f0d17b31-ea1b-49a1-9d47-d4efdb5e9ea9") between ("2023-06-01T01:00:00Z" and "2023-06-01T01:00:00Z" ) "

response=$(curl -X GET "$api_url/statistics?metricNames=humidity,temperature&start_date=2023-06-01T01:00:00Z&end_date=2023-06-30T01:00:00Z&statisticNames=average&sensorIds=f0d17b31-ea1b-49a1-9d47-d4efdb5e9ea9" )

# Print the response
echo "Response :"
echo "$response"

echo -e "\n------------------\n"
echo -e "\n"


echo -e "\n--- Example 2 ----\n"
echo "Request :  Retrieve the max temperature and humidity for sensor (ID = "f0d17b31-ea1b-49a1-9d47-d4efdb5e9ea9") for last 1000 metrics "

response=$(curl -X GET "$api_url/statistics?metricNames=humidity,temperature&statisticNames=max&sensorIds=f0d17b31-ea1b-49a1-9d47-d4efdb5e9ea9" )

# Print the response
echo "Response :"
echo "$response"

echo -e "\n------------------\n"
echo -e "\n"



echo -e "\n--- Example 3 ----\n"
echo "Request :  Retrieve the metrics of type temperature and humidity for sensor (ID = "f0d17b31-ea1b-49a1-9d47-d4efdb5e9ea9")between ("2023-06-01T01:00:00Z" and "2023-06-01T01:00:00Z" )"

response=$(curl -X GET "$api_url?metricNames=humidity,temperature&sensorIds=f0d17b31-ea1b-49a1-9d47-d4efdb5e9ea9&start_date=2023-06-01T01:00:00Z&end_date=2023-06-30T01:00:00Z" )

# Print the response
echo "Response:"
echo "$response"

echo -e "\n------------------\n"
echo -e "\n"