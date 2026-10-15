package adapter.presenter;

import domain.entity.Activity;
import java.util.List;

public class ActivityPresenter {
    public String formatActivity(Activity a) {
        return "[" + a.getId() + "] " + a.getTitle() + " | Hari: " + a.getDay() + " | Pukul: " + a.getTime();
    }

    public void showActivities(List<Activity> list) {
        System.out.println("Daftar Kegiatan:");
        if (list.isEmpty()) {
            System.out.println("- Data kegiatan belum tersedia!");
        } else {
            for (Activity a : list) {
                System.out.println(formatActivity(a));
            }
        }
    }

    public void showAddSuccess(Activity a) {
        System.out.println("Berhasil menambah kegiatan: " + formatActivity(a));
    }

    public void showUpdateSuccess(Activity a) {
        System.out.println("Berhasil mengubah kegiatan.");
        System.out.println(formatActivity(a));
    }

    public void showDeleteSuccess() {
        System.out.println("Berhasil menghapus kegiatan.");
    }

    public void showSearchResults(List<Activity> list, String keyword) {
        System.out.println("Hasil Pencarian: \"" + keyword + "\"");
        if (list.isEmpty()) {
            System.out.println("- Data kegiatan belum tersedia!");
        } else {
            for (Activity a : list) {
                System.out.println(formatActivity(a));
            }
        }
    }

    public void showSortedActivities(List<Activity> list) {
        System.out.println("Daftar Kegiatan (Terurut):");
        if (list.isEmpty()) {
            System.out.println("- Data kegiatan belum tersedia!");
        } else {
            for (Activity a : list) {
                System.out.println(formatActivity(a));
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
