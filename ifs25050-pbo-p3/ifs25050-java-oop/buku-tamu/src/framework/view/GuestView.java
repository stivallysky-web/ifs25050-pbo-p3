package framework.view;

import adapter.presenter.GuestPresenter;
import domain.entity.Guest;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import framework.util.InputUtil;
import usecase.GuestUseCase;

public class GuestView {
    private final GuestUseCase useCase;
    private final GuestPresenter presenter;

    public GuestView(GuestUseCase useCase, GuestPresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        while (true) {
            presenter.showGuests(useCase.getAllGuests());
            System.out.println("Menu:");
            System.out.println("1. Daftarkan tamu");
            System.out.println("2. Cari");
            System.out.println("3. Hapus");
            System.out.println("x. Keluar");

            String input = InputUtil.input("Pilih");
            if (input.equals("x")) {
                break;
            }

            switch (input) {
                case "1" -> registerGuest();
                case "2" -> searchGuest();
                case "3" -> deleteGuest();
                default -> presenter.showInvalidMenu();
            }
            System.out.println();
        }
    }

    private void registerGuest() {
        System.out.println("[Mendaftarkan Tamu]");
        String name = InputUtil.input("Nama (x Jika Batal)");
        if (name.equals("x")) {
            return;
        }

        String purpose = InputUtil.input("Tujuan Kunjungan (x Jika Batal)");
        if (purpose.equals("x")) {
            return;
        }

        try {
            Guest g = useCase.registerGuest(name, purpose);
            presenter.showRegisterSuccess(g);
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
        }
    }

    private void searchGuest() {
        System.out.println("[Mencari Tamu]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");
        if (keyword.equals("x")) {
            return;
        }
        presenter.showSearchResults(useCase.searchGuests(keyword), keyword);
    }

    private void deleteGuest() {
        System.out.println("[Menghapus Tamu]");
        String inputId = InputUtil.input("[ID Tamu] yang dihapus (x Jika Batal)");
        if (inputId.equals("x")) {
            return;
        }

        try {
            int id = parseId(inputId);
            useCase.deleteGuest(id);
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
}
