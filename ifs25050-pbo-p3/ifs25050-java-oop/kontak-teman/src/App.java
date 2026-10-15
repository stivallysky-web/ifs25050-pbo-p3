import adapter.presenter.ContactPresenter;
import adapter.repository.ContactRepository;
import domain.repository.IContactRepository;
import framework.view.ContactView;
import usecase.ContactUseCase;

public class App {
    public static void main(String[] args) {
        IContactRepository repository = new ContactRepository();
        ContactUseCase useCase = new ContactUseCase(repository);
        ContactPresenter presenter = new ContactPresenter();
        ContactView view = new ContactView(useCase, presenter);
        view.show();
    }
}
