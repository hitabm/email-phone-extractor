package ir.barasm;

public class Variables {
    public static int queueSize = 10;
    public static int maxPagesToFetch = 300;
    public static int maxDepthOfCrawling = 10;
    public static int numberOfCrawlers = 7;
    public static String crawlStorageFolder = "D:/CrawlFolder/";
    public static String seedsFile = "seeds.txt";
    public static String extractedEmailsFile = "emails.txt";
    public static String extractedPhoneNumbersFile = "phones.txt";
    public static String stopwordsFile = "src/main/java/ir/barasm/stopwords-all.json";
    public static String trainDataDirectory = "D:/ClassifierTrainData";
    public static String[] categories;
}
