package adapter.repository;

import domain.entity.Item;
import domain.repository.IItemRepository;

public class ItemRepository extends InMemoryRepository<Item, Integer> implements IItemRepository {
    private int nextId = 1;

    @Override
    public Item save(Item item) {
        Item saved;
        if (item.getId() == 0) {
            saved = new Item(nextId, item.getName(), item.getQuantity(), item.getCategory());
            storage.put(nextId, saved);
            nextId++;
        } else {
            saved = item;
            storage.put(item.getId(), saved);
        }
        return saved;
    }
}
