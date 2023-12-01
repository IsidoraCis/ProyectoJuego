package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.ConfiguracionJuego;
import com.badlogic.gdx.graphics.Color;

public class PantallaPausa extends PantallaBase{

        private SpaceNavigation game;
        private PantallaJuego pantallaJuegoActual;
        private Rectangle volverJuego;
        private Rectangle botonReiniciar;
        private Rectangle botonConfiguraciones;
        private Rectangle volverMenu;

        public PantallaPausa(SpaceNavigation game, PantallaJuego pantallaJuegoActual) {
            super(game);
            this.game = game; // Asegúrate de que esta línea esté presente
            this.pantallaJuegoActual = pantallaJuegoActual;

            volverJuego = new Rectangle(500, 400, 200, 60);
            botonReiniciar = new Rectangle(500, 300, 200, 60);
            botonConfiguraciones = new Rectangle(500, 200, 200, 60);
            volverMenu = new Rectangle(500, 100, 200, 60);
        }

    @Override
    protected void initialize() {
        // Inicialización específica de la pantalla de pausa
    }

    public void render(float delta) {
        super.render(delta); // Llamada al método render de la clase base

        ScreenUtils.clear(0, 0, 0.2f, 1);

        batch.begin();
        game.getFont().setColor(Color.WHITE);
        font.draw(batch, "Volver al juego", volverJuego.x, volverJuego.y);
        font.draw(batch, "Reiniciar juego", botonReiniciar.x, botonReiniciar.y);
        font.draw(batch, "Configuraciones", botonConfiguraciones.x, botonConfiguraciones.y);
        font.draw(batch, "Volver al Menú Principal", volverMenu.x, volverMenu.y);
        batch.end();

        manejarInput(); // Manejo de entrada específico de la pantalla de pausa
    }

    private void manejarInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (volverJuego.contains(touchPos.x, touchPos.y)) {
                pantallaJuegoActual.reanudarJuego();
                game.setScreen(pantallaJuegoActual);
                //dispose();
            } else if (botonConfiguraciones.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new PantallaConfiguraciones(game));
                //dispose();
            } else if (botonReiniciar.contains(touchPos.x, touchPos.y)) {
                Screen ss = new PantallaJuego(game, 1, 3, 0, 1, 1, 10);
                ss.resize(1200, 800);
                game.setScreen(ss);
                //dispose();
            } else if (volverMenu.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new PantallaMenu(game));
                //dispose();
            }
        }
    }

    @Override
    public void draw() {
        // Implementación específica de cómo dibujar en la pantalla de pausa
    }
    @Override
    public void show() {
        // TODO Auto-generated method stub
    }
    public void update(float delta) {
        // Implementación específica de cómo actualizar la pantalla de pausa
    }

    @Override
    public void dispose() {
        game.getFont().dispose();
    }
}
