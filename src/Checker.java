
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Checker {
	static int faults = 0;
	static int success = 0;
	static int depth=0;
	public static boolean checkValid(String site) {
		Document doc;
		try {
			doc = Jsoup.connect(site).get();
			success++;
		} catch (IOException e) {
			faults++;
			return false;
		}
		return true;
	}
	public static boolean checkExists(String site) throws FileNotFoundException {
		File linksFile = new File("links.txt");
		try {
			linksFile.createNewFile();
		} catch (IOException e1) {
			System.out.println("An error occurred.");
			e1.printStackTrace();
		}
		Scanner myReader = new Scanner(linksFile);
		while (myReader.hasNextLine()) {
	    	  if(myReader.nextLine().contains(site)) {
	    		  return true;
	    	  }
		}
		return false;
	}
	public static Elements getLinks(String site) {
		Document doc;
		try {

            // need http protocol
            doc = Jsoup.connect(site).get();

            // get page title
            String title = doc.title();
            System.out.println("title : " + title);

            // get all links
            Elements links = doc.select("a[href]");
            return links;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void writeLinks(Elements links) throws IOException {
		for (Element link : links) {
			Files.write(Paths.get("links.txt"), ("\nlink : " + link.attr("href")+"\n"+"text : " + link.text()+"\n").getBytes(), StandardOpenOption.APPEND);
        }
	}
	public static void oneThread(String site) {
		Threads client1=new Threads(site);
    	client1.start();
	}
	public static void createGUI() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	JPanel panel = new JPanel();
    	JLabel title = new JLabel("Link Integrety Checker");
    	title.setBounds(180, 20, 300, 40);
    	title.setFont(new Font("Serif", Font.PLAIN, 24));
    	JLabel linkLbl = new JLabel("Link: ");
    	linkLbl.setBounds(60, 100, 80, 20);
    	linkLbl.setFont(new Font("Serif", Font.PLAIN, 18));
    	JLabel depthLbl = new JLabel("Depth: ");
    	depthLbl.setBounds(60, 200, 80, 20);
    	depthLbl.setFont(new Font("Serif", Font.PLAIN, 18));
    	JTextField link = new JTextField();
    	link.setBounds(150,90,300,50);
    	link.setFont(new Font("Serif", Font.PLAIN, 18));
    	JTextField depth = new JTextField();
    	depth.setBounds(150,190,300,50);
    	depth.setFont(new Font("Serif", Font.PLAIN, 18));
    	JButton go = new JButton("Go");
    	go.setBounds(240, 300, 100, 50);
    	go.setFont(new Font("Serif", Font.PLAIN, 18));
    	JButton stats = new JButton("View Statistics");
    	stats.setBounds(215, 380, 150, 50);
    	stats.setFont(new Font("Serif", Font.PLAIN, 18));
    	panel.add(go);panel.add(depth);panel.add(link);panel.add(depthLbl);panel.add(linkLbl);panel.add(title);panel.add(stats);
    	panel.setLayout(null);
    	frame.add(panel);
    	frame.setSize(600,500);
    	frame.setVisible(true);
	}
    public static void main(String[] args) {
    	createGUI();
    }

}