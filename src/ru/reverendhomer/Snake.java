package ru.reverendhomer;

import java.awt.Toolkit;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Snake {

    Type field[][] = new Type[Constants.SIZE_FIELD][Constants.SIZE_FIELD];
    Link head, tail;
    Link mouse, freeCells[];
    Random random = new Random();
    int length = 2;
    private static boolean go;

    public static boolean gameIsOvered() {
        return go;
    }

    public Snake() {
        go = false;
        for (int i = 0; i < Constants.SIZE_FIELD; i++) {
            for (int j = 0; j < Constants.SIZE_FIELD; j++) {
                field[i][j] = Type.EMPTY;
            }
        }
        tail = new Link((Constants.SIZE_FIELD / 2) - 1, (Constants.SIZE_FIELD / 2), null, null, Direction.RIGHT);
        head = new Link((Constants.SIZE_FIELD / 2), (Constants.SIZE_FIELD / 2), tail, null, Direction.RIGHT);
        tail.prev = head;
        createMouse();
    }

    public int getScore() {
        return length - 2;
    }

    class Link {

        int x, y;
        Link next, prev;
        Type type;
        Direction direction;

        // Конструктор для элемента змеи 
        public Link(int x, int y, Link next, Link prev, Direction direction) {
            this.x = x;
            this.y = y;
            this.next = next;
            this.prev = prev;
            this.direction = direction;
            field[x][y] = Type.SNAKE;
        }

        public Link(int x, int y, Type type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public Link[] getFreeCells() {
        int l = (Constants.SIZE_FIELD * Constants.SIZE_FIELD) - length;
        freeCells = new Link[l];
        int i = 0;
        for (int x = 0; x < Constants.SIZE_FIELD; x++) {
            for (int y = 0; y < Constants.SIZE_FIELD; y++) {
                if (field[x][y] == Type.EMPTY) {
                    freeCells[i++] = new Link(x, y, Type.EMPTY);
                }
            }
        }
        return freeCells;
    }

    public void createMouse() {
        Link[] fc = getFreeCells();
        int index = random.nextInt(fc.length);
        mouse = new Link(freeCells[index].x, freeCells[index].y, Type.MOUSE);
        field[mouse.x][mouse.y] = Type.MOUSE;
    }

    public Link mouse() {
        return mouse;
    }

    public void addHead(int x, int y) {
        Link tmp = new Link(x, y, head, null, head.direction);
        head = tmp;
        head.next.prev = head;
    }

    public void gameOver() {
        go = true;
        SwingUtilities.invokeLater(() -> {
            JFrame goFrame = new JFrame("Игра окончена");
            JLabel message = new JLabel("К сожалению, Вы проиграли. Вы набрали " + getScore() + " очков.");
            goFrame.add(message);
            goFrame.setSize(400, 50);
            goFrame.setResizable(false);
            goFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("res/snake.gif"));
            goFrame.setLocationRelativeTo(null);
            goFrame.setVisible(true);
        });
    }

    public void moveDown() {
        head.direction = Direction.DOWN;
        if (head.y >= Constants.SIZE_FIELD - 1) {
            gameOver();
            return;
        }
        switch (field[head.x][head.y + 1]) {
            case EMPTY:
                tail = tail.prev;
                field[tail.next.x][tail.next.y] = Type.EMPTY;
                tail.next = null;
                addHead(head.x, head.y + 1);
                break;

            case MOUSE:
                length++;
                addHead(mouse.x, mouse.y);
                createMouse();
                break;

            default:
                gameOver();
                break;
        }
    }

    public void moveUp() {
        head.direction = Direction.UP;
        if (head.y - 1 < 0) {
            gameOver();
            return;
        }
        switch (field[head.x][head.y - 1]) {
            case EMPTY:
                tail = tail.prev;
                field[tail.next.x][tail.next.y] = Type.EMPTY;
                tail.next = null;
                addHead(head.x, head.y - 1);
                break;

            case MOUSE:
                length++;
                addHead(mouse.x, mouse.y);
                createMouse();
                break;

            default:
                gameOver();
                break;
        }
    }

    public void moveLeft() {
        head.direction = Direction.LEFT;
        if (head.x - 1 < 0) {
            gameOver();
            return;
        }
        switch (field[head.x - 1][head.y]) {
            case EMPTY:
                tail = tail.prev;
                field[tail.next.x][tail.next.y] = Type.EMPTY;
                tail.next = null;
                addHead(head.x - 1, head.y);
                break;

            case MOUSE:
                length++;
                addHead(mouse.x, mouse.y);
                createMouse();
                break;

            default:
                gameOver();
                break;
        }
    }

    public void moveRight() {
        head.direction = Direction.RIGHT;
        if (head.x >= (Constants.SIZE_FIELD - 1)) {
            gameOver();
            return;
        }
        switch (field[head.x + 1][head.y]) {

            case EMPTY:
                tail = tail.prev;
                field[tail.next.x][tail.next.y] = Type.EMPTY;
                tail.next = null;
                addHead(head.x + 1, head.y);
                break;

            case MOUSE:
                length++;
                addHead(mouse.x, mouse.y);
                createMouse();
                break;

            default:
                gameOver();
                break;
        }
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP:
                moveUp();
                break;

            case DOWN:
                moveDown();
                break;

            case LEFT:
                moveLeft();
                break;

            case RIGHT:
                moveRight();
                break;

            default:
                break;
        }
    }
}
