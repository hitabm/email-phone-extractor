package ir.barasm.presenter.grabber;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import ir.barasm.Helper;
import ir.barasm.Variables;
import ir.barasm.presenter.process.IProcessorEvent;
import ir.barasm.presenter.queue.QueueManager;

import java.util.regex.Pattern;

public class PageCrawler extends WebCrawler {
    private static IProcessorEvent processorEvent;
    private static QueueManager queueManager;

    //This method is package-private
    static void configure(IProcessorEvent processorEvent, QueueManager queueManager) {
        PageCrawler.processorEvent = processorEvent;
        PageCrawler.queueManager = queueManager;
    }

    private final static Pattern FILTERS =
            Pattern.compile(".*(\\.(css|js|rss|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf|rm|smil|wmv|swf|wma|zip|rar|gz|bmp|gif|jpe?g|png|tiff?))$");

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches() && !href.contains("js") && !href.contains("css") && !href.contains("rss");
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            queueManager.getQueue().addHtml(htmlParseData.getHtml());
            queueManager.getQueue().addText(htmlParseData.getText());
        }
        if (queueManager.getQueue().getSize() >= Variables.queueSize) {
            processorEvent.notifyDataReady();
        }
    }
}
