package com.andrew.JavaGame.gameState;

import com.andrew.JavaGame.audio.JukeBox;
import com.andrew.JavaGame.entity.things.player.PlayerSave;
import com.andrew.JavaGame.handlers.Keys;
import com.andrew.JavaGame.tileMap.Background;

import java.awt.*;

public class MenuState extends GameState {

    private Background bg;

    private int currentChoice = 0;
    private String[] options = {
            "Start",
            "Authors",
            "Quit"
    };

    private Color titleColor;
    private Font titleFont;

    private Font font;

    public MenuState(GameStateManager gsm) {
        super(gsm);

        try {

            // load background
            bg = new Background("/Backgrounds/menubg.gif", 1);
            bg.setVector(-0.9, 0);

            titleColor = new Color(128, 0, 0);
            titleFont = new Font(
                    "Times New Roman",
                    Font.BOLD,
                    28);

            font = new Font("Arial", Font.PLAIN, 12);

            // load sound
            JukeBox.load("/Music/menuMusicLowQuality.mp3.mp3", "menuoption");
            JukeBox.loop("menuoption", 600, JukeBox.getFrames("menuoption") - 2200);

            JukeBox.load("/SFX/menuChoiceSound.mp3.mp3", "menuChoiceSound");
            JukeBox.load("/SFX/punch2.mp3.mp3", "punch2");


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
        g.drawString("PokeRUN", 250, 60);

        // draw menu options
        g.setFont(font);
        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(Color.BLACK);
            } else {
                g.setColor(Color.RED);
            }
            if (i != 2) {
                g.drawString(options[i], 290 - i * 9, 200 + i * 15);
            } else {
                g.drawString(options[i], 290, 200 + i * 15);
            }
        }
    }

    private void select() {
        if (currentChoice == 0) {
            JukeBox.play("menuChoiceSound");
            PlayerSave.init();
            gsm.setState(GameStateManager.LEVEL1STATE);
        } else if (currentChoice == 1) {
            gsm.setState(GameStateManager.AUTHORS);
        } else if (currentChoice == 2) {
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