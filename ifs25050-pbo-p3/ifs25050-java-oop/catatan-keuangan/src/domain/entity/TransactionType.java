package domain.entity;

public enum TransactionType {
    INCOME("Pemasukan"),
    EXPENSE("Pengeluaran");

    private final String displayName;

    TransactionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
