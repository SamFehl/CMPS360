# ---------- Build stage ----------
FROM golang:1.25.5 AS builder

# Set working directory inside the container
WORKDIR /app

# Copy go.mod and go.sum and download deps
COPY go.mod ./
RUN go mod download

# Copy the rest of the project files
COPY . .

# Build the Go binary
RUN go build -o main .

# ---------- Run stage ----------
FROM debian:bookworm-slim

WORKDIR /app

# Copy the compiled binary and assets from the builder
COPY --from=builder /app/main .
COPY --from=builder /app/templates ./templates
COPY --from=builder /app/static ./static

# The app listens on port 8080
EXPOSE 8080

# Start the app
CMD ["./main"]
