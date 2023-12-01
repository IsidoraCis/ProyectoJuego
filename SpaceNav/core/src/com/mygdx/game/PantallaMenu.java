package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;

public class PantallaMenu extends PantallaBase {
	private Rectangle botonJugar;
	private Rectangle botonConfiguraciones;
	private Rectangle botonSalir;

	public PantallaMenu(SpaceNavigation game) {
		super(game);
		botonJugar = new Rectangle(500, 350, 200, 60); // Ejemplo de coordenadas y tamaño
		botonConfiguraciones = new Rectangle(500, 250, 200, 60);
		botonSalir = new Rectangle(500, 150, 200, 60);
	}

	@Override
	protected void initialize() {
		// Puedes realizar inicializaciones específicas aquí si es necesario.
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		camera.update();
		game.getBatch().setProjectionMatrix(camera.combined);

		game.getBatch().begin();
		game.getFont().setColor(Color.WHITE);
		game.getFont().draw(game.getBatch(), "Bienvenido a Space Navigation !", 140, 400);

		// Dibujar botones
		game.getFont().draw(game.getBatch(), "Jugar", botonJugar.x, botonJugar.y);
		game.getFont().draw(game.getBatch(), "Configuraciones", botonConfiguraciones.x, botonConfiguraciones.y);
		game.getFont().draw(game.getBatch(), "Salir", botonSalir.x, botonSalir.y);

		game.getBatch().end();

		// Gestionar entradas del usuario
		if (Gdx.input.justTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);

			// Comprobar si el usuario ha tocado alguno de los botones
			if (botonJugar.contains(touchPos.x, touchPos.y)) {
				// Iniciar juego
				Screen ss = new PantallaJuego(game,1,3,0,1,1,10);
				ss.resize(1200, 800);
				game.setScreen(ss);

			} else if (botonConfiguraciones.contains(touchPos.x, touchPos.y)) {
				game.setScreen(new PantallaConfiguraciones(game));
				//dispose();
			} else if (botonSalir.contains(touchPos.x, touchPos.y)) {
				Gdx.app.exit();
			}
		}
	}

	@Override
	protected void draw() {
		game.getBatch().begin();
		game.getFont().draw(game.getBatch(), "Bienvenido a Space Navigation!", 140, 400);
		game.getFont().draw(game.getBatch(), "Jugar", botonJugar.x, botonJugar.y);
		game.getFont().draw(game.getBatch(), "Configuraciones", botonConfiguraciones.x, botonConfiguraciones.y);
		game.getFont().draw(game.getBatch(), "Salir", botonSalir.x, botonSalir.y);
		game.getBatch().end();
	}

	@Override
	protected void update(float delta) {
		// Gestionar entradas del usuario
		if (Gdx.input.justTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);

			// Comprobar si el usuario ha tocado alguno de los botones
			if (botonJugar.contains(touchPos.x, touchPos.y)) {
				iniciarJuego();
			} else if (botonConfiguraciones.contains(touchPos.x, touchPos.y)) {
				abrirConfiguraciones();
			} else if (botonSalir.contains(touchPos.x, touchPos.y)) {
				salirDelJuego();
			}
		}
	}

	protected void iniciarJuego() {
		// Lógica para iniciar el juego
		Screen ss = new PantallaJuego(game, 1, 3, 0, 1, 1, 10);
		ss.resize(1200, 800);
		game.setScreen(ss);
	}

	protected void abrirConfiguraciones() {
		// Lógica para abrir configuraciones
		game.setScreen(new PantallaConfiguraciones(game));
		//dispose();
	}

	protected void salirDelJuego() {
		// Lógica para salir del juego
		Gdx.app.exit();
	}

	@Override
	public void dispose() {
		game.getFont().dispose();
	}
   
}