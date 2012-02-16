package frontend.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.ActionController;

public class StatusBar extends JPanel {

	/*
	 * implements a message display thread that use a queue to store messages
	 */
	private static class MessageThread extends Thread {

		private Queue<String> queue = new LinkedList<String>();

		private static MessageThread instance = null;

		private MessageThread() {

		}

		public static MessageThread getInstance() {
			if (instance == null) {
				instance = new MessageThread();
			}
			return instance;
		}

		@Override
		public void run() {
			try {
				while (true) {
					// check if message waits for display
					if (!this.queue.isEmpty()) {
						infoLabel.setText(this.queue.poll());
					}
					// clear label if NO message waits for display
					else {
						infoLabel.setText("");
					}
					// wait 8 seconds before next round starts
					Thread.sleep(8000);

				}
			} catch (InterruptedException e) {
			}
		}

		/*
		 * adds message to queue
		 */
		public void addMessage(String iv_text) {
			this.queue.add(iv_text);
			this.interrupt();
		}

		/*
		 * clear waiting messages
		 */
		protected void resetQueue() {
			this.queue.clear();
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1210178083917538284L;

	private static Font STATUS_FONT = new Font("Arial", Font.BOLD, 12);

	private static JLabel infoLabel;
	private JLabel playerLabel;
	private JLabel ipLabel;
	@SuppressWarnings("unused")
	private ActionController actController;
	private MessageThread messageThread;

	public StatusBar(ActionController actController) {

		this.actController = actController;
		this.setLayout(null);
		this.setBackground(Color.DARK_GRAY);
		this.setPreferredSize(new Dimension(600, 30));
		this.setSize(getMaximumSize());

		// set Info Label
		infoLabel = new JLabel("", JLabel.LEFT);
		infoLabel.setForeground(Color.WHITE);
		infoLabel.setBackground(Color.DARK_GRAY);
		infoLabel.setFont(STATUS_FONT);
		infoLabel.setOpaque(true);
		infoLabel.setBounds(10, 0, 350, 30);
		infoLabel.setAlignmentY(LEFT_ALIGNMENT);
		this.add(infoLabel);

		// set Label for current player
		playerLabel = new JLabel("");
		playerLabel.setForeground(Color.WHITE);
		playerLabel.setBackground(Color.DARK_GRAY);
		playerLabel.setFont(STATUS_FONT);
		playerLabel.setOpaque(true);
		playerLabel.setBounds(250, 0, 200, 30);
		this.add(playerLabel);

		// set Label for IP adress
		this.setIpAdr();

		// start message thread
		// this.messageThread = new MessageThread();
		this.messageThread = MessageThread.getInstance();
		try {
			this.messageThread.resetQueue();
			this.messageThread.start();
		} catch (IllegalThreadStateException e) {
			// message Thread still exist!
		}

	}

	public void setGeneralInfo(String info) {

		infoLabel.setFont(STATUS_FONT);
		infoLabel.setForeground(Color.WHITE);

		// add Info to message queue
		this.messageThread.addMessage(info);
	}

	public void setGameInfo(String info) {
		infoLabel.setFont(STATUS_FONT);
		infoLabel.setForeground(Color.WHITE);
		infoLabel.setText(info);
	}

	public void setError(String info) {
		infoLabel.setFont(new Font("Arial", Font.BOLD, 11));
		infoLabel.setForeground(Color.red);
		infoLabel.setText(info);

		// add info to message queue
		this.messageThread.addMessage(info);

	}

	public void setPlayer(String currentPlayerInfo) {

		playerLabel.setText(currentPlayerInfo);

	}

	public void setIpAdr() {
		ipLabel = new JLabel("", JLabel.RIGHT);
		ipLabel.setForeground(Color.WHITE);
		ipLabel.setBackground(Color.DARK_GRAY);
		ipLabel.setFont(STATUS_FONT);
		ipLabel.setOpaque(true);
		ipLabel.setBounds(400, 0, 180, 30);

		InetAddress ipAdr = null;
		try {
			ipAdr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ipLabel.setText("My IP: " + ipAdr.getHostAddress() + " ");
		this.add(ipLabel);
	}

}
