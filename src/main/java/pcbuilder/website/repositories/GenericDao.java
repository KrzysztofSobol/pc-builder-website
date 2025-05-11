package pcbuilder.website.repositories;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, K> {
    T save(T entity);
    void delete(T entity);
    T update(T entity);
    Optional<T> findById(K id);
    List<T> findAll();
    boolean exists(K id);
}