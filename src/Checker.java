import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Checker {
	public static Elements getFirstLinks(String site) {
		Document doc = null;
		if(Threads.checkValid(site)) {
			try {
				doc=Jsoup.connect(site).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Elements links = doc.select("a[href]");
			return links;
		}
		return null;
	}
	
	public static void createGUI() {
		File file = new File("links.txt");
		file.delete();
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
    	go.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
						Threads t1 = new Threads(getFirstLinks(link.getText()),Integer.parseInt(depth.getText()));
						t1.start();
			        }  
			    });
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
