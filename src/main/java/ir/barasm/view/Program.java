package ir.barasm.view;

import ir.barasm.model.Queue;
import ir.barasm.presenter.grabber.Grabber;
import ir.barasm.presenter.grabber.GrabberAgent;
import ir.barasm.presenter.process.Processor;
import ir.barasm.presenter.process.ProcessorAgent;
import ir.barasm.presenter.queue.QueueManager;

public class Program {

    public static void main(String[] args) {
        Queue queue = new Queue();
        QueueManager queueManager = new QueueManager(queue);
        Processor processor = new Processor(queueManager);
        Grabber grabber = new Grabber(processor, queueManager);
        GrabberAgent grabberAgent = new GrabberAgent(grabber);
        ProcessorAgent processorAgent = new ProcessorAgent(processor);
        grabberAgent.start();
        processorAgent.start();
        try {
            grabberAgent.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}