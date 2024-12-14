# Trade X - Live Stock Trading App

**Trade X** is a cutting-edge live stock trading application built with a robust backend powered by Spring Boot and a dynamic frontend crafted with React. It leverages WebSockets to provide real-time updates and seamless user interactions.

---

## Features

### 1. Real-Time Stock Updates
- **Periodic Updates:** Every 20 seconds, the latest stock updates are broadcast to all connected users.
- **Dynamic Updates on Purchase:** Whenever a user purchases a stock, detailed updates about that stock (e.g., remaining stock count, percentage of people who purchased) are immediately broadcast to all other users.

### 2. Multi-Instance Synchronization
- Users can log in on two or more instances of the website simultaneously.
- All instances stay synchronized, showing live updates in the preview panel.

---

## Tech Stack

- **Backend:** Spring Boot
- **Frontend:** React
- **Real-Time Communication:** WebSockets

---

## Getting Started

### Prerequisites
- Java (for Spring Boot backend)
- Node.js and npm (for React frontend)

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/<your-username>/trade-x.git
   ```
2. Navigate to the backend folder and start the Spring Boot server:
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```
3. Navigate to the frontend folder and start the React application:
   ```bash
   cd frontend
   npm install
   npm start
   ```

---

## Usage

1. Open multiple browser windows or devices and log in to your account.
2. Purchase stocks and observe the real-time updates in all instances.
3. Monitor live stock updates in the preview panel.

---

