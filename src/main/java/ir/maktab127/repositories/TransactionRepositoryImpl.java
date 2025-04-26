package ir.maktab127.repositories;

import ir.maktab127.config.ApplicationContext;
import ir.maktab127.entities.Transaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.sql.SQLException;
import java.util.List;

public class TransactionRepositoryImpl implements TransactionRepository {

    private final ApplicationContext context ;

    public TransactionRepositoryImpl(ApplicationContext context ) {
        this.context = context;
    }

    @Override
    public void createTransactionTable() throws SQLException {

    }

    @Override
    public void saveTransaction(Transaction transaction) throws SQLException {
        EntityManager entityManager = context.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        try {
            entityTransaction.begin();
            entityManager.persist(transaction);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw new SQLException("Error saving transaction", e);
        }
    }

    @Override
    public List<Transaction> getTransactionsByUser(String cardNumber) throws SQLException {
        EntityManager entityManager = context.getEntityManager();
        
        try {
            TypedQuery<Transaction> query = entityManager.createQuery(
                "SELECT t FROM Transaction t WHERE t.fromCard = :cardNumber OR t.toCard = :cardNumber", 
                Transaction.class
            );
            query.setParameter("cardNumber", cardNumber);
            
            return query.getResultList();
        } catch (Exception e) {
            throw new SQLException("Error retrieving transactions for user", e);
        }
    }
} 