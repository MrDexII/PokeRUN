package GameState;

import Audio.JukeBox;

import Main.GamePanel;

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	
	private PauseState pauseState;
	private boolean paused;
	
	public static final int NUMGAMESTATES = 16;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	public static final int AUTHORS = 2;
	public static final int GAMEOVER = 3;
	public static final int LEVEL2STATE = 4;

	
	public GameStateManager() {
		
		JukeBox.init();
		
		gameStates = new GameState[NUMGAMESTATES];
		
		pauseState = new PauseState(this);
		paused = false;
		
		currentState = MENUSTATE;
		loadState(currentState);
	}
	private void loadState(int state) {
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		else if(state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
		else if(state == AUTHORS)
			gameStates[state] = new Authors(this);
		else if(state == GAMEOVER)
			gameStates[state] = new GameOver(this);
		else if(state == LEVEL2STATE)
			gameStates[state] = new Level2State(this);
	}
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}
	public void setPaused(boolean b) { paused = b; }
	
	public void update() {
		if(paused) {
			pauseState.update();
			return;
		}
		if(gameStates[currentState] != null) gameStates[currentState].update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		if(paused) {
			pauseState.draw(g);
			return;
		}
			if(gameStates[currentState] != null) gameStates[currentState].draw(g);
			else {
				g.setColor(java.awt.Color.BLACK);
				g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
			}
		}
	}