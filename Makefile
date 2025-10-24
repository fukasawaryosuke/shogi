.PHONY: help up down shell run build test

help:
	@echo "Maven開発用コマンド:"
	@echo "  make up  - コンテナ起動"
	@echo "  make down  - コンテナ停止"
	@echo "  make shell - シェル開く"
	@echo "  make build - コンパイル実行"
	@echo "  make run   - アプリケーション実行"
	@echo "  make test  - テスト実行"

up:
	docker-compose up -d

down:
	docker-compose down

shell:
	docker-compose exec app bash

build:
	docker-compose run --rm app mvn compile

run:
	docker-compose exec app mvn exec:java

clean:
	docker-compose run --rm app mvn clean compile

test:
	docker-compose exec app mvn test
