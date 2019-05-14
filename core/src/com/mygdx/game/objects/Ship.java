package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Assets;
import com.mygdx.game.Controls;

public class Ship {

    enum State {
        IDLE, LEFT, RIGHT, SHOOT,DYING, DEAD;
    }

    Vector2 position;

    State state;
    float stateTime;
    float speed = 5;
    int points=0;
    int lives = 2;

    TextureRegion frame, hearth, over;


    Weapon weapon;

    Ship(int initialPosition){
        position = new Vector2(initialPosition, 10);
        state = State.IDLE;
        stateTime = 0;

        weapon = new Weapon();
    }


    void setFrame(Assets assets){
        switch (state){
            case IDLE:
                frame = assets.naveidle.getKeyFrame(stateTime, true);
                break;
            case LEFT:
                frame = assets.naveleft.getKeyFrame(stateTime, true);
                break;
            case RIGHT:
                frame = assets.naveright.getKeyFrame(stateTime, true);
                break;
            case SHOOT:
                frame = assets.naveshoot.getKeyFrame(stateTime, true);
                break;
            case DYING:
                frame = assets.shipcrash.getKeyFrame(stateTime, true);
                break;
            default:
                frame = assets.naveidle.getKeyFrame(stateTime, true);
                break;
        }

        switch (lives){
            case 2:
                hearth = assets.hearth.getKeyFrame(0, false);
                System.out.println(lives);
                break;
            case 1:
                hearth = assets.hearth.getKeyFrame(1, false);
                System.out.println(lives);
                break;
        }
    }

    void render(SpriteBatch batch){
        batch.draw(frame, position.x, position.y);

        weapon.render(batch);

        batch.draw(hearth, 10, 230);
    }

    public void update(float delta, Assets assets) {
        stateTime += delta;

        System.out.println(state);
        switch (state){
            case IDLE:
            case RIGHT:
            case LEFT:
            case SHOOT:
                if (Controls.isLeftPressed()) {
                    setState(State.LEFT);
                    moveLeft();
                } else if (Controls.isRightPressed()) {
                    setState(State.RIGHT);
                    moveRight();
                } else {
                    setState(State.IDLE);
                }

                if (Controls.isShootPressed()) {
                    setState(State.SHOOT);
                    shoot();
                    assets.shootSound.play();
                }
                break;
            case DYING:
                if(assets.shipcrash.isAnimationFinished(stateTime)){
                    setState(State.DEAD);
                    assets.aliendieSound.play();
                }
                break;
        }
        setFrame(assets);
        weapon.update(delta, assets);


    }

    private void setState(State state) {
        this.state = state;
        stateTime = 0;
    }
    void idle(){
        state = State.IDLE;
    }

    void moveLeft(){
        position.x -= speed;
        state = State.LEFT;
    }

    void moveRight(){
        position.x += speed;
        state = State.RIGHT;
    }

    void shoot(){
        state = State.SHOOT;
        weapon.shoot(position.x +16);
    }

    public void damage() {
        lives -= 1;
        if (lives == 0) {
            setState(State.DYING);
        }
    }
}
