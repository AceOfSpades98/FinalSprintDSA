# 🌳 Binary Tree Builder Backend

A **Spring Boot** backend service for building, storing, and retrieving binary search trees.  
This service exposes REST API endpoints that the frontend can consume.

## 🚀 Features

- Accepts a string of integers and builds a **Binary Search Tree**.
- Stores tree structure and original input in a database.
- Returns JSON representation of the tree.
- Provides an endpoint to retrieve **previously saved trees**.
- Uses **JPA** with an embedded H2 database.

## 🛠️ Technologies

- **Java 17+**
- **Spring Boot**
- **Spring Data JPA**
- **H2 Database**
- **Maven**

## 📦 Installation

```bash
mvn install
mvn spring-boot:run
