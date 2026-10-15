package usecase;

import domain.entity.Item;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IItemRepository;
import java.util.ArrayList;
import java.util.List;

public class ItemUseCase {
    private final IItemRepository repository;

    public ItemUseCase(IItemRepository repository) {
        this.repository = repository;
    }

    public Item addItem(String name, int quantity, String category) throws ValidationException {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Nama barang tidak boleh kosong");
        }
        if (quantity <= 0) {
            throw new ValidationException("Jumlah stok tidak valid!");
        }
        if (category == null || category.isBlank()) {
            throw new ValidationException("Kategori tidak boleh kosong");
        }
        Item item = new Item(0, name, quantity, category);
        return repository.save(item);
    }

    public Item updateStock(int id, int quantity) throws EntityNotFoundException, ValidationException {
        if (quantity <= 0) {
            throw new ValidationException("Jumlah stok tidak valid!");
        }
        Item item = repository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("Gagal mengubah barang dengan ID: " + id + ".")
        );
        item.changeQuantity(quantity);
        return repository.save(item);
    }

    public List<Item> getAllItems() {
        return repository.findAll();
    }

    public List<Item> searchItems(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return repository.findAll();
        }
        String lower = keyword.toLowerCase();
        List<Item> result = new ArrayList<>();
        for (Item item : repository.findAll()) {
            if (item.getName().toLowerCase().contains(lower)) {
                result.add(item);
            }
        }
        return result;
    }

    public List<Item> sortItems(SortOption option) {
        List<Item> list = new ArrayList<>(repository.findAll());
        if (option != null && option.getComparator() != null) {
            list.sort(option.getComparator());
        }
        return list;
    }

    public void deleteItem(int id) throws EntityNotFoundException {
        if (!repository.deleteById(id)) {
            throw new EntityNotFoundException("Gagal menghapus barang dengan ID: " + id + ".");
        }
    }
}
