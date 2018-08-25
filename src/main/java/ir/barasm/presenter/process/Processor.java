package ir.barasm.presenter.process;

import ir.barasm.presenter.IService;
import ir.barasm.presenter.concurrent.Mutex;
import ir.barasm.presenter.queue.QueueManager;

public class Processor implements IService, IProcessorEvent {
    private QueueManager queueManager;
    private Mutex mutex;
    private EmailExtractor emailExtractor;
    private PhoneNumberExtractor phoneNumberExtractor;
    private PageClassifier pageClassifier;
    private int state = 0;

    public Processor(QueueManager queueManager) {
        this.queueManager = queueManager;
        mutex = new Mutex(true);
        emailExtractor = new EmailExtractor(queueManager);
        phoneNumberExtractor = new PhoneNumberExtractor(queueManager);
        pageClassifier = new PageClassifier(queueManager);
    }

    public void notifyDataReady() {
        mutex.unlock();
    }

    public int getState() {
        return state;
    }

    public void execute() {
        pageClassifier.init();
        while (true) {
            mutex.lock();
            state = 1;
            System.out.println("[!] INFO: Grabbed data and goto process fuzz!");
            pageClassifier.execute();
            emailExtractor.execute();
            phoneNumberExtractor.execute();
            queueManager.getQueue().emptyQueue();
            state = 0;
        }
    }
}