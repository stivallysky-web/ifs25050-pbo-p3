import adapter.presenter.GuestPresenter;
import adapter.repository.GuestRepository;
import domain.repository.IGuestRepository;
import framework.view.GuestView;
import usecase.GuestUseCase;

public class App {
    public static void main(String[] args) {
        IGuestRepository repository = new GuestRepository();
        GuestUseCase useCase = new GuestUseCase(repository);
        GuestPresenter presenter = new GuestPresenter();
        GuestView view = new GuestView(useCase, presenter);
        view.show();
    }
}
