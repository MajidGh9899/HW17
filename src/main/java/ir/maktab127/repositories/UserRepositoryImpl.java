package ir.maktab127.repositories;

import ir.maktab127.config.ApplicationContext;
import ir.maktab127.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.sql.SQLException;

public class UserRepositoryImpl implements UserRepository {
    
    private final ApplicationContext context ;

    public UserRepositoryImpl(ApplicationContext context ) {
        this.context = context;
    }
    
    @Override
    public void createUserTable() throws SQLException {

    }

    @Override
    public void registerUser(User user) throws SQLException {
        EntityManager entityManager = context.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        
        try {
            transaction.begin();
            entityManager.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new SQLException("Error registering user", e);
        }
    }

    @Override
    public User login(String username, String password) throws SQLException {
        EntityManager entityManager = context.getEntityManager();
        
        try {
            TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = :username AND u.password = :password", 
                User.class
            );
            query.setParameter("username", username);
            query.setParameter("password", password);
            
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new SQLException("Error to login", e);
        }
    }
} 