import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Checker {
	public static Elements getFirstLinks(String site) {
		Document doc = Threads.checkValid(site);
		if(doc!=null) {
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
    	String[] items ={"1","2","4","6","8","10"};
    	JComboBox threadsNum = new JComboBox(items);
    	threadsNum.setBounds(265,260,50,30);
    	threadsNum.setFont(new Font("Serif", Font.PLAIN, 18));
    	JButton go = new JButton("Go");
    	go.setBounds(240, 300, 100, 50);
    	go.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
						Threads.success=0;Threads.faults=0;
						ArrayList <Threads> threads=new ArrayList<Threads>();
						Elements links=getFirstLinks(link.getText());
						ArrayList <Elements> threadLinks=new ArrayList<Elements>();
						int depthNum=0;
						try {
							depthNum=Integer.parseInt(depth.getText());
						}
						catch(Exception x) {
							depthNum=-20;
						}
						if(links!=null&&depthNum!=-20) {
							for(int i=0;i<Integer.parseInt(threadsNum.getSelectedItem().toString());i++) {
								Elements temp = new Elements();
								Threads tempThread = new Threads();
								threads.add(tempThread);
								threadLinks.add(temp);
							}
							int k=0;
							for(Element link:links) {
								threadLinks.get(k).add(link);
								k++;
								if(k==Integer.parseInt(threadsNum.getSelectedItem().toString())){
									k=0;
								}
							}
							for(int i=0;i<Integer.parseInt(threadsNum.getSelectedItem().toString());i++) {
								threads.get(i).links=threadLinks.get(i);
								threads.get(i).depth=depthNum;
								threads.get(i).start();
							}
						}
			    	}
			    });
    	go.setFont(new Font("Serif", Font.PLAIN, 18));
    	JButton stats = new JButton("View Statistics");
    	stats.setBounds(215, 380, 150, 50);
    	stats.setFont(new Font("Serif", Font.PLAIN, 18));
    	panel.add(go);panel.add(depth);panel.add(link);panel.add(threadsNum);panel.add(depthLbl);panel.add(linkLbl);panel.add(title);panel.add(stats);
    	panel.setLayout(null);
    	frame.add(panel);
    	frame.setSize(600,500);
    	frame.setVisible(true);
	}
	public static void main(String[] args) {
		createGUI();
	}

}
