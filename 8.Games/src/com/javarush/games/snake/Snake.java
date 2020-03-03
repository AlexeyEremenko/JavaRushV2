package com.javarush.games.snake;


import java.util.ArrayList;
import java.util.List;
import com.javarush.engine.cell.*;

import static com.javarush.games.snake.SnakeGame.HEIGHT;
import static com.javarush.games.snake.SnakeGame.WIDTH;

public class Snake extends GameObject {

    private List<GameObject> snakeParts = new ArrayList<>();
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    private Direction direction = Direction.LEFT;

    public boolean isAlive = true;

    public Snake(int x, int y) {
        super(x, y);
        snakeParts.add(new GameObject(x, y));
        snakeParts.add(new GameObject(x + 1, y));
        snakeParts.add(new GameObject(x + 2, y));
    }

    public void setDirection(Direction direction) {
 /*       if ((direction == Direction.LEFT || direction == Direction.RIGHT) && (snakeParts.get(0).y == snakeParts.get(1).y )){
            return;
        }

        if ((direction == Direction.UP || direction == Direction.DOWN) && (snakeParts.get(0).x == snakeParts.get(1).x)){
            return;
        }

        this.direction = direction;*/
        switch (this.direction){
            case LEFT:
                if (direction == Direction.RIGHT || snakeParts.get(0).x == snakeParts.get(1).x)
                    break;
                this.direction = direction;
            case RIGHT:
                if (direction == Direction.LEFT || snakeParts.get(0).x == snakeParts.get(1).x)
                    break;
                this.direction = direction;
            case DOWN:
                if (direction == Direction.UP || snakeParts.get(0).y == snakeParts.get(1).y)
                    break;
                this.direction = direction;
            case UP:
                if (direction == Direction.DOWN || snakeParts.get(0).y == snakeParts.get(1).y)
                    break;
                this.direction = direction;
        }
    }

    public void draw(Game game) {
        Color color = isAlive ? Color.BLANCHEDALMOND : Color.RED;
        for (int i = 0; i < snakeParts.size(); i++) {
            if (i ==0){
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, HEAD_SIGN, color, 75);
            }
            else {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, color, 75);
            }
        }
    }

    public void move(Apple apple){
        GameObject newHead = createNewHead();
        if (newHead.x < 0 || newHead.x >= WIDTH || newHead.y < 0 || newHead.y >= HEIGHT) {
            isAlive = false;
        }
        else {
            if (checkCollision(newHead))
                isAlive = false;
            else {
                snakeParts.add(0, newHead);
                if (newHead.x == apple.x && newHead.y == apple.y)
                    apple.isAlive = false;
                else
                    removeTail();
            }
        }
    }

    public int getLength(){
        return snakeParts.size();
    }

    public GameObject createNewHead() {
        GameObject head = snakeParts.get(0);
        switch (direction){
            case UP:
                return new GameObject(head.x, head.y - 1);
            case DOWN:
                return new GameObject(head.x, head.y + 1);
            case LEFT:
                return new GameObject(head.x - 1, head.y);
            case RIGHT:
                return new GameObject(head.x + 1, head.y);
        }
        return head;
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }
    
    public boolean checkCollision(GameObject newObject){
        for (GameObject current :
                snakeParts) {
            if (current.x == newObject.x && current.y == newObject.y)
                return true;
        }
        return false;
    }
}
