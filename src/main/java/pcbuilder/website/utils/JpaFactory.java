package pcbuilder.website.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaFactory {
    private static JpaFactory instance;
    private EntityManagerFactory emf;

    private JpaFactory() {
        emf = Persistence.createEntityManagerFactory("pc-builder-website");
    }

    private static JpaFactory getInstance() {
        if(instance == null) {
            instance = new JpaFactory();
        }
        return instance;
    }

    public static EntityManager getEntityManager() {
        return getInstance().emf.createEntityManager();
    }
}
