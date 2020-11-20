package com.andrew.JavaGame.gameState;

import com.andrew.JavaGame.audio.JukeBox;
import com.andrew.JavaGame.handlers.Keys;
import com.andrew.JavaGame.tileMap.Background;

import java.awt.*;

public class GameOver extends GameState {

    private Background bg;

    private int currentChoice = 0;
    private String[] options = {
            "Back",
            "Quit"
    };

    private Color titleColor;
    private Font titleFont;

    private Font font;

    public GameOver(GameStateManager gsm) {
        super(gsm);
        try {

            bg = new Background("/Backgrounds/menubg.gif", 1);
            bg.setVector(-0.9, 0);

            titleColor = new Color(128, 0, 0);
            titleFont = new Font(
                    "Times New Roman",
                    Font.BOLD,
                    28);

            font = new Font("Arial", Font.PLAIN, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {
    }

    public void update() {
        bg.update();
        // check keys
        handleInput();
    }

    public void draw(Graphics2D g) {
        // draw bg
        bg.draw(g);

        // draw title
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("You Win", 250, 60);

        // draw menu options
        g.setFont(font);
        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(Color.BLACK);
            } else {
                g.setColor(Color.RED);
            }
            g.drawString(options[i], 290, 200 + i * 15);
        }
    }

    private void select() {
        if (currentChoice == 0) {
            JukeBox.play("menuselect");
            gsm.setState(GameStateManager.MENUSTATE);
        } else if (currentChoice == 1) {
            System.exit(0);
        }
    }

    public void handleInput() {
        if (Keys.isPressed(Keys.ENTER)) select();
        if (Keys.isPressed(Keys.UP)) {
            JukeBox.play("punch2", 0);
            if (currentChoice > 0) {
                currentChoice--;
            }
        }
        if (Keys.isPressed(Keys.DOWN)) {
            if (currentChoice < options.length - 1) {
                JukeBox.play("punch2", 0);
                currentChoice++;
            }
        }
    }
}