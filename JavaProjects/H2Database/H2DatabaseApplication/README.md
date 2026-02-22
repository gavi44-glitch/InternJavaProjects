# CRUD SERVICES + H2 Database
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

## 🗄 Konfigurasi Database H2 
Project ini menggunakan H2 File database dengan konfigurasi berikut 
```
spring.datasource.url=jdbc:h2:file:./data/mydb
spring.datasource.username=
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

## 🚀 Cara Mengakses Endpoints 
- Create user     : POST http://localhost:8080/users/create
- Read all user   : GET  http://localhost:8080/users/read
- Find user by id : GET http://localhost:8080/users/{id}
- Update user     : PATCH http://localhost:8080/users/updateUser/{id}
- Delete user     : DELETE http://localhost:8080/users/deleteUser/{id}

## 📂 Struktur Arsitektur
Controller → Service → DAO → EntityManager → H2 Database
- Controller   : untuk menerima HTTP request
- Service      : menampung logika bisnis dan batasan transaction 
- DAO          : untuk mengakses persistence (JPA / EntityManager)
- H2           : menyimpan data
