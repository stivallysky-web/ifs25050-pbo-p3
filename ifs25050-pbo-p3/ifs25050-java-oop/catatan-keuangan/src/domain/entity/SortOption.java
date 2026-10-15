package domain.entity;

import java.util.Comparator;

public enum SortOption {
    INCOME_FIRST((t1, t2) -> {
        if (t1.getType() != t2.getType()) {
            return t1.getType() == TransactionType.INCOME ? -1 : 1;
        }
        return Long.compare(t1.getAmount(), t2.getAmount());
    }),
    EXPENSE_FIRST((t1, t2) -> {
        if (t1.getType() != t2.getType()) {
            return t1.getType() == TransactionType.EXPENSE ? -1 : 1;
        }
        return Long.compare(t1.getAmount(), t2.getAmount());
    }),
    AMOUNT_ASC(Comparator.comparingLong(Transaction::getAmount)),
    AMOUNT_DESC(Comparator.comparingLong(Transaction::getAmount).reversed());

    private final Comparator<Transaction> comparator;

    SortOption(Comparator<Transaction> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Transaction> getComparator() {
        return comparator;
    }
}
