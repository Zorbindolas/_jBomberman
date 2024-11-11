package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import model.CartesianCoordinate;
import model.GameModel;
/**
 * This Card is the first shown by this Application. 
 * It's the starting menu and the welcome to the User.
 */
public class DynaSlaveStart extends DynaSlaveCard implements MyJBombermanFormat {
	/**
	 * Serialized key for Serialization.
	 */
	private static final long serialVersionUID = -3733897038454143570L;
	// Title
	/**
	 * BOMBER sprite for the title
	 */
	private BufferedImage logoBOMBER;
	/**
	 * Current Cartesian Coordinate for BOMBER logo
	 */
	private CartesianCoordinate bomberCoords;
	/**
	 * X cartesian coordinate destination of BOMBER logo
	 */
	private final int bomberGoalX;
	/**
	 * MAN image for title
	 */
	private BufferedImage logoMAN;
	/**
	 * Current Cartesian Coordinate for MAN logo
	 */
	private CartesianCoordinate manCoords;
	/**
	 * X cartesian coordinate destination of MAN logo
	 */
	private final int manGoalX;
	/**
	 * Scroll JAVA image
	 */
	private BufferedImage scrollJava;
	/**
	 * Current Cartesian Coordinate for Scroll JAVA
	 */
	private CartesianCoordinate scrollJavaCoords;
	/**
	 * Logo dimension
	 */
	private int logoW, logoH;
	
	// Airship and Java Logo ScreenSaver
	/**
	 * Airship image
	 */
	private BufferedImage airship;
	/**
	 * Comic speech bubble image
	 */
	private BufferedImage comicSpeechBubble;
	/**
	 * Speeches for comics bubble
	 */
	private final String[] speeches = new String[] {
			"  WHERE IS MY COFFEE qqq     ",
			"  WHERE IS MY CAPPUCCINOqqq  ",
			"  WHERE IS MY MOKACCINO qqq  ",
			"I AM TEN MINUTES HOTCHOCOLATE",
			"     MY MOKA ALWAYS SMILE   ",
			"     DON T BE SO SERIOUS    ",
			"        NO JAVA NO PARTY    ",
			"         TAKE IT EASY       ",
			"       CALL A TAXI PLEASE   ",
			"     YES MOM I AM COMING    "
	};
	/**
	 * Current speech
	 */
	private String speech;
	/**
	 * speech bubble character dimension
	 */
	private final int dimCharBubble = 10;
	/**
	 * JAVA logo image
	 */
	private BufferedImage javaLogo;
	/**
	 * explosion images
	 */
	private List<BufferedImage> explosion;
	/**
	 * Iterator to animate the explosion event
	 */
	private Iterator<BufferedImage> itExplosion;
	/**
	 * Current Airship (or JAVA logo) CartesianCoordinate
	 */
	private CartesianCoordinate airshipCoords;
	/**
	 * Coordinate for the Airship
	 */
	private int xAirS,yAirS;
	/**
	 * Direction modifier for the Airship or JAVA Logo
	 */
	private int modXAir, modYAir;
	/**
	 * True if airship, else if JAVA Logo
	 */
	private boolean isAirShip;
	/**
	 * Final position of the title on the screen
	 */
	private int startTitle;
	/**
	 * Counter for explosions
	 */
	private int counterExplosion;
	/**
	 * True if must be the explosion
	 */
	private int timeToExplode;
	/**
	 * True if airship must became Java logo
	 */
	private int timeToTransform;
	/**
	 * Constructor.
	 * This Card is the first shown by this Application.
	 * @param master CardLayout Master reference
	 */
	public DynaSlaveStart(DynaMasterCard master) {
		super(
				master, 
				4, 
				new String[] {"START","OPTIONS", "ACCOUNT","QUIT"},
				new int[] {6,7,8,9},
				"bgblue"
				);
		// Title
		this.logoW = master.getFrame().getTenthWStand() * 6;
		this.logoH = master.getFrame().getTenthHStand() * 5;
		
		resetLogoTitleCoords();
		
		startTitle = (master.getFrame().getWStand() - logoW) /2;
		bomberGoalX = startTitle;
		manGoalX = startTitle;
		
		// airship
		chooseSpeech();
		explosion = new ArrayList<>();

		resetAirshipCoords();
		
		xAirS = meterH*2;
		yAirS = meterH;
		
		// load png
		try {
			logoBOMBER = ImageIO.read(getClass().getResourceAsStream("/menu/logoBOMBER.png"));
			logoMAN = ImageIO.read(getClass().getResourceAsStream("/menu/logoMAN.png"));
			scrollJava = ImageIO.read(getClass().getResourceAsStream("/menu/scrollJava.png"));
			airship = ImageIO.read(getClass().getResourceAsStream("/menu/airship.png"));
			javaLogo = ImageIO.read(getClass().getResourceAsStream("/menu/javaLogo.png"));
			comicSpeechBubble = ImageIO.read(getClass().getResourceAsStream("/menu/comicSpeachBubble.png"));
			for(int i=0; i<4;i++) 
				explosion.add(ImageIO.read(getClass().getResourceAsStream(
						"/explosion/sphere"+i+".png")));
		} catch (IOException e) {}
		
		this.itExplosion = explosion.iterator();
		
		counterExplosion = 0;
		
		timeToExplode = 400;
		timeToTransform = 600;
		
	}
		
	@Override
	public void optionSwitcher(int pointer) {
		switch(pointer) {
		case 0 -> changePanel("player"); // choose your bomber
		case 1 -> changePanel("option");  // options
		case 2 ->  {
			((DynaSlaveAccount)master.getSpecificPanel("account")).refresh();
			changePanel("account"); // account
		}
		case 3 -> escFunction();
		default -> {}
		}
	}
	
	@Override
	public void escFunction() {

		master.getFrame().dispatchEvent(new WindowEvent(master.getFrame(), WindowEvent.WINDOW_CLOSING));
		GameModel.getInstance().setApp(false);

	}
	/**
	 * Select the speech for this intro
	 */
	private void chooseSpeech() {
		Random rInt = new Random();
		speech = speeches[rInt.nextInt(speeches.length)];
	}
	
	
	@Override
	public Graphics2D drawStuff(Graphics2D g2) {
		
		//System.out.println("sono nello start e questo e' il suo drawStuff");

		long time = master.getTime();
		
		if(time==0) resetLogoTitleCoords();
		
		// Logo "BOMBER"
		g2 = drawTitleLogo(
				g2,logoBOMBER,bomberCoords,
				1,1,
				0,bomberGoalX
				);
		
		// airship & Java Logo Screensaver
		g2 = drawAirshipJava(g2,time);
		
		// Logo "MAN"
		g2 = drawTitleLogo(
				g2,logoMAN,manCoords,
				-1,1,
				0,manGoalX
				);
		
		// Logo JavaScroll
		g2 = drawTitleLogo(
				g2,scrollJava,scrollJavaCoords,
				-1,0,
				0,0
				);
		
		g2 = drawSkyscrapers(g2);

		if(GameModel.getInstance().isReady()) {
			g2.setColor(Color.GREEN);
			g2.fillOval(0, 0, 8, 8);
		}
		
		return g2;
	}
	/**
	 * Set initial ariship coordinates
	 */
	private void resetAirshipCoords() {
		
		int a = (new Random()).nextInt(0,3);
		int b = (new Random()).nextInt(0,3);
		
		isAirShip = true;
		
		airshipCoords = new CartesianCoordinate(
				-meterH*a,-meterW*b);
		
		modXAir = 1;
		modYAir = 1;
		
	}
	/**
	 * Set initial coordinates for all title logo parts
	 */
	private void resetLogoTitleCoords(){
		
		bomberCoords = new CartesianCoordinate(
				0,-logoW);
		
		manCoords = new CartesianCoordinate(
				master.getFrame().getHStand(),0);
		
		scrollJavaCoords = new CartesianCoordinate(
				master.getFrame().getHStand()*3,master.getFrame().getWStand()/5);
		
	}
	/**
	 * Draw the title in input applying a direction
	 * @param g2 graphic context in which we draw
	 * @param logo logo image
	 * @param coords current logo coordinates
	 * @param deltaY y direction modifier
	 * @param deltaX x direction modifier
	 * @param goalY y destination
	 * @param goalX x destination
	 * @return updated drawn graphic context
	 */
	private Graphics2D drawTitleLogo(
			Graphics2D g2, 
			BufferedImage logo, 
			CartesianCoordinate coords,
			int deltaY,
			int deltaX,
			int goalY,
			int goalX
			) {
		
		
		for(int i=0 ; i<15; i++) {
			
			if(coords.getX()!=goalX) coords.setX(coords.getX()+deltaX);
			
			if(coords.getY()!=goalY) coords.setY(coords.getY()+deltaY);
			
		}
		
		g2.drawImage(
				logo,
				coords.getX(), 
				coords.getY(),
				logoW,
				logoH,
				null
				);
		
		return g2;
	}
	/**
	 * Draw the Airship (or Java Logo) on the panel
	 * @param g2 graphic context in which we draw
	 * @param time current panel time
	 * @return updated drawn graphic context
	 */
	private Graphics2D drawAirshipJava(Graphics2D g2, long time) {
		if(isAirShip) {
			
			airshipCoords.setX(airshipCoords.getX()+1);
			airshipCoords.setY(airshipCoords.getY()+1);

			int xOrigin = (int)(
					(counterExplosion>timeToExplode)?
					airshipCoords.getX()+2*Math.sin(time) :
					airshipCoords.getX()
					);

			// Airship	
			g2.drawImage(
					airship,
					xOrigin, 
					(int)(airshipCoords.getY()),
					xAirS,
					yAirS,
					null
					);
			
			// Comic speach bubble	
			g2.drawImage(
					this.comicSpeechBubble,
					(int)(airshipCoords.getX()+(int)(xAirS/2.3)), 
					(int)(airshipCoords.getY()-yAirS),
					(int)(xAirS*2.5),
					yAirS,
					null
					);
			
			// draw speach
//				chooseSpeach();
			g2 = fountainPen(
					g2,
					speech,
					airshipCoords.getX()+(int)(xAirS/1.5),
					airshipCoords.getY()-(int)(yAirS*0.7),
					dimCharBubble,
					dimCharBubble);
			
			if(counterExplosion>timeToExplode) {
				// draw explosion
				if(itExplosion.hasNext()) {
					
					BufferedImage expIm = itExplosion.next();	
					
					if(time%3==0) {
						modXAir = airshipCoords.getX()+(int)(xAirS/4);
						modYAir = (int)(airshipCoords.getY()+(int)(yAirS/3));
					} else if(time%3==1) {
						modXAir = airshipCoords.getX();
						modYAir = airshipCoords.getY();
					} else {
						modXAir = (int)(airshipCoords.getX()+(int)(xAirS/4));
						modYAir = airshipCoords.getY();
					}

					g2.drawImage(
							expIm,
							modXAir, 
							modYAir,
							xAirS,
							yAirS,
							null
							);

				} else { 
					itExplosion = explosion.iterator();
					}
				
			}
			
			counterExplosion++;
			
			isAirShip = counterExplosion < timeToTransform;
			
			modXAir = modYAir = 1;
			
		} else {
			
			setModsCheckingAirshipCollisionWithBorders(
					airshipCoords.getX(),
					airshipCoords.getY(),
					xAirS*2,
					yAirS*2
					);
			
			airshipCoords.setX(airshipCoords.getX()+modXAir);
			airshipCoords.setY(airshipCoords.getY()+modYAir);
			
			// else drawJavaLogo
			g2.drawImage(
					this.javaLogo,
					airshipCoords.getX(), 
					airshipCoords.getY(),//(int)(airshipCoords.getY()+Math.cos(state)),
					xAirS*2,
					yAirS*2,
					null
					);

		}
		return g2;
	}
	/**
	 * Set direction modifiers to obtain
	 * the Wall screen effect for Airship/Java logo moving into the screen
	 * @param x current x coordinate
	 * @param y current y coordinate
	 * @param w airship width
	 * @param h airship height
	 */
	private void setModsCheckingAirshipCollisionWithBorders(
			int x, int y, int w, int h){
		
		if(
				//x>master.getFrame().getWStand() || 
				(x+w)>master.getFrame().getWStand()
				
				){
			modXAir = -1;
			
		} else if(
				
				x<0 
				//||(x+w)<0
				
				) {
			modXAir = 1;
		}
		
		if(
				//y>master.getFrame().getHStand() || 
				(y+h)>master.getFrame().getHStand()
				
				){
			modYAir = -1;
			
		} else if(
				
				y<0 
				//|| (y+h)<0
				
				) {
			modYAir = 1;
		}

	}
	

}
