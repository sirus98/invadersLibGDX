package com.mygdx.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.SpaceInvaders;

public class WinnerScreen  extends SpaceInvadersScreen {
    BitmapFont bitmapFont;
    SpriteBatch batch;

    public WinnerScreen(SpaceInvaders si) {
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
        bitmapFont.draw(batch, "Best Winner", 270, 280);
        batch.end();

    }
}
