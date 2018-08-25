package ir.barasm.presenter.grabber;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import ir.barasm.Helper;
import ir.barasm.Variables;
import ir.barasm.presenter.IService;
import ir.barasm.presenter.process.IProcessorEvent;
import ir.barasm.presenter.queue.QueueManager;

import java.util.ArrayList;

public class Grabber implements IService {
    private IProcessorEvent processorEvent;
    private QueueManager queueManager;

    public Grabber(IProcessorEvent processorEvent, QueueManager queueManager) {
        this.processorEvent = processorEvent;
        this.queueManager = queueManager;
    }

    public void execute() {
        try {
            ArrayList<String> urls = Helper.readFile(Variables.workingDirectory + Variables.inputFileName);
            int numberOfCrawlers = Variables.numberOfCrawlers;

            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder(Variables.workingDirectory);

            /*
             * Instantiate the controller for this crawl.
             */
            PageFetcher pageFetcher = new PageFetcher(config);
            RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
            RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
            CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

            /*
             * For each crawl, you need to add some seed urls. These are the first
             * URLs that are fetched and then the crawler starts following links
             * which are found in these pages
             */
            for (String url : urls) {
                controller.addSeed(url);
            }

            /*
             * Start the crawl. This is a blocking operation, meaning that your code
             * will reach the line after this only when crawling is finished.
             */
            PageCrawler.configure(processorEvent, queueManager);
            controller.start(PageCrawler.class, numberOfCrawlers);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
