package com.gdx.turnquest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.turnquest.dialogs.PreferencesDialog;
import com.gdx.turnquest.entities.Player;
import com.gdx.turnquest.screens.MainMenuScreen;
import com.gdx.turnquest.assets.Assets;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class TurnQuest extends Game {

	private SpriteBatch batch;

	private static int generalVolume = 50;

	private static Graphics.DisplayMode dm;

	private static OrthographicCamera camera;

	private Stage stage;

	private Skin skin;

	private static Viewport viewport;
	private Player player;

	public void render() {
		super.render(); // important!
	}

	public void create() {
		Assets.load();
		Assets.ASSET_MANAGER.finishLoading();
		Assets.adjustTitleFont(25);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		setDisplayMode(Gdx.graphics.getDisplayMode());
		getCamera().setToOrtho(false, getVirtualWidth(), getVirtualHeight());
		setViewport(new FitViewport(getVirtualWidth(), getVirtualHeight(), getCamera()));

		this.setScreen(new MainMenuScreen(this));
	}

	public void dispose() {
		super.dispose();
		Assets.dispose();
	}

	public static int getGeneralVolume(){
		return generalVolume;
	}

	public static void setGeneralVolume(int vol){
		generalVolume=vol;
	}

	public static Graphics.DisplayMode getDisplayMode(){
		return dm;
	}

	public static void setDisplayMode(Graphics.DisplayMode displayMode){
		dm = displayMode;
	}

	public static int getVirtualWidth(){
		return dm.width;
	}

	public static int getVirtualHeight(){
		return dm.height;
	}

	public SpriteBatch getBatch(){
		return batch;
	}


	public static OrthographicCamera getCamera() {
		return camera;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}


	public static Viewport getViewport() {
		return viewport;
	}

	public static void setViewport(Viewport viewport) {
		TurnQuest.viewport = viewport;
	}

	public Player getCurrentPlayer() { return player; }

	public void setCurrentPlayer(Player player) { this.player = player; }

	public static void toggleFullscreen(){
		if (Gdx.graphics.isFullscreen()) {
			Gdx.graphics.setWindowedMode(getVirtualWidth()/2, getVirtualHeight()/2);
			getViewport().update(getVirtualWidth()/2, getVirtualHeight()/2, true);
		} else {
			Gdx.graphics.setFullscreenMode(getDisplayMode());
			getViewport().update(getVirtualWidth(), getVirtualHeight(), true);
		}
	}
	public static boolean hasInternetConnection() {
		try {
			URL url = new URL("https://www.google.com");
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.connect();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public void showPreferencesDialog() {
		PreferencesDialog dialog = new PreferencesDialog("Options", "", () -> {

		}, Assets.getSkin());
		dialog.setColor(Color.LIGHT_GRAY);
		dialog.show(getStage());
	}
}
