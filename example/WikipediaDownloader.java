package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Date;
import java.util.Scanner;

public class WikipediaDownloader implements Runnable{
    private String keyword;
    private String result;
    private String response = null;
    private String img = null;

    public WikipediaDownloader(String keyword){
        this.keyword = keyword;
    }
    public void run() {
        //get clean keyword!
        //get url for wikipedia
        //make a get request to wikipedia
        //parse the useful result using jsoup
        //show the result
        String result = null;
        String img = "";
        //step 1
        if (this.keyword == null || this.keyword.length() == 0) return;
        this.keyword = this.keyword.trim().replaceAll("[ ]+", "_");

        //step 2
        String wikiUrl = getWikipediaUrlForQuery(this.keyword);
        try {
            //step 3
            String response = "";
            String wikipediaResponse = HttpURLConnectionExample.setGet(wikiUrl);

            //System.out.println(wikipediaResponse);

            //step 4
            Document document = Jsoup.parse(wikipediaResponse, "https://en.wikipedia.org");

            Elements childElements = document.body().select(".mw-parser-output > *");
            int state = 0;
            //response = "";
            for (Element childElement : childElements) {
                //System.out.println(childElement);
                if (state == 0) {
                    if (childElement.tagName().equals("table")) {
                        state = 1;
                    }
                } else if (state == 1) {
                    if (childElement.tagName().equals("p")) {
                        state = 2;
                        result = childElement.text();
                        //System.out.println(result);
                        break;
                    }
                }
            }
            try{
                img = document.body().select(".infobox img").attr("src");
            }catch(Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //this.result = response;

        if(img.startsWith("//")){
            img = "https:"+img;
        }

        WikiResult wikiResult = new WikiResult(this.keyword, result, img);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String finalResult = gson.toJson(wikiResult);
        System.out.println(finalResult);

    }


    private String getWikipediaUrlForQuery(String cleanKeyword) {
        return "https://en.wikipedia.org/wiki/"+cleanKeyword;
    }

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager(20);

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name to search");
        String s = sc.nextLine();

        //String arr[] = {"India", "United_States"};

        System.out.println("This side is Balabhadruni Revanth");
        System.out.println("Running Wikipedia Downloader at"+new Date().toString());

        /*for(String x: arr) {
            WikipediaDownloader wiki = new WikipediaDownloader(x);
            taskManager.waitTillQueueIsFreeAndAddTask(wiki);
        }*/
        WikipediaDownloader wiki = new WikipediaDownloader(s);
        taskManager.waitTillQueueIsFreeAndAddTask(wiki);
    }

}

