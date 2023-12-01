package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.ConfiguracionJuego;
import com.badlogic.gdx.graphics.Color;

public class PantallaConfiguraciones extends PantallaBase{

    private Rectangle botonVolver; // Botón para volver al menú principal
    private Rectangle botonAumentarVolumen;
    private Rectangle botonDisminuirVolumen;

    public PantallaConfiguraciones(SpaceNavigation game) {
        super(game);

        botonVolver = new Rectangle(500, 100, 100, 60); // Ejemplo de posición y tamaño
        botonAumentarVolumen = new Rectangle(525, 300, 100, 60);
        botonDisminuirVolumen = new Rectangle(625, 300, 100, 60);
    }

    @Override
    protected void initialize() {
        // Puedes realizar inicializaciones específicas aquí si es necesario.
    }

    @Override
    protected void draw() {
        game.getBatch().begin();
        game.getFont().setColor(Color.WHITE);
        game.getFont().draw(game.getBatch(), "Configuración de Volumen", 140, 400);
        game.getFont().draw(game.getBatch(), "Volumen: " + ConfiguracionJuego.getInstance().getVolumen(), 500, 350);
        game.getFont().draw(game.getBatch(), "+", botonAumentarVolumen.x, botonAumentarVolumen.y);
        game.getFont().draw(game.getBatch(), "-", botonDisminuirVolumen.x, botonDisminuirVolumen.y);
        game.getFont().draw(game.getBatch(), "Volver", botonVolver.x, botonVolver.y);
        game.getBatch().end();
    }

    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        game.getFont().draw(game.getBatch(), "Configuración de Volumen", 140, 400);
        game.getFont().draw(game.getBatch(), "Volumen: " + ConfiguracionJuego.getInstance().getVolumen(), 500, 350);
        game.getFont().draw(game.getBatch(), "+", botonAumentarVolumen.x, botonAumentarVolumen.y);
        game.getFont().draw(game.getBatch(), "-", botonDisminuirVolumen.x, botonDisminuirVolumen.y);
        game.getFont().draw(game.getBatch(), "Volver", botonVolver.x, botonVolver.y);
        game.getBatch().end();

        // Gestionar entradas para volver al menú principal
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (botonAumentarVolumen.contains(touchPos.x, touchPos.y)) {
                int volumenActual = ConfiguracionJuego.getInstance().getVolumen();
                ConfiguracionJuego.getInstance().setVolumen(Math.min(volumenActual + 10, 100));
            } else if (botonDisminuirVolumen.contains(touchPos.x, touchPos.y)) {
                int volumenActual = ConfiguracionJuego.getInstance().getVolumen();
                ConfiguracionJuego.getInstance().setVolumen(Math.max(volumenActual - 10, 0));
            } else if (botonVolver.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new PantallaMenu(game));
                //dispose();
            }
        }
    }
    @Override
    protected void update(float delta) {
        if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            Screen ss = new PantallaJuego(game, 1, 3, 0, 1, 1, 10);
            ss.resize(1200, 800);
            game.setScreen(ss);
            //dispose();
        }
    }


    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }
}
