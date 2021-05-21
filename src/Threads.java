
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Threads  extends Thread{
	Elements links;
	int depth;
	double time;
	int m;
	static double time1=0,time2=0,time4=0,time6=0,time8=0,time10=0,time12=0,time14=0,time16=0;
	static String originalLink;
	public Threads (int m) {
		this.m=m;
	}
	public static Document checkValid(String site) {
		Document doc = null;
		try {
			if(site.startsWith("http")) {
				doc = Jsoup.connect(site).timeout(5000).get();
				if(doc==null) {
					Checker.faults++;
					return null;
				}
				Checker.success++;
			}
			else {
				doc = Jsoup.connect(originalLink+site).timeout(5000).get();
				if(doc==null) {
					Checker.faults++;
					return null;
				}
				Checker.success++;
			}
			return doc;
		} catch (Exception e) {
			Checker.faults++;
			return null;
		}
	}
	public void getLinks(String site, int depth) {
		Document doc;
		depth=depth-1;
		doc = checkValid(site);
		if(doc!=null) {
			Elements links = doc.select("a[href]");
			if(depth<=0) {
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
		if(depth>0) {
			for(Element link : links) {
				getLinks(link.attr("href"),depth);
			}
		}else {
			for(Element link : links) {
				checkValid(link.attr("href"));
			}
		}
		time = (System.nanoTime() - startTime)/1000000000;
		switch(m) {
		case 1:
			if(time>time1) {
				time1=time;
			}
			break;
		case 2:
			if(time>time2) {
				time2=time;
			}
			break;
		case 4:
			if(time>time4) {
				time4=time;
			}
			break;
		case 6:
			if(time>time6) {
				time6=time;
			}
			break;
		case 8:
			if(time>time8) {
				time8=time;
			}
			break;
		case 10:
			if(time>time10) {
				time10=time;
			}
			break;
		case 12:
			if(time>time12) {
				time12=time;
			}
			break;
		case 14:
			if(time>time14) {
				time14=time;
			}
			break;
		case 16:
			if(time>time16) {
				time16=time;
			}
			break;
		}
		Checker.finished++;
	}
}
