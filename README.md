# Spring Security Integration API

### ⚠️ The requests work correctly on ***Postman***, but since a Spring form is used, some minor configuration changes may be necessary.

## 📬 Postman Collection

You can explore and test all API endpoints directly using my public Postman collection:

[![Run in Postman](https://run.pstmn.io/button.svg)](https://www.postman.com/cryosat-technologist-94729418/spring-security/collection/rqmjkva/spring-security?action=share&source=copy-link&creator=34103070)


## 📖 Information

**Spring Security Integration API** is a comprehensive Spring Boot application that demonstrates advanced Spring Security integration patterns including multiple authentication mechanisms, JWT token management, OAuth2 integration, and Two-Factor Authentication (2FA). The project showcases real-world security implementations with role-based access control.

### Key Features:
- **Multiple Authentication Methods**: In-Memory, Basic Auth, JWT, OAuth2, and 2FA
- **JWT Authentication**: Stateless JWT token management with custom claims
- **OAuth2 Integration**: Google OAuth2 login support
- **Two-Factor Authentication**: Google Authenticator integration with QR code generation
- **Role-Based Access Control**: USER, ADMIN, and MODERATOR roles
- **Security Configuration**: Comprehensive security filters and configurations
- **Database Integration**: PostgreSQL with JPA/Hibernate / MySQL
- **Password Security**: BCrypt password encoding

### Authentication Methods:
1. **In-Memory Authentication**: Authentication users stored in application memory (used typically for testing or quick setups)
2. **Basic Authentication**: Traditional username/password with form login
3. **JWT Authentication**: Token-based stateless authentication
4. **OAuth2**: Google social login integration
5. **2FA**: Google Authenticator Based two-factor authentication

### Security Features:
- Role-based endpoint protection
- Custom JWT token validation
- OAuth2 social login
- Two-factor authentication setup and verification
- Password encryption using BCrypt
- Session management configuration

## 🚀 Endpoints Summary

| Method | URL | Description | Authentication | Roles | Response |
|--------|-----|-------------|----------------|-------|----------|
| **Public Endpoints** |  |  |  |  |  |
| GET | `/public` | Public endpoint | None | - | String |
| POST | `/publicBasic/createUser` | Create new user | None | - | User |
| **In-Memory Authentication Endpoints** |  |  |  |  |  |
| GET | `/private` | Private endpoint | Any Auth | Any Role | String |
| GET | `/private/user` | Private user endpoint | Any Auth | USER | String |
| GET | `/private/admin` | Private admin endpoint | Any Auth | ADMIN | String |
| GET | `/admin` | Admin endpoint | Any Auth | ADMIN | String |
| **Basic Authentication** |  |  |  |  |  |
| GET | `/privateBasic/**` | General private endpoint | Basic Auth | Any Role | String |
| GET | `/privateBasic/user` | User private endpoint | Basic Auth | USER | String |
| GET | `/privateBasic/admin` | Admin private endpoint | Basic Auth | ADMIN | String |
| GET | `/privateBasic/mod` | Moderator private endpoint | Basic Auth | MODERATOR | String |
| GET | `/publicBasic` | Public basic endpoint | None | - | String |
| **JWT Authentication** |  |  |  |  |  |
| POST | `/auth/addNewUser` | Alternative new user | None | - | User |
| POST | `/auth/generateToken` | Generate JWT token | None | - | JWT Token |
| GET | `/auth/user` | JWT User endpoint | JWT | USER | String |
| GET | `/auth/admin` | JWT Admin endpoint | JWT | ADMIN | String |
| **OAuth2 Endpoints** |  |  |  |  |  |
| GET | `/oauth2/` | OAuth2 logout page | None | - | String |
| GET | `/oauth2/google` | Google OAuth2 callback | OAuth2 | - | User Info |
| **Two-Factor Authentication** |  |  |  |  |  |
| GET | `/2fa-init` | Initialize 2FA setup | Form Auth | Any Role | HTML Page |
| GET | `/2fa-check` | 2FA verification page | Form Auth | Any Role | HTML Page |
| POST | `/verify-2fa` | Verify 2FA code | Form Auth | Any Role | Redirect |

## 🛠️ Technologies

- **Java 21** - Programming Language
- **Spring Boot 3.5.0** - Application Framework
- **Spring Security** - Authentication & Authorization
- **Spring Data JPA** - Data Access Layer
- **PostgreSQL** - Primary Database
- **MySQL** - Secondary Database for JWT Authentication
- **JWT (JJWT 0.12.6)** - Token Authentication
- **OAuth2** - Social Authentication
- **Google Authenticator** - Two-Factor Authentication
- **Lombok** - Code Generation
- **Thymeleaf** - Template Engine
- **Maven** - Dependency Management
- **Docker** - Containerization


## ⚙️ Configurations

### Database Configuration

```properties
spring.datasource.url=YOUR_DB_URL
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
spring.jpa.properties.hibernate.default_schema=YOUR_DB_SCHEMA
spring.jpa.hibernate.ddl-auto=update
```

### JWT Configuration

```properties
jwt.key=YOUR_JWT_SECRET_KEY
```

### OAuth2 Configuration

```properties
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
spring.security.oauth2.client.registration.google.scope=openid,profile,email
```

### Complete Application Properties

```properties
spring.application.name=Spring-Security

# Database Configuration
spring.datasource.url=YOUR_DB_URL
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
spring.jpa.properties.hibernate.default_schema=YOUR_DB_SCHEMA
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true

# Session Configuration
server.servlet.session.timeout=15s

# JWT Configuration
jwt.key=YOUR_JWT_SECRET_KEY

# OAuth2 Configuration
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
spring.security.oauth2.client.registration.google.scope=openid,profile,email
```

## 🚀 Running the Application

### Maven Run
```bash
cd Spring-Security
mvn clean install
mvn spring-boot:run
```

### Docker Run
```bash
cd Spring-Security
docker-compose up -d
```

## Docker Compose File
### MySQL Container used for JWT Authentication
```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8
    container_name: mysql-container
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: jwt12345
      MYSQL_DATABASE: JWT
      MYSQL_USER: user
      MYSQL_PASSWORD: jwt12345
    ports:
      - "3307:3306"  #The first one is the port accessed from outside and the other one is the image port.
    volumes:
      - mysql_data:/var/lib/mysql

volumes:
  mysql_data:
```

## 📖 API Usage Examples

### User Registration and JWT Authentication

1. **Register User**
```bash
curl -X POST http://localhost:8080/auth/addNewUser \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123",
    "name": "Test User",
    "authorities": ["ROLE_USER"]
  }'
```

2. **Generate JWT Token**
```bash
curl -X POST http://localhost:8080/auth/generateToken \
  -H "Content-Type: application/json" \
  -d '{"username": "testuser", "password": "password123"}'
```

3. **Access JWT Protected Endpoint**
```bash
curl -X GET http://localhost:8080/auth/user \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Basic Authentication

1. **Access Basic Auth Endpoint**
```bash
curl -X GET http://localhost:8080/privateBasic/user \
  -u username:password
```

2. **Create User via Basic Auth**
```bash
curl -X POST http://localhost:8080/publicBasic/createUser \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "password": "password123",
    "name": "New User",
    "authorities": ["ROLE_USER"]
  }'
```

### OAuth2 Authentication

1. **Initiate Google OAuth2 Login**
```
GET http://localhost:8080/oauth2/login/google
```

2. **Access OAuth2 Protected Resource**
```
GET http://localhost:8080/oauth2/google
```

### Two-Factor Authentication Flow

1. **Login with Form Authentication**
```
POST http://localhost:8080/login
Content-Type: application/x-www-form-urlencoded

username=testuser&password=password123
```

2. **Initialize 2FA Setup**
```
GET http://localhost:8080/2fa-init
```

3. **Verify 2FA Code**
```bash
curl -X POST http://localhost:8080/verify-2fa \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "code=123456"
```

## 🔧 Security Configuration Details

### Multiple Authentication Mechanisms

The application supports multiple authentication methods through different security filter chains:

1. **Basic Authentication**: Form-based login with session management
2. **JWT Authentication**: Stateless token-based authentication
3. **OAuth2**: Social login integration
4. **2FA**: Two-factor authentication with Google Authenticator

### Role-Based Access Control

- **ROLE_USER**: Access to user-specific endpoints
- **ROLE_ADMIN**: Access to admin-specific endpoints
- **ROLE_MODERATOR**: Access to moderator-specific endpoints

### JWT Token Configuration

- **Token Expiration**: 2 minutes (configurable)
- **Custom Claims**: Supports custom payload data
- **Signature Algorithm**: HS256
- **Token Validation**: Comprehensive validation including expiration checks

### Two-Factor Authentication

- **Google Authenticator Integration**: QR code generation for easy setup
- **Secret Key Management**: Secure storage of user-specific secret keys
- **Code Verification**: Real-time verification of 6-digit codes

## 🛡️ Security Features

### Password Security
- BCrypt password encoding
- Configurable password strength requirements

### Session Management
- Configurable session timeout (15 seconds default)
- Session creation policies for different authentication methods

### CORS Configuration
- Cross-origin resource sharing support
- Configurable allowed origins and methods

### Authentication Filters
- Custom JWT authentication filter
- OAuth2 authentication integration
- Form-based authentication support

## 🧪 Testing Different Authentication Methods

### Test Basic Authentication
```bash
# Test with correct credentials
curl -X GET http://localhost:8080/privateBasic/user -u testuser:password123

# Test with wrong credentials
curl -X GET http://localhost:8080/privateBasic/user -u testuser:wrongpassword
```

### Test JWT Authentication
```bash
# Generate token first
TOKEN=$(curl -X POST http://localhost:8080/auth/generateToken \
  -H "Content-Type: application/json" \
  -d '{"username": "testuser", "password": "password123"}')

# Use token to access protected endpoint
curl -X GET http://localhost:8080/auth/user \
  -H "Authorization: Bearer $TOKEN"
```

### Test Role-Based Access
```bash
# User trying to access admin endpoint (should fail)
curl -X GET http://localhost:8080/auth/admin \
  -H "Authorization: Bearer $USER_TOKEN"

# Admin accessing admin endpoint (should succeed)
curl -X GET http://localhost:8080/auth/admin \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

## 📋 Project Structure

```
src/main/java/com/MuharremAslan/
├── Config/
│   ├── CorsConfig.java
│   └── PasswordEncoderConfig.java
├── Controller/
│   ├── BasicAuthAdminController.java
│   ├── JwtController.java
│   ├── OAuth2Controller.java
│   ├── PrivateBasicAuthController.java
│   ├── PrivateController.java
│   ├── PublicBasicAuthController.java
│   ├── PublicController.java
│   └── TwoFAController.java
├── Core/
│   └── utils/
│       └── Google2FAUtils.java
├── Model/
│   ├── AuthRequest.java
│   ├── CreateUserRequest.java
│   ├── ROLE.java
│   └── User.java
├── Repository/
│   └── UserRepository.java
├── Security/
│   ├── JwtAuthFilter.java
│   ├── SecurityConfig.java
│   └── UserDetailsServiceImpl.java
└── Service/
    ├── JwtService.java
    ├── TwoFAService.java
    └── UserService.java
```

## 🔄 Authentication Flow Diagrams

### JWT Authentication Flow
1. User sends credentials to `/auth/generateToken`
2. Server validates credentials
3. Server generates JWT token with custom claims
4. User includes token in Authorization header
5. JWT filter validates token on each request
6. Access granted based on token validity and user roles

### 2FA Authentication Flow
1. User logs in with username/password
2. System checks if 2FA is configured
3. If not configured, redirect to `/2fa-init`
4. User scans QR code with Google Authenticator
5. User enters 6-digit code for verification
6. System validates code and grants access

### OAuth2 Authentication Flow
1. User initiates Google OAuth2 login
2. Redirected to Google authorization server
3. User grants permissions
4. Google redirects back with authorization code
5. Server exchanges code for access token
6. User information retrieved from Google
7. User authenticated and session created

## 🚨 Important Notes

- Configure database connection before starting the application
- Update application.properties with your database credentials
- Set up Google OAuth2 credentials in Google Cloud Console
- JWT secret key should be Base64 encoded and kept secure and can be generated `openssl rand -base64 32` in cmd as well
- 2FA requires users to have Google Authenticator app installed
- Different security configurations are available (commented in SecurityConfig.java)
- Session timeout is set to 15 seconds for demonstration purposes


## 📸 Spring Security Use Case Screenshots

<details>
<summary><strong>Click to view figures</strong></summary><br>

### 🔐 In-Memory Security

<details>
<summary>Show details</summary>

![in_memory_private_user](https://github.com/user-attachments/assets/c91f52bb-44ce-41ee-a446-f04693adb644)

![in-memory-private_admin](https://github.com/user-attachments/assets/d5e0741b-0ee5-49bb-be27-05aba3224dee)

</details>

---

### 🔑 Basic-Auth Security

<details>
<summary>Show details</summary>

![basic-mod](https://github.com/user-attachments/assets/a71a5549-a878-4830-b292-c129fa33f45b)


</details>

---

### 📦 JWT Security

<details>
<summary>Show details</summary>

![jwt-generate-token](https://github.com/user-attachments/assets/aff07031-f5b9-444d-a4bd-257fa52f3ee9)

![jwt-user](https://github.com/user-attachments/assets/a005801b-2187-4640-bd75-b447ec6c4dd8)

</details>

---

### 🌐 OAuth2 Security

<details>
<summary>Show details</summary>

![oauth](https://github.com/user-attachments/assets/dfeca7f2-6fea-4bee-adcb-895292624d90)
![oauth-success](https://github.com/user-attachments/assets/0396287c-eb1c-4b00-8973-5775e5e48f89)

</details>

---

### 🔐 2FA Security

<details>
<summary>Show details</summary>

![2fa-init-user](https://github.com/user-attachments/assets/f8ea863e-8420-46a8-ada2-fc3621210718)
![2fa-success](https://github.com/user-attachments/assets/98cdaf80-3b4e-4f1f-96a9-ff55fb72d2e5)
![authenticator](https://github.com/user-attachments/assets/bcf091c6-b9d3-41bb-a000-15d3f4c6a35c)


</details>

### 💾 Other


<details>
<summary>Show details</summary>

![create-user](https://github.com/user-attachments/assets/5b0c23b7-a34f-4210-ad41-93986a773696)


</details>


</details>



---
*This project demonstrates comprehensive Spring Security integration patterns, focusing on multiple authentication mechanisms, role-based access control, and modern security practices. Developed for Educational Purposes*
