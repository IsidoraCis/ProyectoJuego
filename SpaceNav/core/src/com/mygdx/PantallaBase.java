package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class PantallaBase implements Screen{
    protected SpaceNavigation game;
    protected OrthographicCamera camera;
    protected SpriteBatch batch;
    protected BitmapFont font;

    public PantallaBase(SpaceNavigation game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 800);
        batch = new SpriteBatch();  // Inicializamos el objeto SpriteBatch
        font = new BitmapFont();
        initialize();
    }

    // Métodos template
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();  // Comenzamos el lote al principio del renderizado
        draw();
        batch.end();    // Terminamos el lote al final del renderizado

        update(delta);
    }

    protected abstract void initialize();
    protected abstract void draw();
    protected abstract void update(float delta);

    @Override
    public void show() {}
    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {}
}