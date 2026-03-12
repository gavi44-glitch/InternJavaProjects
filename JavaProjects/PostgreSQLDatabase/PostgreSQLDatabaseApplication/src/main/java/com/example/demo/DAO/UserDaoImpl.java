package com.example.demo.DAO;

import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

        /*
        NOTE : BISA JUGA KALO MAU CREATE NATIVE QUERY
            return entityManager
                    .createNativeQuery("SELECT * FROM Users", User.class)
                    .getResultList();
         */

    }

    @Override
    public User login(String userID, String userPass) {

        try {
            return entityManager
                    .createQuery("SELECT u FROM User u where u.userID =:userID AND u.userPass =:userPass", User.class)
                    .setParameter("userID", userID)
                    .setParameter("userPass", userPass)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User findUser(String userID){

        return entityManager.find(User.class, userID);


        /*
                    return entityManager
                    .createQuery("SELECT u FROM User u WHERE u.id =:id",User.class)
                    .setParameter("id",id)
                    .getSingleResult();
         */
    }

    @Override
    public User updatedUsername(String userID, Map<String, Object> updates){

        User updatedData = entityManager.find(User.class,userID);


        if(updatedData == null){
            throw new RuntimeException("User not found!");
        }

        if(updates.containsKey("userName")){
            updatedData.setUserName((String) updates.get("userName"));
        }

        return updatedData;
           /*
          INI cara mengambil entity berdasarkan PK di JPA
         jadi syntax aslinya itu  User user = entityManager.find(User.class, userID);
         artinya dia mengambil entity user berdasarkan primary key = userID

         jadi kalo sudah ada datanya di persistence context, dia return object yang sama
         tapi kalo belum ada, maka hibernate akan generate SQL untuk dikirim ke db
         khusus untuk cari field bedasarkan PK

         */
    }

    @Override
    public User deleteUser(String userID){
        User user = entityManager.find(User.class, userID);

        if(user!=null){
            entityManager.remove(user);
        }
        return user;
    }

}
