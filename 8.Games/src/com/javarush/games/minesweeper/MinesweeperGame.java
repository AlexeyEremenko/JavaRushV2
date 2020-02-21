package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperGame extends Game {
    private static final int SIDE = 9;
    private GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private int countMinesOnField;
    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\uD83D\uDEA9";
    private int countFlags;
    private boolean isGameStopped;
    private int countClosedTiles = SIDE * SIDE;
    private int score;

    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        isGameStopped = false;
        createGame();
    }

    private void createGame() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                boolean isMine = getRandomNumber(10) < 1;
                if (isMine) {
                    countMinesOnField++;
                }
                gameField[y][x] = new GameObject(x, y, isMine);
                setCellColor(x, y, Color.BLANCHEDALMOND);
                setCellValue(x, y, "");
            }
        }
        countMineNeighbors();
        countFlags = countMinesOnField;
    }

    private void countMineNeighbors() {
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                if (!gameField[j][i].isMine) {
                    List<GameObject> list = getNeighbors(gameField[j][i]);
                    int mineNumber = 0;
                    for (GameObject current :
                            list) {
                        if (current.isMine)
                            mineNumber++;
                    }
                    gameField[j][i].countMineNeighbors = mineNumber;
                }
            }
        }
    }

    private List<GameObject> getNeighbors(GameObject gameObject) {
        List<GameObject> result = new ArrayList<>();
        for (int y = gameObject.y - 1; y <= gameObject.y + 1; y++) {
            for (int x = gameObject.x - 1; x <= gameObject.x + 1; x++) {
                if (y < 0 || y >= SIDE) {
                    continue;
                }
                if (x < 0 || x >= SIDE) {
                    continue;
                }
                if (gameField[y][x] == gameObject) {
                    continue;
                }
                result.add(gameField[y][x]);
            }
        }
        return result;
    }

    private void openTile(int x, int y) {
        if (gameField[y][x].isOpen || gameField[y][x].isFlag || isGameStopped){
            return;
        }
        gameField[y][x].isOpen = true;
        countClosedTiles--;
        if (gameField[y][x].isMine) {
            setCellValueEx(x, y, Color.RED, MINE);
            gameOver();
        }
        else{
            int countMineNeighbors=gameField[y][x].countMineNeighbors;
            if (countMineNeighbors == 0){
                setCellValue(x, y, "");
                List<GameObject> neighbors = getNeighbors(gameField[y][x]);
                for (GameObject current :
                        neighbors) {
                    openTile(current.x, current.y);
                }
            }
            else {
                setCellNumber(x, y, countMineNeighbors);
            }
            setCellColor(x, y, Color.ALICEBLUE);
            score += 5;
            setScore(score);
            if (countClosedTiles == countMinesOnField)
                win();
        }
    }

    private void markTile(int x, int y) {
        if (gameField[y][x].isOpen){
            return;
        }
        if (gameField[y][x].isFlag){
            gameField[y][x].isFlag = false;
            countFlags++;
            setCellValue(x, y, "");
            setCellColor(x, y, Color.BLANCHEDALMOND);
        }
        else {
            if (countFlags == 0){
                return;
            }
            gameField[y][x].isFlag = true;
            countFlags--;
            setCellValue(x, y, FLAG);
            setCellColor(x, y, Color.AZURE);
        }
    }

    private void gameOver(){
        isGameStopped = true;
        showMessageDialog(Color.CHARTREUSE,"Игра окончена, лузер!", Color.BLACK, 20);
    }

    private void win(){
        isGameStopped = true;
        showMessageDialog(Color.CHARTREUSE,"Игра окончена, виннер!", Color.BLACK, 20);
    }

    private void restart(){
        isGameStopped = false;
        countClosedTiles = SIDE * SIDE;
        countMinesOnField = 0;
        score = 0;
        setScore(score);
        createGame();
    }

    @Override
    public void onMouseLeftClick(int x, int y) {
        if (isGameStopped){
            restart();
        }
        else {
            openTile(x, y);
        }
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        markTile(x, y);
    }
}