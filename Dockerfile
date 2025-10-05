FROM openjdk:17-jdk-slim

# Mavenをインストール
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

# プロジェクトファイルをコピー
COPY pom.xml .
COPY src/ ./src/

# 依存関係をダウンロードしてコンパイル
RUN mvn clean compile

# コンテナを起動して待機
CMD ["tail", "-f", "/dev/null"]
