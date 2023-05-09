package com.gdx.turnquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.turnquest.TurnQuest;

import static com.gdx.turnquest.TurnQuest.*;
import static com.gdx.turnquest.TurnQuest.getVirtualHeight;

public class MapScreen extends BaseScreen {

    public MapScreen(final TurnQuest game) {
        super(game);

        game.setBackgroundTexture(new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png")));
        game.setStage(new Stage(getViewport()));


        TextButton bEnemy = new TextButton("Enemy", game.getSkin());
        TextButton bBoss = new TextButton("Boss", game.getSkin());
        TextButton bQuit = new TextButton("Quit", game.getSkin());

        final boolean[] boss = {false};

        bEnemy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new CombatScreen(game, boss[0]));
            }
        });

        bBoss.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boss[0] = true;
                game.setScreen(new CombatScreen(game, boss[0]));
            }
        });

        bQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(bEnemy).center().padBottom(50f).row();
        table.add(bBoss).center().padBottom(50f).row();
        table.add(bQuit).center().padBottom(50f);

        table.padTop(100f); // add some padding at the top

        game.getStage().addActor(table);

        getViewport().apply();
    }

    @Override
    protected void refreshScreen() {
        dispose();
        game.setScreen(new MainMenuScreen(game));
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.

        getCamera().update();
        game.getBatch().setProjectionMatrix(getCamera().combined);

        game.getBatch().begin();
        game.getBatch().draw(game.getBackgroundTexture(), 0, 0, getVirtualWidth(), getVirtualHeight());
        game.getFont().getData().setScale(4); //Changes font size.
        game.getBatch().end();

        game.getStage().act();
        game.getStage().draw();

        handleInput();
    }
}
