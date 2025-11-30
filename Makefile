.PHONY: help up down shell build clean package run spring-run test teavm deploy

help:
	@echo "Maven開発用コマンド:"
	@echo "  make up  - コンテナ起動"
	@echo "  make down  - コンテナ停止"
	@echo "  make shell - シェル開く"
	@echo "  make build - コンパイル実行"
	@echo "  make run   - アプリケーション実行"
	@echo "  make test  - テスト実行"
	@echo "  make package - パッケージ作成"
	@echo "  make spring-run - Spring Boot実行"
	@echo "  make teavm - TeaVMでWebAssemblyにコンパイル"

up:
	docker compose up -d

down:
	docker compose down

shell:
	docker compose exec app bash

build:
	docker compose exec app mvn compile

clean:
	docker compose exec app mvn clean

package:
	docker compose exec app mvn -DskipTests package

run:
	docker compose exec app mvn exec:java

spring-run:
	docker compose exec app mvn spring-boot:run

test:
	docker compose exec app mvn test

teavm:
	docker compose exec app mvn clean compile
	docker compose exec app mvn org.teavm:teavm-maven-plugin:0.10.0:compile@wasm
	mkdir -p frontend/dist/wasm
	docker compose cp "app:/app/target/javascript/." frontend/dist/wasm/

deploy:
	./deploy.sh
