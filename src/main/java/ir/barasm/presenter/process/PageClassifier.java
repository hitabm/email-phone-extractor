package ir.barasm.presenter.process;

import de.daslaboratorium.machinelearning.classifier.Classifier;
import de.daslaboratorium.machinelearning.classifier.bayes.BayesClassifier;
import ir.barasm.Helper;
import ir.barasm.presenter.IService;
import ir.barasm.presenter.queue.QueueManager;

public class PageClassifier implements IService {
    private Classifier<String, String> nBayes = new BayesClassifier<>();
    private String[] trainCases;
    private String[] mainCases;
    private QueueManager queueManager;

    public PageClassifier(QueueManager queueManager) {
        this.queueManager = queueManager;
    }

    public void init() {
        for (String text : queueManager.getQueue().getTexts()) {
            for (String stopword : Helper.getLanguageStopwords("en")) {
                //TODO Tokenize text
                text.replaceAll(stopword, null);
            }
        }
        //stem
        //train
    }

    @Override
    public void execute() {
        //stopwords deletion
        //stem
        //classify
    }
}
