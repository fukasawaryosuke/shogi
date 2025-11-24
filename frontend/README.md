# frontend (Vite + React)

Cloudflare Pagesでホスティング

## 開発

```bash
npm install
npm run dev
```

## ビルド

```bash
npm run build
```

出力は `dist/` ディレクトリに生成されます。

## デプロイ

Cloudflare Pagesに自動デプロイ：

- `main` ブランチへのpush時に自動ビルド＆デプロイ
- フロントエンド（`frontend/`）の変更のみをトリガー

### 手動デプロイ

```bash
npm run deploy
```

## API 呼び出し

- 開発時: `http://localhost:8080/api` にプロキシ（vite.config.mjs参照）
- 本番環境: Cloudflare PagesからバックエンドAPI（Spring Boot）へのCORSリクエスト

CORS設定をSpring Bootで行う必要があります。
