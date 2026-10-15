package adapter.presenter;

import domain.entity.Transaction;
import java.util.List;

public class FinancePresenter {
    public String formatTransaction(Transaction t) {
        return "[" + t.getId() + "] " + t.getDescription() + " | Rp " + t.getAmount() + " | [" + t.getType().getDisplayName() + "]";
    }

    public void showTransactions(List<Transaction> list, long balance) {
        System.out.println("Daftar Transaksi:");
        if (list.isEmpty()) {
            System.out.println("- Belum ada transaksi!");
        } else {
            for (Transaction t : list) {
                System.out.println(formatTransaction(t));
            }
            System.out.println("Saldo: Rp " + balance);
        }
    }

    public void showAddSuccess(Transaction t, long balance) {
        System.out.println("Berhasil menambah transaksi: " + formatTransaction(t));
        System.out.println("Saldo: Rp " + balance);
    }

    public void showDeleteSuccess() {
        System.out.println("Berhasil menghapus transaksi.");
    }

    public void showSearchResults(List<Transaction> list, String keyword) {
        System.out.println("Hasil Pencarian: \"" + keyword + "\"");
        if (list.isEmpty()) {
            System.out.println("- Belum ada transaksi!");
        } else {
            for (Transaction t : list) {
                System.out.println(formatTransaction(t));
            }
        }
    }

    public void showSortedTransactions(List<Transaction> list) {
        System.out.println("Daftar Transaksi (Terurut):");
        if (list.isEmpty()) {
            System.out.println("- Belum ada transaksi!");
        } else {
            for (Transaction t : list) {
                System.out.println(formatTransaction(t));
            }
        }
    }

    public void showCurrentBalance(long balance) {
        System.out.println("Saldo saat ini: Rp " + balance);
    }

    public void showInvalidMenu() {
        System.out.println("[!] Pilihan tidak dimengerti.");
    }

    public void showInvalidId() {
        System.out.println("[!] ID tidak valid!");
    }

    public void showInvalidAmount() {
        System.out.println("[!] Jumlah tidak valid!");
    }

    public void showInvalidSortOption() {
        System.out.println("[!] Pilihan tidak valid!");
    }

    public void showError(String message) {
        System.out.println("[!] " + message);
    }
}
