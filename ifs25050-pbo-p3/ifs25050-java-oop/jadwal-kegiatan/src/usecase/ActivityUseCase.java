package usecase;

import domain.entity.Activity;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IActivityRepository;
import java.util.ArrayList;
import java.util.List;

public class ActivityUseCase {
    private final IActivityRepository repository;

    public ActivityUseCase(IActivityRepository repository) {
        this.repository = repository;
    }

    public Activity addActivity(String title, String day, String time) throws ValidationException {
        if (title == null || title.isBlank() || day == null || day.isBlank() || time == null || time.isBlank()) {
            throw new ValidationException("Data kegiatan tidak boleh kosong");
        }
        Activity activity = new Activity(0, title, day, time);
        return repository.save(activity);
    }

    public Activity updateActivity(int id, String newTitle, String newDay, String newTime) throws EntityNotFoundException, ValidationException {
        Activity act = repository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("Gagal mengubah kegiatan dengan ID: " + id + ".")
        );
        if (newTitle != null && !newTitle.isBlank()) {
            act.changeTitle(newTitle);
        }
        if (newDay != null && !newDay.isBlank()) {
            act.changeDay(newDay);
        }
        if (newTime != null && !newTime.isBlank()) {
            act.changeTime(newTime);
        }
        return repository.save(act);
    }

    public List<Activity> getAllActivities() {
        return repository.findAll();
    }

    public List<Activity> searchActivities(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return repository.findAll();
        }
        String lower = keyword.toLowerCase();
        List<Activity> result = new ArrayList<>();
        for (Activity a : repository.findAll()) {
            if (a.getTitle().toLowerCase().contains(lower)) {
                result.add(a);
            }
        }
        return result;
    }

    public List<Activity> sortActivities(SortOption option) {
        List<Activity> list = new ArrayList<>(repository.findAll());
        if (option != null && option.getComparator() != null) {
            list.sort(option.getComparator());
        }
        return list;
    }

    public void deleteActivity(int id) throws EntityNotFoundException {
        if (!repository.deleteById(id)) {
            throw new EntityNotFoundException("Gagal menghapus kegiatan dengan ID: " + id + ".");
        }
    }
}
