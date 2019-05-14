package com.mygdx.game.screen;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.SpaceInvaders;

public class GameOverScreen extends SpaceInvadersScreen {
    BitmapFont bitmapFont;
    SpriteBatch batch;

    public GameOverScreen(SpaceInvaders si) {
        super(si);
    }

    @Override
    public void show() {
        bitmapFont = new BitmapFont();
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        batch.begin();
        bitmapFont.draw(batch, "GAME OVER", 270, 280);

        batch.end();

    }
}
