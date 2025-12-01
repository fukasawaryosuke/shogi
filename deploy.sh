#!/bin/bash
set -e

echo "Building wasm..."
make teavm

echo "Installing frontend dependencies..."
cd frontend
npm ci

echo "Building frontend..."
npm run build

echo "Build completed!"
echo "Deploy directory: frontend/dist"

echo "Deploying to Cloudflare Pages..."
cd ..
npx wrangler pages deploy ./frontend/dist --project-name=shogi-app
