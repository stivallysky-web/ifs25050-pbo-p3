package domain.entity;

import java.util.Comparator;
import java.util.Map;

public enum SortOption {
    DAY((a1, a2) -> {
        int d1 = getDayOrder(a1.getDay());
        int d2 = getDayOrder(a2.getDay());
        return Integer.compare(d1, d2);
    }),
    TIME(Comparator.comparing(Activity::getTime)),
    TITLE_ASC(Comparator.comparing(Activity::getTitle, String.CASE_INSENSITIVE_ORDER)),
    TITLE_DESC(Comparator.comparing(Activity::getTitle, String.CASE_INSENSITIVE_ORDER).reversed());

    private static final Map<String, Integer> DAY_ORDER = Map.of(
        "senin", 1,
        "selasa", 2,
        "rabu", 3,
        "kamis", 4,
        "jumat", 5,
        "sabtu", 6,
        "minggu", 7
    );

    private static int getDayOrder(String day) {
        if (day == null) return 8;
        return DAY_ORDER.getOrDefault(day.trim().toLowerCase(), 8);
    }

    private final Comparator<Activity> comparator;

    SortOption(Comparator<Activity> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Activity> getComparator() {
        return comparator;
    }
}
