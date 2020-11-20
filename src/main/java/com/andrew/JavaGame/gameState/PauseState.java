package com.andrew.JavaGame.gameState;

import com.andrew.JavaGame.GamePanel;
import com.andrew.JavaGame.handlers.Keys;

import java.awt.*;

public class PauseState extends GameState {
    private Font font;

    public PauseState(GameStateManager gsm) {
        super(gsm);
        // fonts
        font = new Font("Century Gothic", Font.PLAIN, 14);
    }

    public void init() {
    }

    public void update() {
        handleInput();
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString("Game Paused", 265, 220);
    }

    public void handleInput() {
        if (Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(false);
        if (Keys.isPressed(Keys.BUTTON1)) {
            gsm.setPaused(false);
            gsm.setState(GameStateManager.MENUSTATE);
        }
    }
}
