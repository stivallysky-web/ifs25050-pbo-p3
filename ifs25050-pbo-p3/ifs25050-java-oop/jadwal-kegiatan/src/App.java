import adapter.presenter.ActivityPresenter;
import adapter.repository.ActivityRepository;
import domain.repository.IActivityRepository;
import framework.view.ActivityView;
import usecase.ActivityUseCase;

public class App {
    public static void main(String[] args) {
        IActivityRepository repository = new ActivityRepository();
        ActivityUseCase useCase = new ActivityUseCase(repository);
        ActivityPresenter presenter = new ActivityPresenter();
        ActivityView view = new ActivityView(useCase, presenter);
        view.show();
    }
}
