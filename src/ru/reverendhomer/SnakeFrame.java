package ru.reverendhomer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class SnakeFrame extends JFrame {
	private final Container root;
	static SnakePanel sPanel;

	public SnakeFrame() {
		super("JSnake");
		root = getContentPane();
		sPanel = new SnakePanel();
		root.add(sPanel);
		this.addKeyListener(new KListener());

		JMenuBar menuBar = new JMenuBar();
		JMenu help = new JMenu("Help");
		JMenu gameMenu = new JMenu("Game");
		JMenuItem about = new JMenuItem("About");
		about.addActionListener((ActionEvent arg0) -> {
                    SwingUtilities.invokeLater(new AboutRunnable());
                });
		JMenuItem newGame = new JMenuItem("New game");
		newGame.addActionListener((ActionEvent e) -> {
                    SwingUtilities.invokeLater(new AskRunnable());
                });
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener((ActionEvent e) -> {
                    System.exit(0);
                });
		help.add(about);
		gameMenu.add(newGame);
		gameMenu.add(quit);
		menuBar.add(gameMenu);
		menuBar.add(help);
		this.setJMenuBar(menuBar);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setResizable(false);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("res/snake.gif"));
		this.pack();
		this.setLocationRelativeTo(null);

	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
                    new SnakeFrame().setVisible(true);
                });
	}
}
class AboutRunnable implements Runnable {
	@Override
	public void run() {
		JFrame aboutFrame = new JFrame("About");
		JPanel about = new JPanel();
		LayoutManager manager = new BorderLayout();
		about.setLayout(manager);
		JLabel copyright, image;
		JTextArea program;
		ImageIcon icon = new ImageIcon("res/about.jpg");
		image = new JLabel(icon);
		copyright = new JLabel("© Преподобный Гомер", SwingConstants.RIGHT);
		program = new JTextArea("JSnake v 0.1\n" +
				"Игра, созданная нашей компанией \"Программы и компьютеры\",\n" +
				" не содержит крови и насилия, так что отлично подойдёт\n" +
				" как молодому игроку, так и представителю старшего поколения.\n" +
				" Цель игры - накормить змейку, не врезаясь в стены или самого себя.\n" +
				" Игра имеет три уровня сложности на выбор.\n При воздержании игрока от выбора" +
				" включается самый сложный уровень.\n" +
				" Приятной игры!");
		program.setEditable(false);
		about.add(image, BorderLayout.PAGE_START);
		about.add(program, BorderLayout.CENTER);
		about.add(copyright, BorderLayout.PAGE_END);
		aboutFrame.setContentPane(about);
		aboutFrame.setResizable(false);
		aboutFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("res/snake.gif"));
		aboutFrame.pack();
		aboutFrame.setLocationRelativeTo(null);
		aboutFrame.setVisible(true);
	}
}
class AskRunnable implements Runnable {
	@Override
	public void run() {
		final JFrame askFrame = new JFrame("Выберите уровень сложности");
		JPanel panel = new JPanel();
		final JRadioButton easy = new JRadioButton("Easy");
		final JRadioButton medium = new JRadioButton("Medium");
		final JRadioButton hard = new JRadioButton("Hard");
		JButton ok = new JButton("OK");
		ok.addActionListener((ActionEvent arg0) -> {
                    SnakePanel.snake = new Snake();
                    askFrame.setVisible(false);
                    if (easy.isSelected()) {
                        new Thread(new EasyRunnable()).start();
                    }
                    else if (medium.isSelected()) {
                        new Thread(new MediumRunnable()).start();
                    }
                    else {
                        new Thread(new HardRunnable()).start();
                    }
                });
		
		LayoutManager lm = new BorderLayout();
		panel.setLayout(lm);
		panel.add(easy, BorderLayout.LINE_START);
		panel.add(medium, BorderLayout.CENTER);
		panel.add(hard, BorderLayout.LINE_END);
		panel.add(ok, BorderLayout.PAGE_END);
		askFrame.setContentPane(panel);
		askFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("res/snake.gif"));
		askFrame.pack();
		askFrame.setLocationRelativeTo(null);
		askFrame.setResizable(false);
		askFrame.setVisible(true);
	}
	
}
class EasyRunnable implements Runnable {

	@Override
	public void run() {
		while (!Snake.gameIsOvered()) {
			SnakePanel.snake.move(SnakePanel.snake.head.direction);
			SnakeFrame.sPanel.repaint();
			try {
				Thread.sleep(Constants.EASY_REPAINT);
			} catch (InterruptedException e) {
			}
		}
	}
	
}
class MediumRunnable implements Runnable {
	@Override
	public void run() {
		while (!Snake.gameIsOvered()) {
			SnakePanel.snake.move(SnakePanel.snake.head.direction);
			SnakeFrame.sPanel.repaint();
			try {
				Thread.sleep(Constants.MEDIUM_REPAINT);
			} catch (InterruptedException e) {
			}
		}
	}
	
}
class HardRunnable implements Runnable {
	@Override
	public void run() {
		while (!Snake.gameIsOvered()) {
			SnakePanel.snake.move(SnakePanel.snake.head.direction);
			SnakeFrame.sPanel.repaint();
			try {
				Thread.sleep(Constants.HARD_REPAINT);
			} catch (InterruptedException e) {
			}
		}
	}	
}

class KListener implements KeyListener {

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
                Direction direct = SnakePanel.snake.head.direction;
		switch (key) {
		case KeyEvent.VK_UP:
			if (direct == Direction.UP || direct == Direction.DOWN) {
				return;
                        }
			SnakePanel.snake.moveUp();
			break;
				
		case KeyEvent.VK_DOWN:
			if (direct == Direction.UP || direct == Direction.DOWN) {
				return;
                        }
			SnakePanel.snake.moveDown();
			break;
			
		case KeyEvent.VK_LEFT:
			if (direct == Direction.LEFT || direct == Direction.RIGHT) {
				return;
                        }
			SnakePanel.snake.moveLeft();
			break;
			
		case KeyEvent.VK_RIGHT:
			if (direct == Direction.LEFT || direct == Direction.RIGHT) {
				return;
                        }
			SnakePanel.snake.moveRight();
			break;
			
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
	
}