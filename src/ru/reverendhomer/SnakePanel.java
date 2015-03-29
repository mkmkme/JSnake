package ru.reverendhomer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import ru.reverendhomer.Snake.Link;

@SuppressWarnings("serial")
public class SnakePanel extends JPanel {

    public static Snake snake = new Snake();

    public SnakePanel() {
        this.setPreferredSize(new Dimension(Constants.FIELD_SIDE, Constants.FIELD_SIDE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Constants.FIELD_SIDE, Constants.FIELD_SIDE);
        g.setColor(Color.WHITE);

        for (Link i = snake.head; i != null; i = i.next) {
            int x = i.getX();
            int y = i.getY();
            g.fillRect(x * Constants.SNAKE_SIDE, y * Constants.SNAKE_SIDE, Constants.SNAKE_SIDE, Constants.SNAKE_SIDE);
        }
        int x = snake.mouse.x;
        int y = snake.mouse.y;
        g.fillRect(x * Constants.SNAKE_SIDE, y * Constants.SNAKE_SIDE, Constants.SNAKE_SIDE, Constants.SNAKE_SIDE);
    }

}
