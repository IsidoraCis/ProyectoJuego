package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;



public class Nave4 extends EntidadMovible{
    private boolean destruida = false;
    private int vidas = 3;
    private Sprite spr;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    private boolean herido = false;
    private int tiempoHeridoMax=50;
    private int tiempoHerido;

    public Nave4(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
        super(x, y, 0, 0);
        sonidoHerido = soundChoque;
        this.soundBala = soundBala;
        this.txBala = txBala;
        spr = new Sprite(tx);
        spr.setPosition(x, y);
        //spr.setOriginCenter();
        spr.setBounds(x, y, 45, 45);

    }
    public void draw(SpriteBatch batch, PantallaJuego juego){
        float x =  spr.getX();
        float y =  spr.getY();
        float xVel = getXSpeed();
        float yVel = getYSpeed();
        if (!herido) {
            // que se mueva con teclado
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) xVel--;
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) xVel++;
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) yVel--;
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) yVel++;

	     /*   if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) spr.setRotation(++rotacion);
	        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) spr.setRotation(--rotacion);

	        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
	        	xVel -=Math.sin(Math.toRadians(rotacion));
	        	yVel +=Math.cos(Math.toRadians(rotacion));
	        	System.out.println(rotacion+" - "+Math.sin(Math.toRadians(rotacion))+" - "+Math.cos(Math.toRadians(rotacion))) ;
	        }
	        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
	        	xVel +=Math.sin(Math.toRadians(rotacion));
	        	yVel -=Math.cos(Math.toRadians(rotacion));

	        }*/

            // que se mantenga dentro de los bordes de la ventana
            if (x+xVel < 0 || x+xVel+spr.getWidth() > Gdx.graphics.getWidth())
                xVel*=-1;
            if (y+yVel < 0 || y+yVel+spr.getHeight() > Gdx.graphics.getHeight())
                yVel*=-1;

            spr.setPosition(x+xVel, y+yVel);
            setYSpeed(yVel);
            setXSpeed(xVel);

            spr.draw(batch);
        } else {
            spr.setX(spr.getX()+MathUtils.random(-2,2));
            spr.draw(batch);
            spr.setX(x);
            tiempoHerido--;
            if (tiempoHerido<=0) herido = false;
        }
        // disparo
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Bullet  bala = new Bullet(spr.getX()+spr.getWidth()/2-5,spr.getY()+ spr.getHeight()-5,0,3,txBala);
            juego.agregarBala(bala);
            soundBala.play();
        }

    }

    public boolean checkCollision(Ball2 b) {
        float xVel = getXSpeed();
        float yVel = getYSpeed();
        if(!herido && b.getArea().overlaps(spr.getBoundingRectangle())){
            // rebote
            if (xVel ==0) xVel += b.getXSpeed()/2;
            if (b.getXSpeed() ==0) b.setXSpeed(b.getXSpeed() + (int)xVel/2);
            xVel = - xVel;
            b.setXSpeed(-b.getXSpeed());

            if (yVel ==0) yVel += b.getYSpeed()/2;
            if (b.getYSpeed() ==0) b.setYSpeed(b.getYSpeed() + (int)yVel/2);
            yVel = - yVel;
            b.setYSpeed(- b.getYSpeed());
            // despegar sprites
      /*      int cont = 0;
            while (b.getArea().overlaps(spr.getBoundingRectangle()) && cont<xVel) {
               spr.setX(spr.getX()+Math.signum(xVel));
            }   */
            //actualizar vidas y herir
            vidas--;
            herido = true;
            tiempoHerido=tiempoHeridoMax;
            sonidoHerido.play();
            if (vidas<=0)
                destruida = true;
            return true;
        }
        return false;
    }

    @Override
    public void update() {
    }

    public boolean estaDestruido() {
        return !herido && destruida;
    }
    public boolean estaHerido() {
        return herido;
    }

    public int getVidas() {return vidas;}
    //public boolean isDestruida() {return destruida;}
    public void setVidas(int vidas2) {vidas = vidas2;}
}
