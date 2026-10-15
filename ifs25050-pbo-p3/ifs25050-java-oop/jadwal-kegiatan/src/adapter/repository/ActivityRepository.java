package adapter.repository;

import domain.entity.Activity;
import domain.repository.IActivityRepository;

public class ActivityRepository extends InMemoryRepository<Activity, Integer> implements IActivityRepository {
    private int nextId = 1;

    @Override
    public Activity save(Activity activity) {
        Activity saved;
        if (activity.getId() == 0) {
            saved = new Activity(nextId, activity.getTitle(), activity.getDay(), activity.getTime());
            storage.put(nextId, saved);
            nextId++;
        } else {
            saved = activity;
            storage.put(activity.getId(), saved);
        }
        return saved;
    }
}
