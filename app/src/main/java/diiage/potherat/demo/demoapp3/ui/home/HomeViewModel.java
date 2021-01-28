package diiage.potherat.demo.demoapp3.ui.home;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import javax.inject.Inject;
import diiage.potherat.demo.demoapp3.dal.repository.QuoteRepository;
import diiage.potherat.demo.demoapp3.model.Quote;

public class HomeViewModel extends ViewModel {

    private QuoteRepository _quoteRepository;

    private MutableLiveData<String> mQuotesCountText;

    private MutableLiveData<String> mDistinctSourcesCountText;

    private MutableLiveData<Quote> mLastQuote;

    @Inject
    @ViewModelInject
    public HomeViewModel(QuoteRepository quoteRepository) {
        mQuotesCountText = new MutableLiveData<>();
        mQuotesCountText.setValue("Nombre de citations : 0");

        mDistinctSourcesCountText = new MutableLiveData<>();
        mDistinctSourcesCountText.setValue("Nombre de sources distinctes : 0");

        mLastQuote = new MutableLiveData<>();

        _quoteRepository = quoteRepository;
    }

    public LiveData<String> getQuotesCountText() { return mQuotesCountText; }

    public LiveData<String> getDistinctSourcesCountText() { return mDistinctSourcesCountText; }

    public LiveData<Quote> getLastQuote() { return mLastQuote; }

    public void loadQuotesCount() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Long count = _quoteRepository.count();
                mQuotesCountText.postValue("Nombre de citations : " + count.toString());
            }
        });

        thread.start();
    }

    public void loadDistinctSourcesCount() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Long count = _quoteRepository.countDistinctSources();
                mDistinctSourcesCountText.postValue("Nombre de sources distinctes : " + count.toString());
            }
        });

        thread.start();
    }

    public void loadLastQuote() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Quote quote = _quoteRepository.getLastQuote();
                mLastQuote.postValue(quote);
            }
        });

        thread.start();
    }
}