package ir.barasm.presenter.queue;

import ir.barasm.model.Queue;

public class QueueManager {
    private Queue queue;

    public QueueManager(Queue queue) {
        this.queue = queue;
    }

    public Queue getQueue() {
        return queue;
    }
}