# Stage 1: Base with Bun runtime
FROM oven/bun:1 AS base
WORKDIR /app

# Stage 2: Install dependencies
FROM base AS dependencies
COPY package.json bun.lockb* ./
RUN bun install --frozen-lockfile

# Stage 3: Development environment
FROM base AS development
COPY package.json bun.lockb* ./
COPY --from=dependencies /app/node_modules ./node_modules
COPY . .
EXPOSE 5173
CMD ["bun", "run", "dev", "--host"]
