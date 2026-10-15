package framework.view;

import adapter.presenter.FinancePresenter;
import domain.entity.SortOption;
import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import framework.util.InputUtil;
import usecase.FinanceUseCase;

public class FinanceView {
    private final FinanceUseCase useCase;
    private final FinancePresenter presenter;

    public FinanceView(FinanceUseCase useCase, FinancePresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        while (true) {
            presenter.showTransactions(useCase.getAllTransactions(), useCase.getBalance());
            System.out.println("Menu:");
            System.out.println("1. Tambah Pemasukan");
            System.out.println("2. Tambah Pengeluaran");
            System.out.println("3. Cari");
            System.out.println("4. Urutkan");
            System.out.println("5. Lihat Saldo");
            System.out.println("6. Hapus");
            System.out.println("x. Keluar");

            String input = InputUtil.input("Pilih");
            if (input.equals("x")) {
                break;
            }

            switch (input) {
                case "1" -> addTransaction(TransactionType.INCOME);
                case "2" -> addTransaction(TransactionType.EXPENSE);
                case "3" -> searchTransaction();
                case "4" -> sortTransaction();
                case "5" -> viewBalance();
                case "6" -> deleteTransaction();
                default -> presenter.showInvalidMenu();
            }
            System.out.println();
        }
    }

    private void addTransaction(TransactionType type) {
        System.out.println(type == TransactionType.INCOME ? "[Menambah Pemasukan]" : "[Menambah Pengeluaran]");
        String description = InputUtil.input("Keterangan (x Jika Batal)");
        if (description.equals("x")) {
            return;
        }

        String amountStr = InputUtil.input("Jumlah (x Jika Batal)");
        if (amountStr.equals("x")) {
            return;
        }

        try {
            long amount = parseAmount(amountStr);
            Transaction t = useCase.addTransaction(description, amount, type);
            presenter.showAddSuccess(t, useCase.getBalance());
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
        }
    }

    private void searchTransaction() {
        System.out.println("[Mencari Transaksi]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");
        if (keyword.equals("x")) {
            return;
        }
        presenter.showSearchResults(useCase.searchTransactions(keyword), keyword);
    }

    private void sortTransaction() {
        System.out.println("[Mengurutkan Transaksi]");
        System.out.println("Pilihan Pengurutan:");
        System.out.println("1. Pemasukan dulu");
        System.out.println("2. Pengeluaran dulu");
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

        presenter.showSortedTransactions(useCase.sortTransactions(option));
    }

    private void viewBalance() {
        presenter.showCurrentBalance(useCase.getBalance());
    }

    private void deleteTransaction() {
        System.out.println("[Menghapus Transaksi]");
        String inputId = InputUtil.input("[ID Transaksi] yang dihapus (x Jika Batal)");
        if (inputId.equals("x")) {
            return;
        }

        try {
            int id = parseId(inputId);
            useCase.deleteTransaction(id);
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

    private long parseAmount(String value) throws ValidationException {
        try {
            long amount = Long.parseLong(value);
            if (amount <= 0) {
                throw new ValidationException("Jumlah tidak valid!");
            }
            return amount;
        } catch (NumberFormatException e) {
            throw new ValidationException("Jumlah tidak valid!");
        }
    }

    private SortOption mapSortOption(String input) {
        return switch (input) {
            case "1" -> SortOption.INCOME_FIRST;
            case "2" -> SortOption.EXPENSE_FIRST;
            case "3" -> SortOption.AMOUNT_ASC;
            case "4" -> SortOption.AMOUNT_DESC;
            default -> null;
        };
    }
}
