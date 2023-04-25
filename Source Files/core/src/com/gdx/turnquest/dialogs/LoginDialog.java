package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.entities.Player;
import com.gdx.turnquest.screens.GameScreen;
import com.gdx.turnquest.utils.PlayerManager;
import com.gdx.turnquest.utils.UserManager;


import static com.gdx.turnquest.TurnQuest.hasInternetConnection;
import static com.gdx.turnquest.TurnQuest.setCurrentPlayer;

public class LoginDialog extends Dialog {
    private final TextField usernameField;
    private final TextField passwordField;
    private final Label errorLabel;
    private final TurnQuest game;
    private UserManager userManager = new UserManager();
    private String username;
    private String password;

    public LoginDialog(String title, Runnable runnable, Skin skin, TurnQuest game) {
        super(title, skin);
        this.game = game;

        getContentTable().defaults().expand().pad(10);
        getContentTable().add("Username:");
        usernameField = new TextField("", skin);
        getContentTable().add(usernameField).width(400);
        getContentTable().row();
        getContentTable().add("Password:");
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        getContentTable().add(passwordField).width(400);
        getContentTable().row();
        errorLabel = new Label("", skin);
        errorLabel.setColor(1, 0, 0, 1); // set the color to red
        getContentTable().add(errorLabel).colspan(2);
        button("Login", true);
        button("Cancel", false);
    }

    @Override
    protected void result(Object object) {
        // Handle the result of the dialog
        boolean result = (boolean) object;
        if (result) {
            // Check credentials
            username = usernameField.getText();
            password = passwordField.getText();
            // If correct, change screen
            // Check if the credentials are valid
            if (!hasInternetConnection()) {
                errorLabel.setText("Connection Error: Could not connect to the server.");
            } else {
                if (!userManager.checkUser(username, password)) {
                    // If the credentials are not valid, display an error message
                    errorLabel.setText("Invalid username or password.");
                } else {
                    // If the credentials are valid, proceed with the login process
                    //TODO: send the credentials to PlayerManager for checking and creating a new Player instance that will get sent to TurnQuest class.
                    PlayerManager playerManager = new PlayerManager();
                    Player player = playerManager.getPlayer(username);
                    setCurrentPlayer(player);
                    hide();
                    game.setScreen(new GameScreen(game));
                }
            }
        }
        else super.hide();
    }

    @Override
    public void hide() {
        // Only hide the dialog if the credentials are valid, this makes it so  that the dialog is not closed whenever a button is pressed but when it needs to.
        if (userManager.checkUser(username, password)) {
            super.hide();
        }
    }

    @Override
    public float getPrefWidth() {
        // Set the preferred width of the dialog
        return 800f;
    }

    @Override
    public float getPrefHeight() {
        // Set the preferred height of the dialog
        return 500f;
    }
}
