import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Checker {
	static int success=0;
	static int faults=0;
	static int finished=0;
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	static JLabel stats=new JLabel("Time1: "+0+" Time2: "+0+" Time4: "+0+" Time6:= "+0+" Time8: "+0+" Time10: "+0+" Time12: "+0+" Time14: "+0+" Time16 "+0);
	static JLabel errors = new JLabel("Successes= "+success+" Faults= "+faults);
	public static Elements getFirstLinks(String site) {
		Document doc = Threads.checkValid(site);
		if(doc!=null) {
			Elements links = doc.select("a[href]");
			return links;
		}
		return null;
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
    	String[] items ={"1","2","4","6","8","10","12","14","16"};
    	JComboBox threadsNum = new JComboBox(items);
    	threadsNum.setBounds(265,260,50,30);
    	threadsNum.setFont(new Font("Serif", Font.PLAIN, 18));
    	JButton go = new JButton("Go");
    	go.setBounds(240, 300, 100, 50);
    	go.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
						success=0;faults=0;finished=0;
						int threadCount=Integer.parseInt(threadsNum.getSelectedItem().toString());
						switch(threadCount) {
						case 1:
							Threads.time1=0;
							break;
						case 2:
							Threads.time2=0;
							break;
						case 4:
							Threads.time4=0;
							break;
						case 6:
							Threads.time6=0;
							break;
						case 8:
							Threads.time8=0;
							break;
						case 10:
							Threads.time10=0;
							break;
						case 12:
							Threads.time12=0;
							break;
						case 14:
							Threads.time14=0;
							break;
						case 16:
							Threads.time16=0;
							break;
						}
						ArrayList <Threads> threads=new ArrayList<Threads>();
						Elements links=getFirstLinks(link.getText());
						Threads.originalLink=link.getText();
						ArrayList <Elements> threadLinks=new ArrayList<Elements>();
						int depthNum=0;
						try {
							depthNum=Integer.parseInt(depth.getText());
						}
						catch(Exception x) {
							depthNum=-20;
						}
						if(links!=null&&depthNum!=-20) {
							for(int i=0;i<threadCount;i++) {
								Elements temp = new Elements();
								Threads tempThread = new Threads(threadCount);
								threads.add(tempThread);
								threadLinks.add(temp);
							}
							int k=0;
							for(Element link:links) {
								threadLinks.get(k).add(link);
								k++;
								if(k==threadCount){
									k=0;
								}
							}
							for(int i=0;i<threadCount;i++) {
								threads.get(i).links=threadLinks.get(i);
								threads.get(i).depth=depthNum;
								threads.get(i).start();
							}
						}
						while(finished !=threadCount) {
							System.out.println(finished);
						}
						stats.setText(("Time1: "+df2.format(Threads.time1)+" Time2: "+df2.format(Threads.time2)+" Time4: "+df2.format(Threads.time4)+" Time6: "+df2.format(Threads.time6)+" Time8: "+df2.format(Threads.time8)+" Time10: "+df2.format(Threads.time10)+" Time12: "+df2.format(Threads.time12)+" Time14: "+df2.format(Threads.time14)+" Time16: "+df2.format(Threads.time16)));
						errors.setText("Successes= "+success+" Faults= "+faults);
					}
			    });
    	go.setFont(new Font("Serif", Font.PLAIN, 18));
    	stats.setBounds(0, 380, 900, 50);
    	stats.setFont(new Font("Serif", Font.PLAIN, 18));
    	errors.setBounds(200, 430, 500, 20);
    	errors.setFont(new Font("Serif", Font.PLAIN, 18));
    	panel.add(go);panel.add(depth);panel.add(link);panel.add(threadsNum);panel.add(depthLbl);panel.add(linkLbl);panel.add(title);panel.add(stats);panel.add(errors);
    	panel.setLayout(null);
    	frame.add(panel);
    	frame.setSize(900,500);
    	frame.setVisible(true);
	}
	public static void main(String[] args) {
		createGUI();
	}
}
