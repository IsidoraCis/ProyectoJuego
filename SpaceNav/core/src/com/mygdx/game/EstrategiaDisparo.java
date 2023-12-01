package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface EstrategiaDisparo {
    void disparar(PantallaJuego juego, SpriteBatch batch, float posX, float posY, float naveWidth, float naveHeight, Texture txBala, Sound soundBala);
}