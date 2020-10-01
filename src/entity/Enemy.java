package entity;


import audio.JukeBox;
import tileMap.TileMap;

public class Enemy extends MapObject {

    protected int health;
    protected int maxHealth;
    protected boolean dead;
    protected int damage;
    protected boolean remove;

    protected boolean flinching;
    protected long flinchCount;

    public Enemy(TileMap tm) {
        super(tm);
        //	remove = false;
    }

    public boolean isDead() {
        return dead;
    }
    //public boolean shouldRemove() { return remove; }

    public int getDamage() {
        return damage;
    }

    public void hit(int damage) {
        if (dead || flinching) return;
        JukeBox.play("kniveHits");
        ;
        health -= damage;
        if (health < 0) health = 0;
        if (health == 0) dead = true;
        //	if(dead) remove = true;
        flinching = true;
        flinchCount = 10;
    }

    public void update() {
    }

}