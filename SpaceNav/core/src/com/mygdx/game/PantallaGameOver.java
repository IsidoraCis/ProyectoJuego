package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;


public class PantallaGameOver extends PantallaBase {

	public PantallaGameOver(SpaceNavigation game) {
		super(game);
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
		game.getFont().draw(game.getBatch(), "Game Over !!! ", 120, 400,400,1,true);
		game.getFont().draw(game.getBatch(), "Pincha en cualquier lado para reiniciar ...", 100, 300);
	
		game.getBatch().end();

		if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			Screen ss = new PantallaJuego(game,1,3,0,1,1,10);
			ss.resize(1200, 800);
			game.setScreen(ss);
			//dispose();
		}
	}

	@Override
	protected void draw() {
		game.getBatch().begin();
		game.getFont().draw(game.getBatch(), "Game Over!!!", 120, 400, 400, 1, true);
		game.getFont().draw(game.getBatch(), "Pincha en cualquier lado para reiniciar...", 100, 300);
		game.getBatch().end();
	}

	@Override
	protected void update(float delta) {
		if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			iniciarJuego();
		}
	}

	private void iniciarJuego() {
		Screen ss = new PantallaJuego(game, 1, 3, 0, 1, 1, 10);
		ss.resize(1200, 800);
		game.setScreen(ss);
		//dispose();
	}

	@Override
	public void dispose() {
		game.getFont().dispose(); // Libera la fuente utilizada
	}
   
}