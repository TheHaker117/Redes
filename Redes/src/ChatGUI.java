import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class ChatGUI extends JFrame implements ActionListener{
	private JTextArea t_history, t_message;
	private JButton b_send;
	private JScrollPane sp_history;
	private ChatHost host;
	private String username;
	
	public ChatGUI(){
		super();
		init();
		config();
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				host.startListening();
			}
		}).start();;
	}
	
	public static void main(String[] args){
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new ChatGUI().setVisible(true);
			}
		});
	}
	
	private void init(){
		this.setTitle("Master chat");
		this.setSize(720, 480);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(WindowEvent e){
		        try{
		        	host.closeChat();
		        }
		        catch(Exception exc){exc.printStackTrace();}
		    }
		});
	}
	
	private void config(){
		t_history = new JTextArea();
		t_history.setEditable(false);
		
		sp_history = new JScrollPane(t_history);
		sp_history.setBounds(10, 10, 690, 320);
		sp_history.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		t_message = new JTextArea();
		t_message.setBounds(10, 340, 550, 100);
		t_message.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		b_send = new JButton("Send");
		b_send.setBounds(570, 340, 130, 100);
		b_send.addActionListener(this);

		host = new ChatHost(t_history);
		
		username = JOptionPane.showInputDialog("Nombre de usuario: ");
		
		this.add(sp_history);
		this.add(t_message);
		this.add(b_send);
		
	}

	@Override
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource().equals(b_send)){
			host.sendMessage(username + ":\t" + t_message.getText());
			t_message.setText("");
			
		}
		
	}
	
}
