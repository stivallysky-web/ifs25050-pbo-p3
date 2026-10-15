package framework.view;

import adapter.presenter.ItemPresenter;
import domain.entity.Item;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import framework.util.InputUtil;
import usecase.ItemUseCase;

public class ItemView {
    private final ItemUseCase useCase;
    private final ItemPresenter presenter;

    public ItemView(ItemUseCase useCase, ItemPresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        while (true) {
            presenter.showItems(useCase.getAllItems());
            System.out.println("Menu:");
            System.out.println("1. Tambah barang");
            System.out.println("2. Ubah stok");
            System.out.println("3. Cari");
            System.out.println("4. Urutkan");
            System.out.println("5. Hapus");
            System.out.println("x. Keluar");

            String input = InputUtil.input("Pilih");
            if (input.equals("x")) {
                break;
            }

            switch (input) {
                case "1" -> addItem();
                case "2" -> updateStock();
                case "3" -> searchItem();
                case "4" -> sortItem();
                case "5" -> deleteItem();
                default -> presenter.showInvalidMenu();
            }
            System.out.println();
        }
    }

    private void addItem() {
        System.out.println("[Menambah Barang]");
        String name = InputUtil.input("Nama (x Jika Batal)");
        if (name.equals("x")) {
            return;
        }

        String qtyStr = InputUtil.input("Jumlah Stok (x Jika Batal)");
        if (qtyStr.equals("x")) {
            return;
        }

        try {
            int qty = parseQuantity(qtyStr);
            String category = InputUtil.input("Kategori (x Jika Batal)");
            if (category.equals("x")) {
                return;
            }
            Item item = useCase.addItem(name, qty, category);
            presenter.showAddSuccess(item);
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
        }
    }

    private void updateStock() {
        System.out.println("[Mengubah Stok Barang]");
        String inputId = InputUtil.input("ID Barang yang diubah (x Jika Batal)");
        if (inputId.equals("x")) {
            return;
        }

        try {
            int id = parseId(inputId);
            String qtyStr = InputUtil.input("Jumlah Stok Baru (x Jika Batal)");
            if (qtyStr.equals("x")) {
                return;
            }
            int qty = parseQuantity(qtyStr);
            Item item = useCase.updateStock(id, qty);
            presenter.showUpdateSuccess(item);
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
        } catch (EntityNotFoundException e) {
            presenter.showError(e.getMessage());
        }
    }

    private void searchItem() {
        System.out.println("[Mencari Barang]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");
        if (keyword.equals("x")) {
            return;
        }
        presenter.showSearchResults(useCase.searchItems(keyword), keyword);
    }

    private void sortItem() {
        System.out.println("[Mengurutkan Barang]");
        System.out.println("Pilihan Pengurutan:");
        System.out.println("1. Nama (A-Z)");
        System.out.println("2. Nama (Z-A)");
        System.out.println("3. Jumlah terkecil");
        System.out.println("4. Jumlah terbesar");
        System.out.println("x. Batal");

        String input = InputUtil.input("Pilih");
        if (input.equals("x")) {
            return;
        }

        SortOption option = mapSortOption(input);
        if (option == null) {
            presenter.showInvalidSortOption();
            return;
        }

        presenter.showSortedItems(useCase.sortItems(option));
    }

    private void deleteItem() {
        System.out.println("[Menghapus Barang]");
        String inputId = InputUtil.input("[ID Barang] yang dihapus (x Jika Batal)");
        if (inputId.equals("x")) {
            return;
        }

        try {
            int id = parseId(inputId);
            useCase.deleteItem(id);
            presenter.showDeleteSuccess();
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
        } catch (EntityNotFoundException e) {
            presenter.showError(e.getMessage());
        }
    }

    private int parseId(String value) throws ValidationException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ValidationException("ID tidak valid!");
        }
    }

    private int parseQuantity(String value) throws ValidationException {
        try {
            int qty = Integer.parseInt(value);
            if (qty <= 0) {
                throw new ValidationException("Jumlah stok tidak valid!");
            }
            return qty;
        } catch (NumberFormatException e) {
            throw new ValidationException("Jumlah stok tidak valid!");
        }
    }

    private SortOption mapSortOption(String input) {
        return switch (input) {
            case "1" -> SortOption.NAME_ASC;
            case "2" -> SortOption.NAME_DESC;
            case "3" -> SortOption.QUANTITY_ASC;
            case "4" -> SortOption.QUANTITY_DESC;
            default -> null;
        };
    }
}
