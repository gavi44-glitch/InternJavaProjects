package com.example.demo.DAO;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDAO{

        @PersistenceContext
        private EntityManager entityManager;

        @Override
        public User create(User newUser){
            entityManager.persist(newUser);
            return newUser;
        }

        @Override
        public List<User> readAll(){
            //MENGGUNAKAN JPQL (Java Persistence Query Language)
            return entityManager
                    .createQuery("SELECT u FROM User u", User.class)
                    .getResultList();

//            //NOTE : BISA JUGA KALO MAU CREATE NATIVE QUERY
//            return entityManager
//                    .createNativeQuery("SELECT * FROM Users", User.class)
//                    .getResultList();
        }

        @Override
        public User findUser(String id){
//            return entityManager
//                    .createQuery("SELECT u FROM User u WHERE u.id =:id",User.class)
//                    .setParameter("id",id)
//                    .getSingleResult();
            return entityManager.find(User.class, id);
        }

        @Override
        public User updatedUsername(String id, Map<String, Object> updates){

            //INI cara mengambil entity berdasarkan PK di JPA
            // jadi syntax aslinya itu  User user = entityManager.find(User.class, id);
            // artinya dia mengambil entity user berdasarkan primary key = id

            // jadi kalo sudah ada datanya di persistence context, dia return object yang sama
            // tapi kalo belum ada, maka hibernate akan generate SQL untuk dikirim ke db
            // khusus untuk cari field bedasarkan PK

            User updatedData = entityManager.find(User.class,id);


            if(updatedData == null){
                throw new RuntimeException("User not found!");
            }

            if(updates.containsKey("userName")){
                updatedData.setUserName((String) updates.get("userName"));
            }

            return updatedData;
        }

        @Override
        public User deleteUser(String id){
            User user = entityManager.find(User.class, id);

            if(user!=null){
                entityManager.remove(user);
            }
            return user;
        }

}
