
import processing.core.*;
import java.util.ArrayList;


/**
 * This is the template of your java Processing sketch You can use the
 * Processing API now.
 *
 * Don't forget that in java, Processing color type doesn't exist, use int
 * instead e.g. int red = color(255,0,0);
 */

public class Space_Shooter extends PApplet {
    public static PImage[] allFrames;
    public static PImage player;
    public static PImage[] game_bg_frames;
    public static PImage normalBullet;
    public static PImage normalEnemy;
    PImage menuBG;
    PImage gameBG;
    int W = 700;
    int H = 700;
    int screen = 1;
    float distanceL;
    float distanceR;
    double score;
    int kills;
    boolean bulletHit = false;
    public static ArrayList<userBullets> bullets;
    static ArrayList<enemyBullets> enemyBullet;
    public static playerShip playerShip;
    public static userBullets userBullets;
    enemy enemy = new enemy(660, -500, 100, 100, 100, 100, 1);
    userBullets uBullets = new userBullets(5, 80, 80, 50, 50, this);
    enemyBullets enemyBullets;
    int bulletCheck, enemyCheck;
    boolean playSound = false;
    
    @Override
    public void settings() {
        //generated size: replace this settings with yours
        size(W, H);
        playerShip = new playerShip(350, 600, 4, this);
        userBullets = new userBullets(5, 80, 80, 50, 50, this);//(Speed, width, height, redius for hitbox, PApplet)
        enemy = new enemy(660, -500, 100, 100, 100, 100, 1);
        enemyBullets = new enemyBullets(5);
        
    }
    
    @Override
    public void setup() {
        //replace this setup below with yours
        player = loadImage("userShip.png");
//        menuBG = new Gif(this, "data/menuBG.gif");//create new instance of gif object
//        gameBG = new Gif(this, "data/gameBG.gif");
        normalBullet = loadImage("normalBullet.png");
        normalBullet.resize(80,80);
        normalEnemy = loadImage("data/enemyShip.png");
        normalEnemy.resize(100,100);
        
        frameRate(200);
        bullets = new ArrayList<userBullets>();
        enemy.enemies = new ArrayList<enemy>();
        enemyBullet = new ArrayList<enemyBullets>();
        
        imageMode(CENTER);
        
        
        
    }
    
    public void spawnEnemies(){
        int x = 0;
        int randSpeed = (int)(Math.random()*(2 - 1))+1;
        rectMode(CENTER);

        if(enemy.enemies.size() < 10){
            enemy.enemies.add(new enemy(660, -200, 100, 100, 70, 100, 1));
        }
        for(int i = 0; i < enemy.enemies.size(); i++){
            enemy.enemies.get(i).y = enemy.enemies.get(i).y+randSpeed;
            noFill();
            noStroke();
            rect(enemy.enemies.get(i).x, enemy.enemies.get(i).y, enemy.enemies.get(i).rH, enemy.enemies.get(i).rW);
            image(Space_Shooter.normalEnemy, enemy.enemies.get(i).x, enemy.enemies.get(i).y, enemy.enemies.get(i).w, enemy.enemies.get(i).h);
        }
    }
    
    
    public void clearEnemyArray(){
        int x=0;

        while(x < enemy.enemies.size()){
            if (enemy.enemies.get(x).y > 900){
                playerShip.health = playerShip.health-30;
                enemy.enemies.remove(x);
                enemy.enemies.add(new enemy(660, -200, 100, 100, 70, 100, 1));
            }
            x++;
        }
    }
    
    void updateEnemyBullets(){ 
        boolean del = false;
        for (int i = 0; i < enemy.enemies.size(); i++) {
            for (int x = 0; x < enemyBullet.size(); x++) {
                enemyBullet.get(x).y = enemyBullet.get(x).y+1;
                if(enemy.enemies.get(i).y > 10){
                    int firstEnemyY = enemy.enemies.get(i).y;
                    enemyBullet.get(x).x = enemy.enemies.get(i).x;
                    enemyBullet.get(x).y = firstEnemyY + 1;
                    image(normalBullet, enemyBullet.get(x).x, enemyBullet.get(x).y);
                    
                    if(enemyBullet.get(x).y > 900){
                        enemyBullet.remove(x);
                        enemyBullet.add(new enemyBullets(1));
                    }
                }
            }
        }
    }
   
    
    public void updateBullets(){
        ellipseMode(CENTER);
        if(userBullets.keys[1] == true)
                userBullets.shot = true;
            
        if(userBullets.shot == true){
            
            for (userBullets i: Space_Shooter.bullets) {
                if(i.botY > 0){
                    i.botY = i.botY-userBullets.speed;
                    noFill();
                    noStroke();
                    rect(i.leftX, i.botY, 13, 30);
                    rect(i.rightX, i.botY, 13, 30);
                    image(Space_Shooter.normalBullet, i.leftX, i.botY, i.w, i.h);
                    image(Space_Shooter.normalBullet, i.rightX, i.botY, i.w, i.h);
                }
                
                    
                if(userBullets.botY < 0){
                
                    Space_Shooter.bullets.remove(i);
                    
                    userBullets.shot = false;    
                }
            }
        }
    }
    
    
    /*
    *Method to check collision between two objectss
    @param x1 the x coordinate of the first object
    @param y1 the y coordinate of the first object
    @param x2 the x coordinate of the second object
    @param y2 the y coordinate of the second object
    @param w1 the width of the first object object
    @param h1 the height of the first object object
    @param w2 the width of the second object object
    @param h2 the height of the second object object
    @return returns if a collision is detected object
    */
    boolean collide(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {  
        // test for collision
        if (x1+w1/2 >= x2-w2/2 && x1-w1/2 <= x2+w2/2 && y1+h1/2 >= y2-h2/2 && y1-h1/2 <= y2+h2/2) {
          return true;    // if a hit, return true
        }else {            // if not, return false
          return false;
        }
    }
    
    /*
    *Method to check collision between enemy and bullet [ One bullet hit reduces health by half and a crit (double bullet hit) instantly kills the enemy ]
    @return does not return anythin
    */
    public void checkBulletCollision(){
        for(int i = 0; i < enemy.enemies.size(); i++){
            for(int x = 0; x < bullets.size(); x++){
                if(collide(bullets.get(x).leftX, bullets.get(x).botY, 13, 30, enemy.enemies.get(i).x, enemy.enemies.get(i).y,70, 100)== true && collide(bullets.get(x).rightX, bullets.get(x).botY, 13, 30, enemy.enemies.get(i).x, enemy.enemies.get(i).y,70, 100)== true && enemy.enemies.get(i).y >10){
                    enemy.enemies.get(i).health = enemy.enemies.get(i).health-100;
                    enemy.enemies.get(i).y = enemy.enemies.get(i).y - 55;
                    bullets.remove(x);
                }
                else if(collide(bullets.get(x).leftX, bullets.get(x).botY, 13, 30, enemy.enemies.get(i).x, enemy.enemies.get(i).y,70, 100)== true || collide(bullets.get(x).rightX, bullets.get(x).botY, 13, 30, enemy.enemies.get(i).x, enemy.enemies.get(i).y,70, 100)== true && enemy.enemies.get(i).y >10){
                    enemy.enemies.get(i).health = enemy.enemies.get(i).health-50;
                    enemy.enemies.get(i).y = enemy.enemies.get(i).y - 20;
                    bullets.remove(x);
                }
                if(enemy.enemies.get(i).health <= 0){
                    kills++;
                    enemy.enemies.remove(i);
                    enemy.enemies.add(new enemy(660, -500, 100, 100, 70, 100, 1));
                }
            }
        }
    }
    
    public void checkPlayerCollision(){
        for(int i = 0; i < enemyBullet.size(); i++){
            if(collide(playerShip.x, playerShip.y, 70, 100, enemyBullet.get(i).x, enemyBullet.get(i).y, 13, 30)== true){
                exit();
            }
        }
    }
    
    public void menu(){//menu screen
        background(255);//set gif to background
        fill(38,59,255,95);
        rect(450, 825, 250,75);
        textSize(50);
        fill(255);
        System.out.println("X: "+mouseX+" Y: "+mouseY);
        text("Play", 534, 879);
        
        if(mouseX >= 450 && mouseY >= 825 && mousePressed == true){
            screen = 2;
        }
    }
    
    
    
    public void game(){
    	background(0);
    	
        score = (kills*13);  
        
        userBullets.removeBullets();
        
        background(0);
        
        updateBullets();
        
        spawnEnemies();
        
        enemyBullets.spawnEnemyBullets();
        
        updateEnemyBullets(); 
                
        checkBulletCollision();

        clearEnemyArray();
        
        playerShip.update();
        
        if(playerShip.health <= 0){
            screen = 3;
        }
        
        textSize(52);
        
        text(playerShip.health, 590, 60);
        
        text("Score: "+(int)score, 0, 119);
        
        println("Score: "+score+" Kills: "+kills+" Health: "+playerShip.health);        
    }
    

    
    @Override
    public void draw() {
        
        if(screen == 1){
            game();
        }
        
        if(screen == 2){
            game();    
        }
        
        if(screen == 3){
            background(115, 255, 115, 255);
            textSize(52);
            text("GAME OVER!", width/2, height/2);
            text("Score: "+(int)score, 0, height/4);
        }
    }
    
    @Override
    public void keyPressed(){
        
        playerShip.keyp();
        userBullets.keyP();
    }
    
    @Override
    public void mousePressed(){
        System.out.println("X: "+mouseX + " Y: " + mouseY);
    }
    
    @Override
    public void keyReleased(){
        playerShip.keyr();
        userBullets.keyR();
    }
    
    public static void main(String[] args) {
        PApplet.main("Space_Shooter");
    }
    
}
