import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Threads  extends Thread{
	Elements links;
	int depth;
	static int success=0;
	static int faults=0;
	double time;
	public Threads (Elements links,int depth) {
		this.links=links;
		this.depth=depth;
	}
	public static boolean checkValid(String site) {
		Document doc;
		try {
			doc = Jsoup.connect(site).get();
			System.out.println(doc.title());
			success++;
		} catch (Exception e) {
			faults++;
			return false;
		}
		return true;
	}
	public Elements getLinks(String site) {
		Document doc;
		if(checkValid(site)) {
			try {
				doc=Jsoup.connect(site).get();
				Elements links = doc.select("a[href]");
				for(Element link :links) {
	            	System.out.println("\nlink : " + link.attr("href")+"\n"+"text : " + link.text()+"\n");
	            }
				if(depth!=0) {
					depth--;
					for(Element link : links) {
						getLinks(link.attr("href"));
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public void run() {
		double startTime = System.nanoTime();
		depth--;
		for(Element link : links) {
			System.out.println("\nlink : " + link.attr("href")+"\n"+"text : " + link.text()+"\n");
			getLinks(link.attr("href"));
		}
		System.out.println(Threads.success+" "+Threads.faults);
		time = (System.nanoTime() - startTime);
	}
}
