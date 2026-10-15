package adapter.repository;

import domain.repository.IRepository;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Generic base class untuk penyimpanan in-memory berbasis Map.
 *
 * @param <T>  tipe entitas
 * @param <ID> tipe identifier unik entitas
 */
public abstract class InMemoryRepository<T, ID> implements IRepository<T, ID> {
    protected final Map<ID, T> storage = new LinkedHashMap<>();

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public boolean deleteById(ID id) {
        return storage.remove(id) != null;
    }
}
