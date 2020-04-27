import processing.core.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Farees
 */
public class enemyBullets {
    int x, y, speed, damage;
    
    enemyBullets(int speed1){
        enemy enemy = new enemy(660, -200, 100, 100, 70, 100, 1);
        this.speed = speed1;
        this.y = enemy.y;
        this.x = enemy.x;
    }
    
    void spawnEnemyBullets(){
        if(Space_Shooter.enemyBullet.size() < 10){
            Space_Shooter.enemyBullet.add(new enemyBullets(5));
        }
    }
    
}
