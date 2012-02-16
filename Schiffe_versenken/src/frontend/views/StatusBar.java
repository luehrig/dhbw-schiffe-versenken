package frontend.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.ActionController;

public class StatusBar extends JPanel {

	private class MessageThread extends Thread {

		private String[] queue = new String[3];

		@Override
		public void run() {
			try {
				while (true) {

					if (!this.isEmpty()) {
						infoLabel.setText(this.queue[0]);

						for (int i = 1; i < this.queue.length; i++) {
							this.queue[i - 1] = this.queue[i];
						}
						queue[queue.length - 1] = null;
						Thread.sleep(8000);
					} else
						infoLabel.setText("");
				}
			} catch (InterruptedException e) {
			}
		}

		public void addMessage(String text) {
			for (int i = 0; i < this.queue.length; i++) {
				if (this.queue[i] == null) {
					this.queue[i] = text;
					return;
				}
			}
		}

		public boolean isEmpty() {
			if (this.queue[0] == null)
				return true;
			return false;
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1210178083917538284L;

	private static Font STATUS_FONT = new Font("Arial", Font.BOLD, 12);

	private JLabel infoLabel;
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
		infoLabel.setBounds(10, 0, 240, 30);
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
		this.messageThread = new MessageThread();
		this.messageThread.start();

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
