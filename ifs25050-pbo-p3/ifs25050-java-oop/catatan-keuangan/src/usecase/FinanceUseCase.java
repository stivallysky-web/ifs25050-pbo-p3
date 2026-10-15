package usecase;

import domain.entity.SortOption;
import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.ITransactionRepository;
import java.util.ArrayList;
import java.util.List;

public class FinanceUseCase {
    private final ITransactionRepository repository;

    public FinanceUseCase(ITransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction addTransaction(String description, long amount, TransactionType type) throws ValidationException {
        if (description == null || description.isBlank()) {
            throw new ValidationException("Keterangan tidak boleh kosong");
        }
        if (amount <= 0) {
            throw new ValidationException("Jumlah tidak valid!");
        }
        Transaction transaction = new Transaction(0, description, amount, type);
        return repository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public List<Transaction> searchTransactions(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return repository.findAll();
        }
        String lower = keyword.toLowerCase();
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : repository.findAll()) {
            if (t.getDescription().toLowerCase().contains(lower)) {
                result.add(t);
            }
        }
        return result;
    }

    public List<Transaction> sortTransactions(SortOption option) {
        List<Transaction> list = new ArrayList<>(repository.findAll());
        if (option != null && option.getComparator() != null) {
            list.sort(option.getComparator());
        }
        return list;
    }

    public void deleteTransaction(int id) throws EntityNotFoundException {
        if (!repository.deleteById(id)) {
            throw new EntityNotFoundException("Gagal menghapus transaksi dengan ID: " + id + ".");
        }
    }

    public long getBalance() {
        long balance = 0;
        for (Transaction t : repository.findAll()) {
            if (t.getType() == TransactionType.INCOME) {
                balance += t.getAmount();
            } else if (t.getType() == TransactionType.EXPENSE) {
                balance -= t.getAmount();
            }
        }
        return balance;
    }
}
