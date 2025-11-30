#!/bin/bash
set -e

echo "Building frontend..."
cd frontend
npm run build

echo "Building wasm..."
cd ..
make teavm

echo "Build completed!"
echo "Deploy directory: frontend/dist"
