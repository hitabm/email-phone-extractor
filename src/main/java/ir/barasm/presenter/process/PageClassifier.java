package ir.barasm.presenter.process;

import de.daslaboratorium.machinelearning.classifier.Classifier;
import de.daslaboratorium.machinelearning.classifier.bayes.BayesClassifier;
import ir.barasm.Helper;
import ir.barasm.Variables;
import ir.barasm.presenter.IService;
import ir.barasm.presenter.queue.QueueManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class PageClassifier implements IService {
    private Classifier<String, String> nBayes = new BayesClassifier<>();
    private QueueManager queueManager;

    public PageClassifier(QueueManager queueManager) {
        this.queueManager = queueManager;
    }

    public void init() {
        Variables.categories = new String[Helper.getDirectoryFileCount(Variables.trainDataDirectory)];
        Variables.categories = Helper.getDirectoryFileNames(Variables.trainDataDirectory);
        ArrayList<String> categoryUrls;

        for (int i = 0; i < 15; i++) {
            categoryUrls = Helper.readFile(Variables.trainDataDirectory + Variables.categories[i]); //split("\\.(?=[^\\.]+$)");
            for (String url : categoryUrls) {
                try {
                    //retrieve html page of url and its texts
                    Document doc = Jsoup.connect(url).get();
                    String text = doc.wholeText();
                    //remove all delimiters
                    text = text.replaceAll("[-!@#$%^&*()_+=.,\\\\;:/?><\"\'| \r\n\t]+", "");

                    //remove all stopwords from text
                    text = Helper.removeStopwords(text, "en");

                    //train classifier with the category and ready form text
                    nBayes.learn(Variables.categories[i], Arrays.asList(Helper.stemAllWords(text)));
                } catch (IOException iox) {
                    iox.printStackTrace();
                }
            }
        }
    }

    @Override
    public void execute() {
        ArrayList<String> pagesText = queueManager.getQueue().getTexts();
        for (String pageText : pagesText) {
            //remove all stopwords from text
            pageText = Helper.removeStopwords(pageText, "en");
            //classify the page by its text
            System.out.println(nBayes.classify(Arrays.asList(pageText)).getCategory());
        }
        nBayes.setMemoryCapacity(500);
    }
}
