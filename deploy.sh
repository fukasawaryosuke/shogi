#!/bin/bash
set -e

echo "Installing frontend dependencies..."
cd frontend
npm ci

echo "Building frontend..."
npm run build

echo "Building wasm..."
cd ..
make teavm

echo "Build completed!"
echo "Deploy directory: frontend/dist"
