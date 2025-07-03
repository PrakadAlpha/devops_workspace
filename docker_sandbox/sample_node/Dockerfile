# -------- Stage 1: Prepare Code --------
FROM node:18-alpine AS builder

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

# -------- Stage 2: Production Image --------
FROM node:18-alpine

RUN apk add --no-cache curl

WORKDIR /app

COPY --from=builder /app ./

RUN npm install --only=production

EXPOSE 3000

HEALTHCHECK --interval=30s --timeout=30s --start-period=10s --retries=2 \
CMD curl -f http://localhost:3000/health || exit 1

CMD ["npm", "start"]
