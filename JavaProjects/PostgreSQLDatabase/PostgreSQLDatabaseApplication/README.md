# CRUD SERVICES + PostgreSQL Database
Project ini membuat REST API sederhana menggunakan spring boot 

# Features 
1. Create User
2. Read All user
3. Find user by id
4. Update user (found by id)
5. Delete user (found by id)

## 🚀 Cara Menjalankan Project
## ✅— Lewat IDE (IntelliJ / VS Code)

1. Buka project `demo`
2. Klik **Build Project** sampai folder `target/` muncul
3. Jalankan file:
DemoApplication.java
4. Tunggu sampai muncul di console:
Tomcat started on port(s): 8080
Berarti aplikasi sudah berjalan ✅

## 🗄 Konfigurasi Database PostgreSQL
Project ini menggunakan PostgreSQL database dengan konfigurasi berikut 
```
spring.datasource.url=jdbc:postgresql://<host>:<port>/<database>?currentSchema=<schema>
spring.datasource.username=<db_username>
spring.datasource.password=<db_password>
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## 🚀 Cara Mengakses Endpoints 
- Create user     : POST http://localhost:8080/users/create
- Read all user   : GET  http://localhost:8080/users/read
- Find user by id : GET http://localhost:8080/users/{id}
- Update user     : PATCH http://localhost:8080/users/updateUser/{id}
- Delete user     : DELETE http://localhost:8080/users/deleteUser/{id}

## 📂 Struktur Arsitektur
Controller → Service → DAO → JPA (EntityManager) → PostgreSQL
- Controller : untuk menerima HTTP request
- Service    : menampung logika bisnis dan batasan transaction
- DAO        : mengakses persistence layer (JPA / EntityManager)
- JPA        : abstraction layer untuk ORM
- PostgreSQL : relational database sebagai tempat menyimpan data
