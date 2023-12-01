package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Ball2 extends EntidadMovible implements Dibujable {
    private Sprite spr;

    public Ball2(float x, float y, int size, float xSpeed, float ySpeed, Texture tx) {
        super(x, y, xSpeed, ySpeed);
        spr = new Sprite(tx);

        //validar que borde de esfera no quede fuera
        if (x-size < 0) this.x = x+size;
        if (x+size > Gdx.graphics.getWidth())this.x = x-size;

        //validar que borde de esfera no quede fuera
        if (y-size < 0) this.y = y+size;
        if (y+size > Gdx.graphics.getHeight())this.y = y-size;

        spr.setPosition(x, y);
    }
    
    public void update() {
        x += getXSpeed();
        y += getYSpeed();

        if (x+getXSpeed() < 0 || x+getXSpeed()+spr.getWidth() > Gdx.graphics.getWidth())
            setXSpeed(getXSpeed() * -1);
        if (y+getYSpeed() < 0 || y+getYSpeed()+spr.getHeight() > Gdx.graphics.getHeight())
            setYSpeed(getYSpeed() * -1);
        spr.setPosition(x, y);
    }

    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }
    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }

    public void checkCollision(Ball2 b2) {
        if(spr.getBoundingRectangle().overlaps(b2.spr.getBoundingRectangle())){
            // rebote
            if (getXSpeed() ==0) setXSpeed(getXSpeed() + b2.getXSpeed()/2);
            if (b2.getXSpeed() ==0) b2.setXSpeed(b2.getXSpeed() + getXSpeed()/2);
            setXSpeed(- getXSpeed());
            b2.setXSpeed(-b2.getXSpeed());

            if (getYSpeed() ==0) setYSpeed(getYSpeed() + b2.getYSpeed()/2);
            if (b2.getYSpeed() ==0) b2.setYSpeed(b2.getYSpeed() + getYSpeed()/2);
            setYSpeed(- getYSpeed());
            b2.setYSpeed(- b2.getYSpeed());
        }
    }

}
