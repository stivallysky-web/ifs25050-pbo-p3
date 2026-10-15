package adapter.presenter;

import domain.entity.Item;
import java.util.List;

public class ItemPresenter {
    public String formatItem(Item item) {
        return "[" + item.getId() + "] " + item.getName() + " | Stok: " + item.getQuantity() + " | Kategori: " + item.getCategory();
    }

    public void showItems(List<Item> list) {
        System.out.println("Daftar Barang:");
        if (list.isEmpty()) {
            System.out.println("- Data barang belum tersedia!");
        } else {
            for (Item item : list) {
                System.out.println(formatItem(item));
            }
        }
    }

    public void showAddSuccess(Item item) {
        System.out.println("Berhasil menambah barang: " + formatItem(item));
    }

    public void showUpdateSuccess(Item item) {
        System.out.println("Berhasil mengubah stok barang.");
        System.out.println(formatItem(item));
    }

    public void showDeleteSuccess() {
        System.out.println("Berhasil menghapus barang.");
    }

    public void showSearchResults(List<Item> list, String keyword) {
        System.out.println("Hasil Pencarian: \"" + keyword + "\"");
        if (list.isEmpty()) {
            System.out.println("- Data barang belum tersedia!");
        } else {
            for (Item item : list) {
                System.out.println(formatItem(item));
            }
        }
    }

    public void showSortedItems(List<Item> list) {
        System.out.println("Daftar Barang (Terurut):");
        if (list.isEmpty()) {
            System.out.println("- Data barang belum tersedia!");
        } else {
            for (Item item : list) {
                System.out.println(formatItem(item));
            }
        }
    }

    public void showInvalidMenu() {
        System.out.println("[!] Pilihan tidak dimengerti.");
    }

    public void showInvalidId() {
        System.out.println("[!] ID tidak valid!");
    }

    public void showInvalidStock() {
        System.out.println("[!] Jumlah stok tidak valid!");
    }

    public void showInvalidSortOption() {
        System.out.println("[!] Pilihan tidak valid!");
    }

    public void showError(String message) {
        System.out.println("[!] " + message);
    }
}
