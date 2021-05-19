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
	public Threads () {
		
	}
	public Threads (Elements links,int depth) {
		this.links=links;
		this.depth=depth;
	}
	public static Document checkValid(String site) {
		Document doc;
		try {
			doc = Jsoup.connect(site).get();
			success++;
			return doc;
		} catch (Exception e) {
			faults++;
			return null;
		}
	}
	public void getLinks(String site, int depth) {
		Document doc;
		depth=depth-1;
		doc = checkValid(site);
		if(doc!=null) {
			Elements links = doc.select("a[href]");
			if(depth==0) {
				for(Element link : links) {
					checkValid(link.attr("href"));
				}
				return;
			}
			else {
				for(Element link : links) {
					getLinks(link.attr("href"),depth-1);
				}
			}
		}
	}
	public void run() {
		double startTime = System.nanoTime();
		depth--;
		if(depth!=0) {
			for(Element link : links) {
				getLinks(link.attr("href"),depth);
			}
		}else {
			for(Element link : links) {
				checkValid(link.attr("href"));
			}
		}
		time = (System.nanoTime() - startTime);
		System.out.println(Threads.success+" "+Threads.faults+" "+time/1000000000);
	}
}
