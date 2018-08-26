package ir.barasm.presenter.process;

import ir.barasm.Helper;
import ir.barasm.Variables;
import ir.barasm.presenter.IService;
import ir.barasm.presenter.queue.QueueManager;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberExtractor implements IService {
    private QueueManager queueManager;

    public PhoneNumberExtractor(QueueManager queueManager) {
        this.queueManager = queueManager;
    }

    @Override
    public void execute() {
        ArrayList<String> htmls = queueManager.getQueue().getHtmls();
        for (String html : htmls) {
            Matcher m = Pattern.compile("\\(('+'|00)?([0-9]{1,3})[-. ]?\\)?(([0-9]{3,4})[-. ]?)(([0-9]{3})[-. ]?)([0-9]{4})").matcher(html);
            while (m.find()) {
                Helper.writeToFile(Variables.crawlStorageFolder + Variables.extractedPhoneNumbersFile, m.group());
            }
        }
    }
}
