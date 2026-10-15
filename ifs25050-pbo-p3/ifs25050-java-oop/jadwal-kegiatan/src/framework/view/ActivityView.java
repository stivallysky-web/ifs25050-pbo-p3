package framework.view;

import adapter.presenter.ActivityPresenter;
import domain.entity.Activity;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import framework.util.InputUtil;
import usecase.ActivityUseCase;

public class ActivityView {
    private final ActivityUseCase useCase;
    private final ActivityPresenter presenter;

    public ActivityView(ActivityUseCase useCase, ActivityPresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        while (true) {
            presenter.showActivities(useCase.getAllActivities());
            System.out.println("Menu:");
            System.out.println("1. Tambah kegiatan");
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
                case "1" -> addActivity();
                case "2" -> updateActivity();
                case "3" -> searchActivity();
                case "4" -> sortActivity();
                case "5" -> deleteActivity();
                default -> presenter.showInvalidMenu();
            }
            System.out.println();
        }
    }

    private void addActivity() {
        System.out.println("[Menambah Kegiatan]");
        String title = InputUtil.input("Judul (x Jika Batal)");
        if (title.equals("x")) {
            return;
        }

        String day = InputUtil.input("Hari (x Jika Batal)");
        if (day.equals("x")) {
            return;
        }

        String time = InputUtil.input("Waktu (x Jika Batal)");
        if (time.equals("x")) {
            return;
        }

        try {
            Activity a = useCase.addActivity(title, day, time);
            presenter.showAddSuccess(a);
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
        }
    }

    private void updateActivity() {
        System.out.println("[Mengubah Kegiatan]");
        String inputId = InputUtil.input("ID Kegiatan yang diubah (x Jika Batal)");
        if (inputId.equals("x")) {
            return;
        }

        try {
            int id = parseId(inputId);
            String title = InputUtil.inputRaw("Judul Baru (Kosongkan jika tidak ingin mengubah)");
            String day = InputUtil.inputRaw("Hari Baru (Kosongkan jika tidak ingin mengubah)");
            String time = InputUtil.inputRaw("Waktu Baru (Kosongkan jika tidak ingin mengubah)");

            Activity a = useCase.updateActivity(id, title, day, time);
            presenter.showUpdateSuccess(a);
        } catch (ValidationException e) {
            presenter.showError(e.getMessage());
        } catch (EntityNotFoundException e) {
            presenter.showError(e.getMessage());
        }
    }

    private void searchActivity() {
        System.out.println("[Mencari Kegiatan]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");
        if (keyword.equals("x")) {
            return;
        }
        presenter.showSearchResults(useCase.searchActivities(keyword), keyword);
    }

    private void sortActivity() {
        System.out.println("[Mengurutkan Kegiatan]");
        System.out.println("Pilihan Pengurutan:");
        System.out.println("1. Hari");
        System.out.println("2. Waktu");
        System.out.println("3. Judul (A-Z)");
        System.out.println("4. Judul (Z-A)");
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

        presenter.showSortedActivities(useCase.sortActivities(option));
    }

    private void deleteActivity() {
        System.out.println("[Menghapus Kegiatan]");
        String inputId = InputUtil.input("[ID Kegiatan] yang dihapus (x Jika Batal)");
        if (inputId.equals("x")) {
            return;
        }

        try {
            int id = parseId(inputId);
            useCase.deleteActivity(id);
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
            case "1" -> SortOption.DAY;
            case "2" -> SortOption.TIME;
            case "3" -> SortOption.TITLE_ASC;
            case "4" -> SortOption.TITLE_DESC;
            default -> null;
        };
    }
}
