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
    private EstrategiaDisparo estrategiaDisparo;

    private static final float VELOCIDAD = 300;

    private Nave4(Builder builder) {
        super(builder.x, builder.y, 0, 0);
        this.vidas = builder.vidas;
        this.sonidoHerido = builder.sonidoHerido;
        this.soundBala = builder.soundBala;
        this.txBala = builder.texturaBala;
        this.spr = new Sprite(builder.texturaNave);
        this.spr.setPosition(builder.x, builder.y);
        this.spr.setBounds(builder.x, builder.y, 45, 45);
        this.estrategiaDisparo = new DisparoSimple(); // Asumiendo que DisparoSimple es una clase válida
    }

    public static class Builder {
        private int x, y;
        private Texture texturaNave;
        private Sound sonidoHerido;
        private Sound soundBala; // Única declaración de soundBala
        private Texture texturaBala;
        private int vidas = 3; // Valor predeterminado

        public Builder setPosicion(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder setTexturaNave(Texture textura) {
            this.texturaNave = textura;
            return this;
        }

        public Builder setSonidoHerido(Sound sonido) {
            this.sonidoHerido = sonido;
            return this;
        }

        public Builder setSoundBala(Sound sonido) {
            this.soundBala = sonido;
            return this;
        }

        public Builder setTexturaBala(Texture textura) {
            this.texturaBala = textura;
            return this;
        }

        public Builder setVidas(int vidas) {
            this.vidas = vidas;
            return this;
        }

        public Nave4 build() {
            return new Nave4(this);
        }
    }

    public void draw(SpriteBatch batch, PantallaJuego juego, float deltaTime) {
        float x = spr.getX();
        float y = spr.getY();
        float xVel = getXSpeed();
        float yVel = getYSpeed();

        if (!herido) {
            // Ajusta la velocidad basada en la entrada del teclado
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                xVel = -VELOCIDAD;
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                xVel = VELOCIDAD;
            } else {
                xVel = 0; // Detiene la nave si no se presionan las teclas izquierda o derecha
            }

            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                yVel = VELOCIDAD;
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                yVel = -VELOCIDAD;
            } else {
                yVel = 0; // Detiene la nave si no se presionan las teclas arriba o abajo
            }

            // Actualiza la posición de la nave
            x += xVel * deltaTime;
            y += yVel * deltaTime;

            // Restringe la nave a los límites de la pantalla
            x = Math.max(0, Math.min(x, Gdx.graphics.getWidth() - spr.getWidth()));
            y = Math.max(0, Math.min(y, Gdx.graphics.getHeight() - spr.getHeight()));

            spr.setPosition(x, y);
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
            disparar(juego, batch);
        }
    }

    public void disparar(PantallaJuego juego, SpriteBatch batch){
        estrategiaDisparo.disparar(juego, batch, spr.getX(), spr.getY(), spr.getWidth(), spr.getHeight(),txBala, soundBala);
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
        // Si la nave está herida, se reduce el tiempo de herido
        if (herido) {
            tiempoHerido--;
            if (tiempoHerido <= 0) {
                herido = false;
            }
        } else {
            // Movimiento básico de la nave
            float x = spr.getX();
            float y = spr.getY();
            float xVel = getXSpeed();
            float yVel = getYSpeed();

            // Control de la nave con el teclado
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) xVel--;
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) xVel++;
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) yVel--;
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) yVel++;

            // Mantener la nave dentro de los bordes de la pantalla
            if (x + xVel < 0 || x + xVel + spr.getWidth() > Gdx.graphics.getWidth())
                xVel *= -1;
            if (y + yVel < 0 || y + yVel + spr.getHeight() > Gdx.graphics.getHeight())
                yVel *= -1;

            // Actualizar la posición y velocidad de la nave
            spr.setPosition(x + xVel, y + yVel);
            setXSpeed(xVel);
            setYSpeed(yVel);
        }

        // Comprobar si la nave está destruida
        if (vidas <= 0) {
            destruida = true;
        }
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
