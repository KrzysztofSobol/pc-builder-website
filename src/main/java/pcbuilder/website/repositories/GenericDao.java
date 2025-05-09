package pcbuilder.website.repositories;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, K> {
    void save(T entity);
    void delete(T entity);
    void update(T entity);
    Optional<T> findById(K id);
    List<T> findAll();
    boolean exists(K id);
}