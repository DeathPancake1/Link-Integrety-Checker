import java.io.FileNotFoundException;
import java.io.IOException;

import org.jsoup.select.Elements;

public class Threads extends Thread {
	String name;
	public Threads(String name) {
		this.name=name;
	}
	public void run() {
		Checker.checkValid(name);
		try {
			Checker.checkExists(name);
			Elements links=Checker.getLinks(name);
			Checker.writeLinks(links);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("alo");
		}
	}
}