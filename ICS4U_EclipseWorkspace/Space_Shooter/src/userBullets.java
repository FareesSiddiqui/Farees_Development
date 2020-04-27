import processing.core.*;
import java.util.ArrayList;
import static processing.core.PApplet.dist;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Farees
 */
public class userBullets {
    int leftX, rightX, botY, speed, w, h, r, rH, rW;
    protected final PApplet pp;
        
    boolean keys[] = new boolean[2];
    boolean shot = false;
    userBullets(int speed1, int w1, int h1, int rH1, int rW1, PApplet bulletApp){
        this.rH = rH1;
        this.rW = rW1;
        this.pp = bulletApp;
        this.leftX = playerShip.x-36;
        this.w = w1;
        this.h = h1;
        this.rightX = playerShip.x+37;
        this.botY = playerShip.y - 16;
        this.speed = speed1;
    }
        
    void keyP(){
        if(pp.key == ' '){
            keys[1] = true;
            userBullets bullet;
            bullet = new userBullets(5, 80, 80, 13, 30, pp);
            Space_Shooter.bullets.add(bullet);
        }
    }
    void keyR(){
        if(pp.key == ' ')
            keys[1] = false;
    }
    
    void removeBullets(){
        int x = 0;
        while(x < Space_Shooter.bullets.size() - 1){
            x++;
            if(Space_Shooter.bullets.get(x).botY <= 0){
                Space_Shooter.bullets.remove(x);
            }
        }
    }
}
