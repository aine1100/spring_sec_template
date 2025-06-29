
```markdown
# 🌟 Spring Security JWT Template

A robust and scalable Spring Boot template for implementing **JWT-based authentication** with **refresh tokens**, built using **Spring Security**. Perfect for securing RESTful APIs.

---

## 📚 Table of Contents

- [✨ Features](#-features)
- [📁 Project Structure](#-project-structure)
- [🛠️ Prerequisites](#-prerequisites)
- [⚙️ Setup Instructions](#-setup-instructions)
- [🔑 Environment Variables](#-environment-variables)
- [🌐 API Endpoints](#-api-endpoints)
- [📘 Usage Guide](#-usage-guide)
- [🛡️ Security Considerations](#-security-considerations)
- [🤝 Contributing](#-contributing)
- [📜 License](#-license)

---

## ✨ Features

- 🔐 **JWT Authentication** — Stateless authentication with JSON Web Tokens.
- 🔄 **Refresh Tokens** — Automatically renew access tokens using refresh tokens.
- 🛡️ **Spring Security Integration** — Industry-standard authentication & authorization.
- 🔑 **BCrypt Password Hashing** — Strong password protection using hashing.
- 🌍 **CORS Ready** — Integrate with any frontend seamlessly.
- 🗃️ **Database Support** — JPA with MySQL/PostgreSQL and token persistence.
- 📦 **.env File Support** — Use `dotenv-java` for clean environment configs.

---

## 📁 Project Structure

```

spring-security-jwt-template/
├── src/
│   ├── main/
│   │   ├── java/com/backend/irire/
│   │   │   ├── controller/       # API controllers (e.g., AuthController)
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   ├── model/            # Entities (User, RefreshToken)
│   │   │   ├── repository/       # JPA Repositories
│   │   │   ├── service/          # Business Logic
│   │   │   ├── security/         # JWT & Spring Security setup
│   │   │   └── filter/           # JWT filter chain
│   └── resources/
│       └── application.properties
├── .env                         # Local environment configuration
├── pom.xml                      # Maven configuration
├── .gitignore                   # Ignored files
└── README.md                    # This file

````

---

## 🛠️ Prerequisites

| Tool     | Version            |
|----------|--------------------|
| Java     | 17+                |
| Maven    | 3.x                |
| Database | MySQL / PostgreSQL |
| IDE      | IntelliJ / VS Code |
| Git      | Latest             |

---

## ⚙️ Setup Instructions

### 1. 📥 Clone the Repository

```bash
git clone https://github.com/aine1100/spring_sec_template.git
cd irire
````

### 2. 🧩 Install Dependencies

```bash
mvn clean install
```

### 3. 🛢️ Configure the Database

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=${DATABASE_URL:jdbc:mysql://localhost:3306/yourdb}
spring.datasource.username=${DATABASE_USERNAME:root}
spring.datasource.password=${DATABASE_PASSWORD:secret}
spring.jpa.hibernate.ddl-auto=update
```

### 4. 🗝️ Set Up Environment Variables

Create a `.env` file in the project root:

```env
JWT_SECRET=your-256-bit-secret-key
JWT_ACCESS_TOKEN_EXPIRATION=900000
JWT_REFRESH_TOKEN_EXPIRATION=604800000
DATABASE_URL=jdbc:mysql://localhost:3306/yourdb
DATABASE_USERNAME=root
DATABASE_PASSWORD=secret
```

### 5. 🚀 Start the Application

```bash
mvn spring-boot:run
```

App will be available at: [http://localhost:8080](http://localhost:8080)

---

## 🔑 Environment Variables

| Variable                       | Description                  | Example                             |
| ------------------------------ | ---------------------------- | ----------------------------------- |
| `JWT_SECRET`                   | Secret key for JWT signing   | your-256-bit-secret-key             |
| `JWT_ACCESS_TOKEN_EXPIRATION`  | Access token expiry (in ms)  | 900000                              |
| `JWT_REFRESH_TOKEN_EXPIRATION` | Refresh token expiry (in ms) | 604800000                           |
| `DATABASE_URL`                 | JDBC connection string       | jdbc\:mysql://localhost:3306/yourdb |
| `DATABASE_USERNAME`            | Database username            | root                                |
| `DATABASE_PASSWORD`            | Database password            | secret                              |

> 🔒 `.env` is loaded using `dotenv-java` and excluded via `.gitignore`.

---

## 🌐 API Endpoints

| Method | Endpoint                  | Description               | Request Body                                     |
| ------ | ------------------------- | ------------------------- | ------------------------------------------------ |
| POST   | `/api/auth/register`      | Register a new user       | `{ email, password, firstName, lastName, role }` |
| POST   | `/api/auth/login`         | Login and get tokens      | `{ email, password }`                            |
| POST   | `/api/auth/refresh-token` | Refresh access token      | `{ refreshToken }`                               |
| POST   | `/api/auth/logout`        | Logout and revoke refresh | `{ refreshToken }`                               |

### 🧪 Example: Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john.doe@example.com", "password":"password123"}'
```

**Sample Response**

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "123e4567-e89b-12d3-a456-426614174000"
}
```

---

## 📘 Usage Guide

1. **Register a New User** → `POST /api/auth/register`
2. **Login** → `POST /api/auth/login`
   Use `Authorization: Bearer <accessToken>` in headers.
3. **Refresh Token** → `POST /api/auth/refresh-token`
4. **Logout** → `POST /api/auth/logout`

---

## 🛡️ Security Considerations

* 🔐 Keep `JWT_SECRET` secure in `.env` (never expose publicly).
* 🌐 Always use **HTTPS** in production.
* 🔁 Enable **Refresh Token Rotation** to prevent misuse.
* 🚫 Add **Rate Limiting** to login endpoints.
* ✅ Enforce **Strong Password Policies** at registration.

---

## 🤝 Contributing

We welcome contributions from the community! 🚀

```bash
# Fork the repo
# Create a feature branch
git checkout -b feature/your-feature

# Commit your changes
git commit -m "Add your feature"

# Push to GitHub
git push origin feature/your-feature
```

Then open a **Pull Request** with a clear description.

---

## 📜 License

This project is licensed under the [MIT License](LICENSE).

---

💬 **Questions or Suggestions?**
Open an issue or start a discussion — let’s build secure APIs together!

```


