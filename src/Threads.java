import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.management.ManagementFactory;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Threads extends Thread {
	String name;
	int depth;
	double nsToS=1000000000;
	double time;
	public Threads(String name,int depth) {
		this.name=name;
		this.depth=depth;
	}
	public void run() {
		double startTime = System.nanoTime();
		Checker.getLinks(name);
		System.out.println(Checker.faults);
    	System.out.println(Checker.success);
    	time = (System.nanoTime() - startTime)/1000000000;
        System.out.println("My thread " + this.getId() + " execution time: " + time + " s");
	}
}