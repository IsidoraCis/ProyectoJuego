package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Bullet extends EntidadMovible implements Dibujable{
	private boolean destroyed = false;
	private Sprite spr;

	public Bullet(float x, float y, int xSpeed, int ySpeed, Texture tx) {
		super(x, y, xSpeed, ySpeed);
		spr = new Sprite(tx);
		spr.setPosition(getX(), getY());
	}
	//Comprueba colisiones con el borde de la pantalla
	public void update() {
		spr.setPosition(spr.getX()+xSpeed, spr.getY()+ySpeed);
		if (spr.getX() < 0 || spr.getX()+spr.getWidth() > Gdx.graphics.getWidth()) {
			destroyed = true;
		}
		if (spr.getY() < 0 || spr.getY()+spr.getHeight() > Gdx.graphics.getHeight()) {
			destroyed = true;
		}

	}

	public void draw(SpriteBatch batch) {
		spr.draw(batch);
	}
	//Comprueba colision con las pelotas
	public boolean checkCollision(Ball2 b2) {
		if(spr.getBoundingRectangle().overlaps(b2.getArea())){
			// Se destruyen ambos
			this.destroyed = true;
			return true;

		}
		return false;
	}

	public boolean isDestroyed() {return destroyed;}

}
