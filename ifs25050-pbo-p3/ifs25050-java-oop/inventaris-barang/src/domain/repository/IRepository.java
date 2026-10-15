package domain.repository;

import java.util.List;
import java.util.Optional;

/**
 * Generic interface (port) penyimpanan data.
 *
 * @param <T>  tipe entitas
 * @param <ID> tipe identifier unik entitas
 */
public interface IRepository<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    boolean deleteById(ID id);
}
