package adapter.presenter;

import domain.entity.Guest;
import java.util.List;

public class GuestPresenter {
    public String formatGuest(Guest g) {
        return "[" + g.getId() + "] " + g.getName() + " | Tujuan: " + g.getPurpose();
    }

    public void showGuests(List<Guest> list) {
        System.out.println("Daftar Tamu:");
        if (list.isEmpty()) {
            System.out.println("- Data tamu belum tersedia!");
        } else {
            for (Guest g : list) {
                System.out.println(formatGuest(g));
            }
        }
    }

    public void showRegisterSuccess(Guest g) {
        System.out.println("Berhasil mendaftarkan tamu: " + formatGuest(g));
    }

    public void showDeleteSuccess() {
        System.out.println("Berhasil menghapus tamu.");
    }

    public void showSearchResults(List<Guest> list, String keyword) {
        System.out.println("Hasil Pencarian: \"" + keyword + "\"");
        if (list.isEmpty()) {
            System.out.println("- Data tamu belum tersedia!");
        } else {
            for (Guest g : list) {
                System.out.println(formatGuest(g));
            }
        }
    }

    public void showInvalidMenu() {
        System.out.println("[!] Pilihan tidak dimengerti.");
    }

    public void showInvalidId() {
        System.out.println("[!] ID tidak valid!");
    }

    public void showError(String message) {
        System.out.println("[!] " + message);
    }
}
