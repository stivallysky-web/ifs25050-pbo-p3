import adapter.presenter.ItemPresenter;
import adapter.repository.ItemRepository;
import domain.repository.IItemRepository;
import framework.view.ItemView;
import usecase.ItemUseCase;

public class App {
    public static void main(String[] args) {
        IItemRepository repository = new ItemRepository();
        ItemUseCase useCase = new ItemUseCase(repository);
        ItemPresenter presenter = new ItemPresenter();
        ItemView view = new ItemView(useCase, presenter);
        view.show();
    }
}
