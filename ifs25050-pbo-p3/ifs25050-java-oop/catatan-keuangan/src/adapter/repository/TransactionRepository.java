package adapter.repository;

import domain.entity.Transaction;
import domain.repository.ITransactionRepository;

public class TransactionRepository extends InMemoryRepository<Transaction, Integer> implements ITransactionRepository {
    private int nextId = 1;

    @Override
    public Transaction save(Transaction transaction) {
        Transaction saved;
        if (transaction.getId() == 0) {
            saved = new Transaction(nextId, transaction.getDescription(), transaction.getAmount(), transaction.getType());
            storage.put(nextId, saved);
            nextId++;
        } else {
            saved = transaction;
            storage.put(transaction.getId(), saved);
        }
        return saved;
    }
}
