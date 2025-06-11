package pcbuilder.website.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, K> {
    T save(T entity);
    void delete(T entity);
    T update(T entity);
    Optional<T> findById(K id);
    List<T> findAll();
    Page<T> findPaged(Pageable pageable);
    boolean exists(K id);
}