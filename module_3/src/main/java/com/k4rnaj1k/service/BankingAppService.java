package com.k4rnaj1k.service;

import com.k4rnaj1k.model.Account;
import com.k4rnaj1k.model.Operation;
import com.k4rnaj1k.model.OperationCategory;
import com.k4rnaj1k.model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class BankingAppService {

    Session session;

    public BankingAppService(Session session) {
        this.session = session;
    }

    public List<OperationCategory> getOperationCategories(OperationCategory.Type type) {
        Query<OperationCategory> q = session.createQuery("from OperationCategory where type=:type", OperationCategory.class);
        q.setParameter("type", type);
        return q.getResultList();
    }

    public void addOperation(OperationCategory category, Long sum, Account account) {
        session.getTransaction().begin();
        Operation operation = new Operation(category, sum, account);
        session.persist(operation);
        session.getTransaction().commit();
    }

    public User getUser(String email, String username, String password) {
        Query<User> query = session.createQuery("from User where email=:email and username=:username and password=:password", User.class);
        query.setParameter("email", email)
                .setParameter("username", username)
                .setParameter("password", password)
                .setMaxResults(1);
        return query.uniqueResult();
    }
}
