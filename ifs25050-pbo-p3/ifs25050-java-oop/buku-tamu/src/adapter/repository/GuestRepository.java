package adapter.repository;

import domain.entity.Guest;
import domain.repository.IGuestRepository;

public class GuestRepository extends InMemoryRepository<Guest, Integer> implements IGuestRepository {
    private int nextId = 1;

    @Override
    public Guest save(Guest guest) {
        Guest saved;
        if (guest.getId() == 0) {
            saved = new Guest(nextId, guest.getName(), guest.getPurpose());
            storage.put(nextId, saved);
            nextId++;
        } else {
            saved = guest;
            storage.put(guest.getId(), saved);
        }
        return saved;
    }
}
