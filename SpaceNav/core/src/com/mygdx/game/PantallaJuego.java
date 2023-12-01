package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;

public class PantallaJuego extends PantallaBase {
	private Nave4 nave;
	private ArrayList<Ball2> balls1 = new ArrayList<>();
	private ArrayList<Ball2> balls2 = new ArrayList<>();

	private ArrayList<Bullet> balas = new ArrayList<>();

	private ArrayList<PowerUp> powerUps = new ArrayList<>();

	private Sound explosionSound;
	private Music gameMusic;
	private boolean musicaOn = false;
	private int score;
	private int ronda;
	private int velXAsteroides;
	private int velYAsteroides;
	private int cantAsteroides;
	private Rectangle botonPausaArea;
	private ShapeRenderer shapeRenderer;
	private boolean juegoPausado;
	private static final int botonSize = 50;
	private static final int padding = 10;
	private static final float spaceBetweenBars = 15;
	private long explosionSoundId;

	public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score,
						 int velXAsteroides, int velYAsteroides, int cantAsteroides) {
		super(game);
		this.ronda = ronda;
		this.score = score;
		this.velXAsteroides = velXAsteroides;
		this.velYAsteroides = velYAsteroides;
		this.cantAsteroides = cantAsteroides;
		juegoPausado = false;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 640);

		// Inicializar assets; música de fondo y efectos de sonido
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
		explosionSoundId = explosionSound.play(1.0f);
		float volumenConfigurado = ConfiguracionJuego.getInstance().getVolumen() / 100f;
		explosionSound.setVolume(explosionSoundId, volumenConfigurado);
		if (!musicaOn) {
			gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav"));
			gameMusic.setLooping(true);
			musicaOn = true;
		}
		gameMusic.setVolume(volumenConfigurado);

		// Cargar imagen de la nave, 64x64
		nave = new Nave4.Builder()
				.setPosicion(Gdx.graphics.getWidth() / 2 - 50, 30)
				.setTexturaNave(new Texture(Gdx.files.internal("MainShip3.png")))
				.setSonidoHerido(Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")))
				.setTexturaBala(new Texture(Gdx.files.internal("Rocket2.png")))
				.setSoundBala(Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"))) // Corregido aquí
				.build();

		// Crear asteroides
		Random r = new Random();
		for (int i = 0; i < cantAsteroides; i++) {
			Ball2 bb = new Ball2(r.nextInt((int) Gdx.graphics.getWidth()),
					50 + r.nextInt((int) Gdx.graphics.getHeight() - 50),
					20 + r.nextInt(10), velXAsteroides + r.nextInt(4), velYAsteroides + r.nextInt(4),
					new Texture(Gdx.files.internal("aGreyMedium4.png")));
			balls1.add(bb);
			balls2.add(bb);
		}

		float pauseButtonX = 800 - botonSize - padding;
		float pauseButtonY = 640 - botonSize - padding;

		botonPausaArea = new Rectangle(
				pauseButtonX,
				pauseButtonY,
				botonSize * 2 + spaceBetweenBars,
				botonSize
		);
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	protected void initialize() {
		// Inicialización específica del juego (nave, asteroides, etc.)
	}
    
	public void dibujaEncabezado() {
		CharSequence str = "Vidas: " + nave.getVidas() + " Ronda: " + ronda;
		game.getFont().getData().setScale(2f);
		game.getFont().draw(batch, str, 10, 30);
		game.getFont().draw(batch, "Score:" + this.score, Gdx.graphics.getWidth() - 150, 30);
		game.getFont().draw(batch, "HighScore:" + game.getHighScore(), Gdx.graphics.getWidth() / 2 - 100, 30);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.WHITE); // Color del botón de pausa

		// Dibuja los dos rectángulos del botón de pausa
		float pauseButtonWidth = 10;
		float pauseButtonHeight = 40;
		shapeRenderer.rect(botonPausaArea.x, botonPausaArea.y, pauseButtonWidth, pauseButtonHeight);
		shapeRenderer.rect(botonPausaArea.x + pauseButtonWidth + spaceBetweenBars, botonPausaArea.y, pauseButtonWidth, pauseButtonHeight);
		shapeRenderer.end();

		batch.begin();

		if (!juegoPausado) {
			if (!nave.estaHerido()) {
				nave.draw(batch, this, delta);
				nave.update();
				// colisiones entre balas y asteroides y su destruccion
				for (int i = 0; i < balas.size(); i++) {
					Bullet b = balas.get(i);
					b.update();
					for (int j = 0; j < balls1.size(); j++) {
						if (b.checkCollision(balls1.get(j))) {

							float volumenConfigurado = ConfiguracionJuego.getInstance().getVolumen() / 100f;
							explosionSoundId = explosionSound.play(volumenConfigurado);
							balls1.remove(j);
							balls2.remove(j);
							j--;
							score += 10;
						}
					}

					//   b.draw(batch);
					if (b.isDestroyed()) {
						balas.remove(b);
						i--; //para no saltarse 1 tras eliminar del arraylist
					}
				}
				//actualizar movimiento de asteroides dentro del área
				for (Ball2 ball : balls1) {
					ball.update();
				}
				//colisiones entre asteroides y sus rebotes
				for (int i = 0; i < balls1.size(); i++) {
					Ball2 ball1 = balls1.get(i);
					for (int j = 0; j < balls2.size(); j++) {
						Ball2 ball2 = balls2.get(j);
						if (i < j) {
							ball1.checkCollision(ball2);

						}
					}
				}
			}

		}

		//dibujar balas
		for (Bullet b : balas) {
			b.draw(batch);
		}
		nave.draw(batch, this, delta);
		//dibujar asteroides y manejar colision con nave
		for (int i = 0; i < balls1.size(); i++) {
			Ball2 b = balls1.get(i);
			b.draw(batch);
			//perdió vida o game over
			if (nave.checkCollision(b)) {
				//asteroide se destruye con el choque
				balls1.remove(i);
				balls2.remove(i);
				i--;
			}
		}

		if (nave.estaDestruido()) {
			if (score > game.getHighScore())
				game.setHighScore(score);
			Screen ss = new PantallaGameOver(game);
			ss.resize(1200, 800);
			game.setScreen(ss);
			//dispose();
		}
		batch.end();

		batch.begin();
		dibujaEncabezado();
		batch.end();
		//nivel completado
		if (balls1.size() == 0) {
			Screen ss = new PantallaJuego(game, ronda + 1, nave.getVidas(), score,
					velXAsteroides + 3, velYAsteroides + 3, cantAsteroides + 10);
			ss.resize(1200, 800);
			game.setScreen(ss);
			//dispose();
		}

		if (!juegoPausado && Gdx.input.justTouched()) {
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos); // Convierte la posición del toque a coordenadas del mundo del juego
			if (botonPausaArea.contains(touchPos.x, touchPos.y)) {
				juegoPausado = true;
				game.setScreen(new PantallaPausa(game, this));
			}
		}

	}

	public void update(float delta) {
		// Implementación específica de cómo actualizar el juego
	}

    public boolean agregarBala(Bullet bb) {
    	return balas.add(bb);
    }

	public void reanudarJuego() {
		juegoPausado = false;
	}

	@Override
	public void pause() {
		if (gameMusic != null && gameMusic.isPlaying()) {
			gameMusic.pause();
		}
	}

	@Override
	public void resume() {
		if (gameMusic != null && !gameMusic.isPlaying()) {
			gameMusic.play();
		}

	}


	@Override
	public void show() {
		// TODO Auto-generated method stub
		float volumenConfigurado = ConfiguracionJuego.getInstance().getVolumen() / 100f;
		if (gameMusic != null && !gameMusic.isPlaying()) {
			gameMusic.play();
		}
	}

	@Override
	public void draw(){
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		this.explosionSound.dispose();
		this.gameMusic.dispose();
		shapeRenderer.dispose();
		if (gameMusic != null) {
			gameMusic.dispose();
		}
	}
   
}
