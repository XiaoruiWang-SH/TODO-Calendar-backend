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


case "$ENV" in
  dev)
    docker compose --project-name myapp_dev -f docker-compose-dev.yml  down
    docker compose --project-name myapp_dev -f docker-compose-dev.yml --env-file .env.dev up --build -d
    ;;
  prod)
    docker compose --project-name myapp_prod -f docker-compose-prod.yml  down
    docker compose --project-name myapp_prod -f docker-compose-prod.yml --env-file .env.prod up --build -d
    ;;
esac

echo "Application started successfully in $ENV environment!" 