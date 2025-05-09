package pcbuilder.website.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import pcbuilder.website.repositories.GenericDao;
import pcbuilder.website.utils.JpaFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public class GenericDaoImpl<T,K> implements GenericDao<T, K> {
    private final Class<T> classType;

    @SuppressWarnings("unchecked") // compiler doesn't know about reflection api sad :(
    public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType p = (ParameterizedType) t;
        this.classType = (Class<T>) p.getActualTypeArguments()[0];
    }

    protected EntityManager getEntityManager() {
        return JpaFactory.getEntityManager();
    }

    @Override
    public void save(T entity) {
        try(EntityManager em = getEntityManager()) {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        }
    }

    @Override
    public void delete(T entity) {
        try(EntityManager em = getEntityManager()) {
            em.getTransaction().begin();
            em.remove(em.merge(entity));
            em.getTransaction().commit();
        }
    }

    @Override
    public void update(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        try(EntityManager em = getEntityManager()) {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        }
    }

    @Override
    public Optional<T> findById(K id) {
        try(EntityManager em = getEntityManager()) {
            T entity = em.find(classType, id);
            return Optional.ofNullable(entity);
        }
    }

    @Override
    public List<T> findAll() {
        try(EntityManager em = getEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(classType);
            Root<T> rootEntry = cq.from(classType);

            CriteriaQuery<T> all = cq.select(rootEntry);
            TypedQuery<T> allQuery = em.createQuery(all);
            return allQuery.getResultList();
        }
    }

    @Override
    public boolean exists(K id) {
        try (EntityManager em = getEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<T> rootEntry = cq.from(classType);

            Predicate idPredicate = cb.equal(rootEntry.get("id"), id);
            cq.select(cb.count(rootEntry)).where(idPredicate);

            Long count = em.createQuery(cq).getSingleResult();
            return count > 0;
        }
    }
}
