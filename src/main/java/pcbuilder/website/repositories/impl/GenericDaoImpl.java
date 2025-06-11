package pcbuilder.website.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import pcbuilder.website.repositories.GenericDao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public class GenericDaoImpl<T,K> implements GenericDao<T, K>  {
    private final Class<T> classType;

    @PersistenceContext
    protected EntityManager em;

    @SuppressWarnings("unchecked") // compiler doesn't know about reflection api :(
    public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType p = (ParameterizedType) t;
        this.classType = (Class<T>) p.getActualTypeArguments()[0];
    }

    @Override
    @Transactional
    public T save(T entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public void delete(T entity) {
        em.remove(em.merge(entity));
    }

    @Override
    @Transactional
    public T update(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        em.merge(entity);
        return entity;
    }

    @Override
    public Optional<T> findById(K id) {
        T entity = em.find(classType, id);
        return Optional.ofNullable(entity);
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(classType);
        Root<T> rootEntry = cq.from(classType);

        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = em.createQuery(all);

        return allQuery.getResultList();
    }

    @Override
    public Page<T> findPaged(Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<T> cq = cb.createQuery(classType);
        Root<T> root = cq.from(classType);
        cq.select(root);

        TypedQuery<T> query = em.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<T> resultList = query.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(classType);
        countQuery.select(cb.count(countRoot));
        Long total = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(resultList, pageable, total);
    }

    @Override
    public boolean exists(K id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> rootEntry = cq.from(classType);

        Predicate idPredicate = cb.equal(rootEntry.get("id"), id);
        cq.select(cb.count(rootEntry)).where(idPredicate);

        Long count = em.createQuery(cq).getSingleResult();

        return count > 0;
    }
}
