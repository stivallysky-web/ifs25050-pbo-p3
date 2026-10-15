package adapter.presenter;

import domain.entity.Contact;
import java.util.List;

public class ContactPresenter {
    public String formatContact(Contact c) {
        return "[" + c.getId() + "] " + c.getName() + " | Telp: " + c.getPhone() + " | Email: " + c.getEmail();
    }

    public void showContacts(List<Contact> list) {
        System.out.println("Daftar Kontak:");
        if (list.isEmpty()) {
            System.out.println("- Data kontak belum tersedia!");
        } else {
            for (Contact c : list) {
                System.out.println(formatContact(c));
            }
        }
    }

    public void showAddSuccess(Contact c) {
        System.out.println("Berhasil menambah kontak: " + formatContact(c));
    }

    public void showUpdateSuccess(Contact c) {
        System.out.println("Berhasil mengubah kontak.");
        System.out.println(formatContact(c));
    }

    public void showDeleteSuccess() {
        System.out.println("Berhasil menghapus kontak.");
    }

    public void showSearchResults(List<Contact> list, String keyword) {
        System.out.println("Hasil Pencarian: \"" + keyword + "\"");
        if (list.isEmpty()) {
            System.out.println("- Data kontak belum tersedia!");
        } else {
            for (Contact c : list) {
                System.out.println(formatContact(c));
            }
        }
    }

    public void showSortedContacts(List<Contact> list) {
        System.out.println("Daftar Kontak (Terurut):");
        if (list.isEmpty()) {
            System.out.println("- Data kontak belum tersedia!");
        } else {
            for (Contact c : list) {
                System.out.println(formatContact(c));
            }
        }
    }

    public void showInvalidMenu() {
        System.out.println("[!] Pilihan tidak dimengerti.");
    }

    public void showInvalidId() {
        System.out.println("[!] ID tidak valid!");
    }

    public void showInvalidSortOption() {
        System.out.println("[!] Pilihan tidak valid!");
    }

    public void showError(String message) {
        System.out.println("[!] " + message);
    }
}
