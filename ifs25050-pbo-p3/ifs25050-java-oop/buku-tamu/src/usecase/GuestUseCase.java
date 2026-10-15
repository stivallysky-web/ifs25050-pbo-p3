package usecase;

import domain.entity.Guest;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IGuestRepository;
import java.util.ArrayList;
import java.util.List;

public class GuestUseCase {
    private final IGuestRepository repository;

    public GuestUseCase(IGuestRepository repository) {
        this.repository = repository;
    }

    public Guest registerGuest(String name, String purpose) throws ValidationException {
        if (name == null || name.isBlank() || purpose == null || purpose.isBlank()) {
            throw new ValidationException("Nama dan tujuan kunjungan tidak boleh kosong");
        }
        Guest guest = new Guest(0, name, purpose);
        return repository.save(guest);
    }

    public List<Guest> getAllGuests() {
        return repository.findAll();
    }

    public List<Guest> searchGuests(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return repository.findAll();
        }
        String lower = keyword.toLowerCase();
        List<Guest> result = new ArrayList<>();
        for (Guest g : repository.findAll()) {
            if (g.getName().toLowerCase().contains(lower)) {
                result.add(g);
            }
        }
        return result;
    }

    public void deleteGuest(int id) throws EntityNotFoundException {
        if (!repository.deleteById(id)) {
            throw new EntityNotFoundException("Gagal menghapus tamu dengan ID: " + id + ".");
        }
    }
}
