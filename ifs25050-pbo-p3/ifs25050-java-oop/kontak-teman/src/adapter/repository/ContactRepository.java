package adapter.repository;

import domain.entity.Contact;
import domain.repository.IContactRepository;

public class ContactRepository extends InMemoryRepository<Contact, Integer> implements IContactRepository {
    private int nextId = 1;

    @Override
    public Contact save(Contact contact) {
        Contact saved;
        if (contact.getId() == 0) {
            saved = new Contact(nextId, contact.getName(), contact.getPhone(), contact.getEmail());
            storage.put(nextId, saved);
            nextId++;
        } else {
            saved = contact;
            storage.put(contact.getId(), saved);
        }
        return saved;
    }
}
