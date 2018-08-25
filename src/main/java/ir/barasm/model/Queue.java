package ir.barasm.model;

import java.util.ArrayList;

public class Queue {
    private ArrayList<String> htmls;
    private ArrayList<String> texts;
    private Object sync = new Object();

    public Queue() {
        htmls = new ArrayList<>();
        texts = new ArrayList<>();
    }

    public ArrayList<String> getHtmls() {
        synchronized (sync) {
            return htmls;
        }
    }

    public ArrayList<String> getTexts() {
        synchronized (sync) {
            return texts;
        }
    }

    public void addHtml(String html) {
        synchronized (sync) {
            htmls.add(html);
        }
    }

    public void addText(String text) {
        synchronized (sync) {
            htmls.add(text);
        }
    }

    public int getSize() {
        int size = 0;
        synchronized (sync) {
            size = htmls.size();
        }
        return size;
    }

    public void emptyQueue() {
        htmls.clear();
    }
}
