#!/bin/bash

# Default to dev environment if not specified
ENV=${1:-dev}

# Check if valid environment
if [[ "$ENV" != "dev" && "$ENV" != "test" && "$ENV" != "prod" ]]; then
  echo "Invalid environment: $ENV"
  echo "Usage: ./run.sh [dev|test|prod]"
  exit 1
fi

echo "Starting application in $ENV environment..."

# Copy the appropriate .env file
cp .env.$ENV .env

# Run with docker compose
docker compose down
docker compose up --build -d

echo "Application started successfully in $ENV environment!" 