package ir.barasm.presenter.concurrent;

import ir.barasm.presenter.IService;

public abstract class Agent extends Thread {

    private IService service;

    public Agent(IService service) {
        this.service = service;
    }

    public void run() {
        service.execute();
    }

}
