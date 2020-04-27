import processing.core.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends PApplet {
	public static PImage[] img = new PImage[9];

	public static float cameraXpos = 0;
	public static float cameraXvel = 0;

	public static int time;

	public static int score = 0;

	public static int frameCount = 0;;
	public static int FinalScore = 0;
	
	public static int equipedWeapon;

	public static MarioActions mainPlayer;

	public static Animation coinSpin;

	public static ArrayList<Effect> effects = new ArrayList<Effect>();

	public static ArrayList<PVector> coins = new ArrayList<PVector>();
	public static ArrayList<Item> items = new ArrayList<Item>();

	public static float imgScale = 1;

	public static ArrayList<PVector> distroyedBlocks = new ArrayList<PVector>();

	public static ArrayList<QuadBlock> distroyedQuadBlocks = new ArrayList<QuadBlock>();

	public static ArrayList<Goomba> goombaEnemies = new ArrayList<Goomba>();

	public static char[][] grid = new char[1280][100];
	String[] blocksStr;
	public static int gridHeight = 0;
	public static int gridWidth = 0;
	public static int gridSize = 25;

	public static boolean[] keyHeldDown = new boolean[255];
	
	public void settings() {
		size(1280, 720, P2D);
	}
	
	public void setup() {
		loadLevel();
		for (int i = 0; i < 9; i++) {
			img[i] = loadImage("map/LevelPiece" + (i + 1) + ".png");
		}

		coinSpin = new Animation("Coin/", "gif", 4, 2, this);

		PFont font;
		// The font must be located in the sketch's
		// "data" directory to load successfully
		font = createFont("Super-Mario-World.ttf", 32);
		textFont(font, 32);

		// println(img.length);

		noStroke();
		frameRate(40);

		gridWidth = (int) (width * imgScale);
		gridHeight = (int) (height * imgScale);

		for (int i = 0; i < gridWidth / gridSize; i++) {
			for (int j = 0; j < gridHeight / gridSize; j++) {
				grid[i][j] = 0;
			}
		}

//		Effect.initEffects();
//		Gun.initGuns();

//		loadLevel();

		mainPlayer = new MarioActions(500, 525, this);
	}
	
	void loader(String path) {
		String[] MarioLevelFile = loadStrings(path);
	}

	public void loadLevel()
	{
	  score = 0;
	  FinalScore = 0;
	  time = 230;
	  
	  items.clear();
	  
	  String[] MarioLevelFile = loadStrings("map/MarioLevel1.txt");
	  for (int i=0; i<MarioLevelFile.length; i++)
	  {
	    String[] line = split(MarioLevelFile[i],':');
	    int col = Integer.parseInt(line[0]);
	    
	    blocksStr = split(line[1],',');

	    int[] blocks = new int[blocksStr.length];
	    
	    
	    
//	    for (int z = 0; z < blocksStr.length; z++) {
//	    	blocks[z] = Integer.parseInt(blocksStr[z]);
//	    }
	    
	    for (int j=0; j<blocks.length-1; j++)
	    {
	      grid[col][blocks[j]] = 1;
	    }
	  }
	  System.out.println(blocksStr);
	  
	  distroyedBlocks.clear();
	  distroyedQuadBlocks.clear();
	  
	  String[] QuadBlocksFile = loadStrings("map/QuadBlocks.txt");
	  for (int i=0; i<QuadBlocksFile.length; i++)
	  {
	    String[] line = split(QuadBlocksFile[i],':');
	    int QuadBlockX = Integer.parseInt(line[0]);
	    int QuadBlockY = Integer.parseInt(line[1]);
	    int QuadBlockType = Integer.parseInt(line[2]);
	    distroyedQuadBlocks.add(new QuadBlock(QuadBlockX,QuadBlockY,QuadBlockType, mainPlayer.pp));
	  }
	  
	  goombaEnemies.clear();
	  String[] GoombaFile = loadStrings("map/GoombaRanges.txt");
	  for (int i=0; i<GoombaFile.length; i++)
	  {
	    String[] line = split(GoombaFile[i],':');
	    int GoombaX = Integer.parseInt(line[0]);
	    int GoombaY = Integer.parseInt(line[1]);
	    int rangeL = Integer.parseInt(line[2]);
	    int rangeR = Integer.parseInt(line[3]);
	    
	    Goomba newGoomba = new Goomba(GoombaX,GoombaY, mainPlayer.pp);
	    newGoomba.setPath(rangeL,rangeR);
	    goombaEnemies.add(newGoomba);
	  }
	  
	  coins.clear();
	  String[] CoinFile = loadStrings("map/Coins.txt");
	  for (int i=0; i<CoinFile.length; i++)
	  {
	    String[] line = split(CoinFile[i],':');
	    int CoinX = Integer.parseInt(line[0]);
	    int CoinY = Integer.parseInt(line[1]);
	    
	    PVector newCoin = new PVector(CoinX,CoinY);
	    coins.add(newCoin);
	  }
	}

	static boolean SegmentIntersectRectangle(double a_rectangleMinX, double a_rectangleMinY, double a_rectangleMaxX,
			double a_rectangleMaxY, double a_p1x, double a_p1y, double a_p2x, double a_p2y) {
		// Find min and max X for the segment

		double minX = a_p1x;
		double maxX = a_p2x;

		if (a_p1x > a_p2x) {
			minX = a_p2x;
			maxX = a_p1x;
		}

		// Find the intersection of the segment's and rectangle's x-projections

		if (maxX > a_rectangleMaxX) {
			maxX = a_rectangleMaxX;
		}

		if (minX < a_rectangleMinX) {
			minX = a_rectangleMinX;
		}

		if (minX > maxX) // If their projections do not intersect return false
		{
			return false;
		}

		// Find corresponding min and max Y for min and max X we found before

		double minY = a_p1y;
		double maxY = a_p2y;

		double dx = a_p2x - a_p1x;

		if (dx > 0.0000001 || dx < -0.0000001) {
			double a = (a_p2y - a_p1y) / dx;
			double b = a_p1y - a * a_p1x;
			minY = a * minX + b;
			maxY = a * maxX + b;
		}

		if (minY > maxY) {
			double tmp = maxY;
			maxY = minY;
			minY = tmp;
		}

		// Find the intersection of the segment's and rectangle's y-projections

		if (maxY > a_rectangleMaxY) {
			maxY = a_rectangleMaxY;
		}

		if (minY < a_rectangleMinY) {
			minY = a_rectangleMinY;
		}

		if (minY > maxY) // If Y-projections do not intersect return false
		{
			return false;
		}

		return true;
	}

	public void keyPressed() {
		
		if (keyCode == 'G' && keyHeldDown['G'] == false) {
			int x1 = mainPlayer.quadify(mouseX + cameraXpos) * gridSize;
			int y1 = (mainPlayer.quadify(mouseY + gridSize) - 1) * gridSize;
			println(x1 + ":" + y1);

			Effect newEffect = new Effect(1, x1 - 120, y1 - 50, this);
			effects.add(newEffect);
		} else if (keyCode == '1' && keyHeldDown['1'] == false) {
			equipedWeapon = 1;
		} else if (keyCode == '2' && keyHeldDown['2'] == false) {
			equipedWeapon = 2;
		}

		if (keyCode == 'B' && keyHeldDown['B'] == false) {
			println((mainPlayer.gridify((int) (mouseX + cameraXpos)) * gridSize) + ":" + (mainPlayer.gridify((int) (mouseY)) * gridSize));
		}

		keyHeldDown[keyCode] = true;

		if (keyCode == 'R') {
			mainPlayer.resetMario(500, 525);

			loadLevel();
		} else if (keyCode == 'H') {
			for (int i = 0; i < gridWidth / gridSize; i++) {
				String str = i + ":";
				int count = 0;
				;
				for (int j = 0; j < gridHeight / gridSize; j++) {
					if (grid[i][j] == 1) {
						count++;
						str += j + ",";
					}
				}
				if (count != 0)
					println(str);
			}
		}
	}

	public void keyReleased() {
		keyHeldDown[keyCode] = false;
	}

	public void mousePressed() {
		/*
		 * if(mouseButton==LEFT) {
		 * grid[(int)((cameraXpos+mouseX)/gridSize)][(int)(mouseY/gridSize)] = 1; } else
		 * if(mouseButton==RIGHT) {
		 * grid[(int)((cameraXpos+mouseX)/gridSize)][(int)(mouseY/gridSize)] = 0;
		 * distroyedBlocks.add(new
		 * PVector((int)((cameraXpos+mouseX)/gridSize)*gridSize,(int)(mouseY/gridSize)*
		 * gridSize)); //println(distroyedBlocks.size()); }
		 */
	}

	public void draw() {
	  background(0,100,190);

	  cameraXpos += cameraXvel;
	  if(cameraXpos<0)
	    cameraXpos = 0;
	  if(cameraXpos>9295)
	    cameraXpos = 9295;
	    
	  cameraXvel = (mainPlayer.x-(cameraXpos+width/2))/10;
	  //println((mainPlayer.x-(cameraXpos+width/2)));
	  
	  if (keyHeldDown['W'] || keyHeldDown[' '])
	  {
	    mainPlayer.jump();
	  }
	  pushMatrix();
	  
	  translate(-cameraXpos, 0);

	  pushMatrix();

	  scale(imgScale);
	  
	  int imageNumber1 = (int)(cameraXpos/(1280));
	  int imageNumber2 = (int)(cameraXpos/(1280))+1;
	  
	  if(imageNumber1<=9&&imageNumber1>=0)
	    image(img[imageNumber1], imageNumber1*1280, 0);
	  if(imageNumber2<=9&&imageNumber2>=0)
	    image(img[imageNumber2], imageNumber2*1280, 0);

	  popMatrix();
	  
	  fill(93, 148, 251);
	  for (int i=0; i<distroyedQuadBlocks.size(); i++)
	  {
	    if(abs(mainPlayer.x-distroyedQuadBlocks.get(i).x)<1200)
	    {
	      distroyedQuadBlocks.get(i).display();
	    }
	  }
	  
	  for (int i=0; i<distroyedBlocks.size(); i++)
	  {
	    if(abs(mainPlayer.x-distroyedBlocks.get(i).x)<1200)
	    {
	      rect(distroyedBlocks.get(i).x, distroyedBlocks.get(i).y, gridSize, gridSize);
	    }
	  }
	  
	  for (int i=0; i<coins.size(); i++)
	  {
	    if(abs(mainPlayer.x-coins.get(i).x)<1200)
	    {
	      coinSpin.displayStill(coins.get(i).x, coins.get(i).y);
	      
	      if(abs(mainPlayer.x-coins.get(i).x)<2*gridSize&&coins.get(i).y-mainPlayer.y>=-23&&coins.get(i).y-mainPlayer.y<=112)
	      {
	        coins.remove(i);
	        score += 100;
	        i--;
	        continue;
	      }
	    }
	  }
	  coinSpin.increaseFrame();
	  
	  for (int i=0; i<items.size(); i++)
	  {
	    if(abs(mainPlayer.x-items.get(i).x)<1200)
	    {
	      items.get(i).update();
	      items.get(i).display();
	      if(abs(mainPlayer.x-items.get(i).x)<2*gridSize&&items.get(i).y-mainPlayer.y>=-23&&items.get(i).y-mainPlayer.y<=112)
	      {
	        items.remove(i);
	        score += 300;
	        i--;
	        continue;
	      }
	    }
	  }
	  
	  boolean bounce = false;
	  for (int i=0; i<goombaEnemies.size(); i++)
	  {
	    if(abs(mainPlayer.x-goombaEnemies.get(i).x)<1200)
	    {
	      if(abs(mainPlayer.x-goombaEnemies.get(i).x)<2*gridSize&&goombaEnemies.get(i).y-mainPlayer.y>=-23&&goombaEnemies.get(i).y-mainPlayer.y<=112)
	      {
	        if(goombaEnemies.get(i).y-mainPlayer.y>=25&&mainPlayer.yvel<0)
	        {
	          bounce = true;
	          
	          if(goombaEnemies.get(i).damage(120))
	          {
	            goombaEnemies.remove(i);
	            score += 100;
	            i--;
	            continue;
	          }
	        }
	        else
	        {
	          mainPlayer.health -= 100;
	        }
	      }
	      goombaEnemies.get(i).display();
	      goombaEnemies.get(i).update();
	    }
	  }
	  if(bounce)
	    mainPlayer.yvel = 30;
	  
	  for (int i=0; i<effects.size(); i++)
	  {
	    effects.get(i).display();
	    if(effects.get(i).doneRendering())
	    {
	      effects.remove(i);
	      score += 100;
	      i--;
	      continue;
	    }
	  }
	 
	  if(FinalScore==0) 
	  {
	    mainPlayer.update();
	    mainPlayer.display();
	    if(equipedWeapon == 1)
	    {
	      Gun.AK47.display();
	    }
	  }
	  
	  popMatrix();
	  
	  fill(255);
	  text(nf(score,13),width-450,70);
	  
	  text("Time " + nf(time,3),70,70);
	  
	  if(FinalScore==0) 
	  {
	    if(frameCount%40==0)
	    {
	      time--;
	      if(time<0)
	        mainPlayer.health -= 100;
	    }
	    if(mainPlayer.x>=9925)
	    {
	      FinalScore = score + time*13;
	      score = score + time*13;
	    }
	  }
	  
	  
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("Main");
	}

}
