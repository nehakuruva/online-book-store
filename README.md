# Online Book Store

A full-stack starter project with **React + Vite** frontend and **Java Spring Boot** backend.
It uses an H2 file database by default, so MySQL is not required.

## Features included
- Home page with book catalogue, categories, search and recommendations
- Register and login (demo starter authentication)
- Add to cart, change quantity, remove items
- Wishlist and move wishlist items to cart
- Checkout / place order and order history
- REST API with Spring Boot and H2 persistence
- Responsive UI

> Academic-project starter: authentication is intentionally simple for local learning. Before public deployment, replace the demo token approach with Spring Security + BCrypt + JWT, add validation and authorization, and configure production database/secrets.

## Requirements
- Java 17+
- Maven 3.9+
- Node.js 20+ (npm included)

## Run backend
Open a terminal in `backend`:
```bash
mvn spring-boot:run
```
API runs at `http://localhost:8080`. H2 console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:file:./data/bookstoredb`
- Username: `sa`
- Password: (blank)

## Run frontend
Open another terminal in `frontend`:
```bash
npm install
npm run dev
```
Open the local URL printed by Vite (usually `http://localhost:5173`).

## Demo flow
1. Register a new account from the Login button.
2. Browse books, add to cart/wishlist.
3. Open Cart and place an order.
4. Open My Orders to see previous orders.

## Project structure
```text
online-book-store/
  backend/   Spring Boot REST API + H2
  frontend/  React + Vite app
```
