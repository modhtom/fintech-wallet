# **Fintech Wallet – Round-Up Investment Platform**

## **Overview**

The **Fintech Wallet** is a Spring Boot-based application that allows users to:

* Create accounts and manage balances.
* Automatically "round up" spare change from transactions.
* Invest accumulated round-ups into a portfolio.
* Track all transactions and portfolio growth in real time.

This project demonstrates **backend development mastery** using **Spring Boot**, **PostgreSQL**, and **REST API best practices**.
It’s designed to simulate a real-world fintech product that could be deployed in production.

---

## **Features**

* **User Management** – Create, update, and delete user accounts.
* **Bank Account Linking** – Connect multiple accounts to a single user.
* **Transaction Tracking** – Record and view spending transactions.
* **Round-Up Calculation** – Automatically collect spare change from transactions.
* **Investment Portfolio** – Store and track investments from round-ups.
* **Batch Processing** – Process round-ups in batches for investment.
* **API-First Design** – Fully documented REST API.

---

## **Tech Stack**
- **Java 21**
- **Spring Boot 3.x**
- **PostgreSQL** 
- **Spring Data JPA**
- **Lombok**
- **Springdoc OpenAPI**
- **Maven**

---

## **Database Schema**

![Database Diagram](docs/db_schema.png)
*(See `src/main/resources/data.sql` for full schema.)*

---

## **API Endpoints**

Base URL: `http://localhost:8080/api/`

For complete API details, run the application and visit:

```
http://localhost:8080/swagger-ui.html
```

---

## **Installation & Setup**

### **1. Clone the repository**

```bash
git clone https://github.com/modhtom/fintech-wallet.git
cd fintech-wallet
```

### **2. Configure PostgreSQL**

* Install PostgreSQL (if not already installed).
* Create the database:

```bash
createdb fintech_wallet
```

* Update `src/main/resources/application.properties` with your PostgreSQL credentials.

### **3. Run Database Scripts**

```bash
psql -U postgres -d fintech_wallet -f src/main/resources/schema.sql
psql -U postgres -d fintech_wallet -f src/main/resources/data.sql
```

### **4. Run the application**

```bash
mvn spring-boot:run
```
---

## TODO
- Email notifications for investments
- Integration with real bank APIs
---


## **License**

This project is licensed under the MIT License.
