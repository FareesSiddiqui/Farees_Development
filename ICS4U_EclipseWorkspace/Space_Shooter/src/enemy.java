import java.util.ArrayList;
import processing.core.*;
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
public class enemy {
        int x, y, max = 630, min = 40, range = max - min +1, w, h, amount, rW, rH, health, leftBulletX, rightBulletX, bulletY, bulletSpeed;
        public static ArrayList<enemy> enemies;

        enemy(int x1, int y1, int w1, int h1, int rH1, int rW1, int bulletSpeed1){
            this.health = 100;
            this.bulletSpeed = bulletSpeed1;
            this.max = x1;
            this.rH = rH1;
            this.rW = rW1;
            this.w = w1;
            this.h = h1;
            this.x = (int)(Math.random()*(this.max - min))+min;
            this.y = (int)(Math.random()*(y1 -(-400)))+(-400);
        }
        
        
    }