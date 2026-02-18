## CRUD SERVICES 
Project ini membuat REST API sederhana menggunakan spring boot 

# Features 
1. Create User
2. Read All user
3. Find user by id
4. Update user (found by id)
5. Delete user (found by id)

# 🚀 Cara Menjalankan Project
# ✅— Lewat IDE (IntelliJ / VS Code)

1. Buka project `demo`
2. Klik **Build Project** sampai folder `target/` muncul
3. Jalankan file:
DemoApplication.java
4. Tunggu sampai muncul di console:
Tomcat started on port(s): 9092
Berarti aplikasi sudah berjalan ✅

# 🚀 Cara Mengakses Endpoints 
- Create user     : POST http://localhost:9092/api/users/create
- Read all user   : GET  http://localhost:9092/api/users/read
- Find user by id : GET http://localhost:9092/api/users/{id}
- Update user     : PUT http://localhost:9092/api/users/updateUser/{id}
- Delete user     : DELETE http://localhost:9092/api/users/deleteUser/{id}

