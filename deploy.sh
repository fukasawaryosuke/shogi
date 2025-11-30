#!/bin/bash
set -e

echo "Installing frontend dependencies..."
cd frontend
npm ci

echo "Building frontend..."
npm run build

# WASMファイルは事前にビルドしてコミット済みと想定
if [ ! -f "dist/wasm/classes.wasm" ]; then
  echo "Warning: WASM files not found in dist/wasm/"
  echo "Run 'make teavm' locally before deploying"
fi

echo "Build completed!"
echo "Deploy directory: frontend/dist"
