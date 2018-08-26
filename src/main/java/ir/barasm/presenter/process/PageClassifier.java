package ir.barasm.presenter.process;

import com.google.common.io.Files;
import de.daslaboratorium.machinelearning.classifier.Classifier;
import de.daslaboratorium.machinelearning.classifier.bayes.BayesClassifier;
import ir.barasm.Helper;
import ir.barasm.Variables;
import ir.barasm.presenter.IService;
import ir.barasm.presenter.queue.QueueManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class PageClassifier implements IService {
    private Classifier<String, String> nBayes = new BayesClassifier<>();
    private String[] trainCases;
    private String[] mainCases;
    private QueueManager queueManager;

    public PageClassifier(QueueManager queueManager) {
        this.queueManager = queueManager;
    }

    public void init() {
        Variables.categories = Helper.getSubdirectories(Variables.trainDataDirectory);
        ArrayList<String> categoryUrls;
        for (int i = 0; i < 15; i++) {
            categoryUrls = Helper.readFile(Variables.trainDataDirectory + Variables.categories[i] + ".txt");
            for (String url : categoryUrls) {
                try {
                    Document doc = Jsoup.connect(url).get();
                    String text = doc.wholeText();
                    for (String stopword : Helper.getLanguageStopwords("en")) {
                        //TODO Tokenize text
                        text.replaceAll(stopword, null);
                    }
                    //TODO stemming and train classifier
                } catch (IOException iox) {
                    iox.printStackTrace();
                }
            }
        }
    }

    @Override
    public void execute() {
        //stopwords deletion
        //stem
        //classify
    }
}
