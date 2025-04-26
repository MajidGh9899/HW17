package ir.maktab127.repositories;

import ir.maktab127.config.ApplicationContext;
import ir.maktab127.entities.Card;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.sql.SQLException;
import java.util.List;

public class CardRepositoryImpl implements CardRepository {

    private final ApplicationContext context ;

    public CardRepositoryImpl(ApplicationContext context ) {
        this.context = context;

    }
    @Override
    public void createCardTable() throws SQLException {

    }

    @Override
    public void addCard(Card card) throws SQLException {
        EntityManager entityManager = context.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        
        try {
            transaction.begin();
            entityManager.persist(card);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new SQLException("Error adding card", e);
        }
    }

    @Override
    public void deleteCard(String cardNumber) throws SQLException {
        EntityManager entityManager = context.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        
        try {
            transaction.begin();
            TypedQuery<Card> query = entityManager.createQuery(
                "SELECT c FROM Card c WHERE c.cardNumber = :cardNumber", 
                Card.class
            );
            query.setParameter("cardNumber", cardNumber);
            
            Card card = query.getSingleResult();
            if (card != null) {
                entityManager.remove(card);
            }
            transaction.commit();
        } catch (NoResultException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new SQLException("Error deleting card", e);
        }
    }

    @Override
    public Card getCardByNumber(String cardNumber) throws SQLException {
        EntityManager entityManager = context.getEntityManager();
        
        try {
            TypedQuery<Card> query = entityManager.createQuery(
                "SELECT c FROM Card c WHERE c.cardNumber = :cardNumber", 
                Card.class
            );
            query.setParameter("cardNumber", cardNumber);
            
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new SQLException("Error retrieving card", e);
        }
    }

    @Override
    public List<Card> getCardsByBank(String bankName) throws SQLException {
        EntityManager entityManager = context.getEntityManager();
        
        try {
            TypedQuery<Card> query = entityManager.createQuery(
                "SELECT c FROM Card c WHERE c.bankName = :bankName", 
                Card.class
            );
            query.setParameter("bankName", bankName);
            
            return query.getResultList();
        } catch (Exception e) {
            throw new SQLException("Error retrieving cards by bank", e);
        }
    }

    @Override
    public List<Card> getAllCards() throws SQLException {
        EntityManager entityManager = context.getEntityManager();
        
        try {
            TypedQuery<Card> query = entityManager.createQuery(
                "SELECT c FROM Card c", 
                Card.class
            );
            
            return query.getResultList();
        } catch (Exception e) {
            throw new SQLException("Error retrieving all cards", e);
        }
    }

    @Override
    public boolean updateBalance(String cardNumber, double newBalance) throws SQLException {
        EntityManager entityManager = context.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        
        try {
            transaction.begin();
            TypedQuery<Card> query = entityManager.createQuery(
                "SELECT c FROM Card c WHERE c.cardNumber = :cardNumber", 
                Card.class
            );
            query.setParameter("cardNumber", cardNumber);
            
            Card card = query.getSingleResult();
            if (card != null) {
                card.setBalance(newBalance);
                entityManager.merge(card);
                transaction.commit();
                return true;
            }
            return false;
        } catch (NoResultException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new SQLException("Error updating balance", e);
        }
    }
} 