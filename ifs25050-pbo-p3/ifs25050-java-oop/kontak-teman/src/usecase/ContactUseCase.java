package usecase;

import domain.entity.Contact;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IContactRepository;
import java.util.ArrayList;
import java.util.List;

public class ContactUseCase {
    private final IContactRepository repository;

    public ContactUseCase(IContactRepository repository) {
        this.repository = repository;
    }

    public Contact addContact(String name, String phone, String email) throws ValidationException {
        if (name == null || name.isBlank() || phone == null || phone.isBlank() || email == null || email.isBlank()) {
            throw new ValidationException("Data kontak tidak boleh kosong");
        }
        Contact contact = new Contact(0, name, phone, email);
        return repository.save(contact);
    }

    public Contact updateContact(int id, String newName, String newPhone, String newEmail) throws EntityNotFoundException, ValidationException {
        Contact contact = repository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("Gagal mengubah kontak dengan ID: " + id + ".")
        );
        if (newName != null && !newName.isBlank()) {
            contact.changeName(newName);
        }
        if (newPhone != null && !newPhone.isBlank()) {
            contact.changePhone(newPhone);
        }
        if (newEmail != null && !newEmail.isBlank()) {
            contact.changeEmail(newEmail);
        }
        return repository.save(contact);
    }

    public List<Contact> getAllContacts() {
        return repository.findAll();
    }

    public List<Contact> searchContacts(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return repository.findAll();
        }
        String lower = keyword.toLowerCase();
        List<Contact> result = new ArrayList<>();
        for (Contact c : repository.findAll()) {
            if (c.getName().toLowerCase().contains(lower)) {
                result.add(c);
            }
        }
        return result;
    }

    public List<Contact> sortContacts(SortOption option) {
        List<Contact> list = new ArrayList<>(repository.findAll());
        if (option != null && option.getComparator() != null) {
            list.sort(option.getComparator());
        }
        return list;
    }

    public void deleteContact(int id) throws EntityNotFoundException {
        if (!repository.deleteById(id)) {
            throw new EntityNotFoundException("Gagal menghapus kontak dengan ID: " + id + ".");
        }
    }
}
