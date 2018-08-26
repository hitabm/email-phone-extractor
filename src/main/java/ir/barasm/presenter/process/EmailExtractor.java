package ir.barasm.presenter.process;

import ir.barasm.Helper;
import ir.barasm.Variables;
import ir.barasm.presenter.IService;
import ir.barasm.presenter.queue.QueueManager;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailExtractor implements IService {
    private QueueManager queueManager;

    public EmailExtractor(QueueManager queueManager) {
        this.queueManager = queueManager;
    }

    @Override
    public void execute() {
        ArrayList<String> htmls = queueManager.getQueue().getHtmls();
        for (String html : htmls) {
            Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+(@|\\[at\\])[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(html);
            while (m.find()) {
                Helper.writeToFile(Variables.crawlStorageFolder + Variables.extractedEmailsFile, m.group());
            }
        }
    }
}
