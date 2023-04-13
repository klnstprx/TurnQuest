package com.gdx.turnquest;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.gdx.turnquest.MainMenuScreen.dm;

public class AbilitiesScreen implements Screen {
    final TurnQuest game;

    private final Texture backgroundTexture;

    OrthographicCamera camera;

    public static final int VIRTUAL_WIDTH = dm.width;

    public static final int VIRTUAL_HEIGHT = dm.height;

    public static Stage stage;

    public Skin skin;

    Viewport viewport;
    public AbilitiesScreen(final TurnQuest game) {
        this.game = game;
        backgroundTexture = new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);


        AssetDescriptor<Skin> skinAssetDescriptor = new AssetDescriptor<Skin>("pixthulhu/skin/pixthulhu-ui.json", Skin.class);
        game.manager.load(skinAssetDescriptor);
        game.manager.finishLoading();

        skin = game.manager.get(skinAssetDescriptor);

        TextButton bReturn = new TextButton("Return", skin);

        bReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(bReturn).center().padBottom(50f).row();

        table.padTop(100f); // add some padding at the top

        stage.addActor(table);

        viewport.apply();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        game.font.getData().setScale(4); //Changes font size.
        game.font.draw(game.batch, "Abilities", VIRTUAL_WIDTH*45/100, VIRTUAL_HEIGHT*85/100);
        game.batch.end();

        stage.act();
        stage.draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            toggleFullscreen();
        }
    }

    public static void toggleFullscreen(){
        if (Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setWindowedMode(VIRTUAL_WIDTH/2, VIRTUAL_HEIGHT/2);
            stage.getViewport().update(VIRTUAL_WIDTH/2, VIRTUAL_HEIGHT/2, true);
        } else {
            Gdx.graphics.setFullscreenMode(dm);
            stage.getViewport().update(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, true);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
    }
}