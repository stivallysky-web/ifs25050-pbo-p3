package framework.view;

import adapter.presenter.ContactPresenter;
import domain.entity.Contact;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import framework.util.InputUtil;
import usecase.ContactUseCase;

public class ContactView {
    private final ContactUseCase useCase;
    private final ContactPresenter presenter;

    public ContactView(ContactUseCase useCase, ContactPresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        while (true) {
            presenter.showContacts(useCase.getAllContacts());
            System.out.println("Menu:");
            System.out.println("1. Tambah kontak");
            System.out.println("2. Ubah");
            System.out.println("3. Cari");
            System.out.println("4. Urutkan");
            System.out.println("5. Hapus");
            System.out.println("x. Keluar");

            String input = InputUtil.input("Pilih");
            if (input.equals("x")) {
                break;
            }

            switch (input) {
                case "1" -> addContact();
                case "2" -> updateContact();
                case "3" -> searchContact();
                case "4" -> sortContact();
                case "5" -> deleteContact();
                default -> presenter.showInvalidMenu();
            }
            System.out.println();
        }
    }

    private void addContact() {
        System.out.println("[Menambah Kontak]");
        String name = InputUtil.input("Nama (x Jika Batal)");
        if (name.equals("x")) {
            return;
        }

        String phone = InputUtil.input("Nomor Telepon (x Jika Batal)");
        if (phone.equals("x")) {
            return;
        }

        String email = InputUtil.input("Email (x Jika Batal)");
        if (email.equals("x")) {
            return;
        }

        try {
            Contact c = useCase.addContact(name, phone, email);
            presenter.showAddSuccess(c);
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
        }
    }

    private void updateContact() {
        System.out.println("[Mengubah Kontak]");
        String inputId = InputUtil.input("ID Kontak yang diubah (x Jika Batal)");
        if (inputId.equals("x")) {
            return;
        }

        try {
            int id = parseId(inputId);
            String name = InputUtil.inputRaw("Nama Baru (Kosongkan jika tidak ingin mengubah)");
            String phone = InputUtil.inputRaw("Nomor Telepon Baru (Kosongkan jika tidak ingin mengubah)");
            String email = InputUtil.inputRaw("Email Baru (Kosongkan jika tidak ingin mengubah)");

            Contact c = useCase.updateContact(id, name, phone, email);
            presenter.showUpdateSuccess(c);
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
        } catch (EntityNotFoundException e) {
            presenter.showError(e.getMessage());
        }
    }

    private void searchContact() {
        System.out.println("[Mencari Kontak]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");
        if (keyword.equals("x")) {
            return;
        }
        presenter.showSearchResults(useCase.searchContacts(keyword), keyword);
    }

    private void sortContact() {
        System.out.println("[Mengurutkan Kontak]");
        System.out.println("Pilihan Pengurutan:");
        System.out.println("1. Nama (A-Z)");
        System.out.println("2. Nama (Z-A)");
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

        presenter.showSortedContacts(useCase.sortContacts(option));
    }

    private void deleteContact() {
        System.out.println("[Menghapus Kontak]");
        String inputId = InputUtil.input("[ID Kontak] yang dihapus (x Jika Batal)");
        if (inputId.equals("x")) {
            return;
        }

        try {
            int id = parseId(inputId);
            useCase.deleteContact(id);
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

    private SortOption mapSortOption(String input) {
        return switch (input) {
            case "1" -> SortOption.NAME_ASC;
            case "2" -> SortOption.NAME_DESC;
            default -> null;
        };
    }
}
