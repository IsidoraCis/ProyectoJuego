package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DisparoSimple implements EstrategiaDisparo{
    @Override
    public void disparar(PantallaJuego juego, SpriteBatch batch, float posX, float posY, float naveWidth, float naveHeight, Texture txBala, Sound soundBala) {
        float balaX = posX + naveWidth / 2 - 5;
        float balaY = posY + naveHeight - 5;
        Bullet bala = new Bullet(balaX, balaY, 0, 3, txBala);
        juego.agregarBala(bala);
        soundBala.play();
    }
}
