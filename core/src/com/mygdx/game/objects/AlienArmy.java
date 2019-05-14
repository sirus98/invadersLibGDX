package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Assets;
import com.mygdx.game.Timer;

import java.util.Random;

public class AlienArmy {

    int x, y, maxX,yAlien;

    float speed = 8f;

    Array<Alien> aliens;
    Array<AlienShoot> shoots;

    int levelArmy = 1;
    boolean gameOver = false;
    boolean winner = false;
    float moveTimerLimit = 0.8f;
    private int WORLD_HEIGHT;

    Timer moveTimer, shootTimer;
    Random random = new Random();

    AlienArmy(int WORLD_WIDTH, int WORLD_HEIGHT){

        this.x = 0;
        this.y = WORLD_HEIGHT-30;
        this.maxX = 60;
        this.yAlien = y;

        this.WORLD_HEIGHT = WORLD_HEIGHT;

        aliens = new Array<Alien>();
        shoots = new Array<AlienShoot>();

        moveTimer = new Timer(0.8f);
        shootTimer = new Timer(random.nextFloat()%5+1);

        positionAliens();
    }


    void render(SpriteBatch batch){
        for(Alien alien: aliens) {
            alien.render(batch);
        }

        for (AlienShoot shoot: shoots) {
            shoot.render(batch);
        }
    }

    public void update(float delta, Assets assets) {
        moveTimer.update(delta);
        shootTimer.update(delta);

        move();
        shoot(assets);

        for(Alien alien: aliens) {
            alien.update(delta, assets);
        }

        for(AlienShoot shoot: shoots){
            shoot.update(delta, assets);
        }


        removeDeadAliens();
        removeShoots();
    }


    void positionAliens(){
        for (int i = 0; i < 5; i++) {  // fila
            for (int j = 0; j < 11; j++) {  // columna
                aliens.add(new Alien(j*30 + 10, y - i*12));
            }
        }
    }

    void move() {
        System.out.println(x + "  " + speed);
        if (moveTimer.check()){
            x += speed;

            if(x > maxX){

                x = maxX;
                speed *= -1;
                for (Alien alien : aliens) {
                    alien.position.y -=5f;
                }

            } else if(x < 0){
                x = 0;
                speed *= -1;

                for (Alien alien : aliens) {
                    alien.position.y -=5f;
                }
            }

            for (Alien alien : aliens) {
                alien.position.x += speed;
            }
        }
    }

    void shoot(Assets assets){
        if(shootTimer.check() && gameOver == false){
            int alienNum = random.nextInt(aliens.size);

            Alien alien = aliens.get(alienNum);

            shoots.add(new AlienShoot(new Vector2(alien.position)));

            assets.alienSound.play();

            shootTimer.set(random.nextFloat()%5+1);
        }

        if (aliens.size <= 0 && gameOver == false) {
            nextlvl();
        }
    }

    void nextlvl(){
        aliens.clear();
        positionAliens();
        levelArmy++;
        x = 0;  // reset posición de aliens
        this.y = WORLD_HEIGHT-30;   // reset posición de aliens
        if (levelArmy > 4) {
            finish();
        } else {
            moveTimerLimit *= 0.7f;
            moveTimer = new Timer(moveTimerLimit);
        }

    }
    private void finish() {
        aliens.clear();
        gameOver = true;
        winner = true;

    }

    private void removeDeadAliens() {
        Array<Alien> aliensToRemove = new Array<Alien>();
        for(Alien alien: aliens){
            if(alien.state == Alien.State.DEAD){
                aliensToRemove.add(alien);
            }
        }

        for (Alien alien: aliensToRemove){
            aliens.removeValue(alien, true);
        }
    }
    public void removeShoots(){
        Array<AlienShoot> shootsToRemove = new Array<AlienShoot>();
        for(AlienShoot shoot:shoots){
            if(shoot.state == AlienShoot.State.TO_REMOVE){
                shootsToRemove.add(shoot);
            }
        }

        for (AlienShoot shoot: shootsToRemove){
            shoots.removeValue(shoot, true);
        }
    }

}
