package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import controller.DynaManager;
import model.CartesianCoordinate;
import model.Dir;
import model.EnemyPyro;
import model.GameCharacter;
import model.GameModel;
import model.Gravedigger;
import model.NonPlayableCharacter;
import model.NpcMediator;
import model.PlayerCharacter;
/**
 * Implemented version of NpcMediator from Model in which 
 * view components are developed.
 * The Singleton Pattern simplifies the realization 
 * of dependencies towards this mediator.
 */
public class EnjoyNpcMediator extends NpcMediator 
		implements FootageFuncs, MyJBombermanFormat {
	/**
	 * Singleton instance
	 */
	private static EnjoyNpcMediator instance;
	/**
	 * All GameCharacter from the current game session: NPCs with the PlayerCharacter.
	 */
	private List<GameCharacter> gameChars = new ArrayList<>();
	/**
	 * Private Constructor for Singleton Design Pattern
	 */
	private EnjoyNpcMediator() {}
	/**
	 * singleton getInstance method
	 * @return singleton instance of this class
	 */
	public static EnjoyNpcMediator getInstance() {
		if(instance==null) instance = new EnjoyNpcMediator();
		return instance;
	}
	
	@Override
	protected NonPlayableCharacter getPyro(
			CartesianCoordinate cGravity, List<Dir> dirs,
			int h, int w, int speed, NpcMediator npcMediator) {
		EnemyPyro pyro = (EnemyPyro) super.getPyro(
				cGravity,dirs,UNIT_MEGA,UNIT_NORMAL,speed,npcMediator);
		DecoratedNonPlayableCharacter decoratedPyro =
				new DecoratedMrPyroWhiteBlue(pyro);
		return decoratedPyro;
	}
	
	@Override
	public void commitments() {
		npcs.forEach(x-> {
			x.doYourThing();
			DynaManager.getInstance().updateActualXYGrid(x);
		});
	}
	
	@Override
	protected void incScoreGM(NonPlayableCharacter npc) {
		DynaSound.playEffect(9); // npcDeath
		GameModel.getInstance().incScore(
				new DecoratedGravediggerByNpc(
						Gravedigger.setTomb(
								npc.getScoreValue(), 
								npc.getYPanel(), 
								npc.getXPanel()),
						((DecoratedNonPlayableCharacter)npc).getDeathImage(),
						npc.getHeight(),
						npc.getWidth()
						)
				);
	}

	@Override
	public void incFootage() {
		for(NonPlayableCharacter npc : npcs) {
			if(npc.getDir()!=Dir.NONE) {
				((FootageFuncs)npc).incFootage();
			} else {
				((FootageFuncs)npc).resetFootage();
			}
		}
	}

	@Override
	public void resetFootage() {}

	@Override
	public BufferedImage getImage() {
		return null;
	}
	/**
	 * This method allows you to draw all the characters 
	 * respecting the frontal perspective typical of this video game. 
	 * It is necessary to consider the main character within this comparison.
	 * This method is used by the DynaSlaveGame.
	 * A stream is used to order the characters in order to respect 
	 * the proportions (a TreeSet could also have been used).
	 * 
	 * @param g2 graphic context in which operate
	 * @param xPC current PC's x coordinate
	 * @param yPC current PC's y coordinate
	 * @param pcImage current PC's image
	 * @param bFlash current PC's bFlash
	 * @param kantGlasses current PC's kantGlasses
	 * @return updated drawn graphic context
	 */
	public Graphics2D renderNPCsWithPlayer(
			Graphics2D g2, int xPC, int yPC, 
			BufferedImage pcImage, boolean bFlash, boolean kantGlasses) {

		gameChars = new ArrayList<>(npcs);
		gameChars.add(GameModel.getInstance().getActualPC());
		gameChars =	gameChars.stream()
								.sorted((o1, o2)->o1.compareTo(o2))
								.collect(Collectors.toList());
//		System.out.println("----------------");
//		gameChars.forEach(x->{
//			System.out.println(x.toString()+" "+x.getPanelCoords().toString());
//		});
//		System.out.println("----------------");

		for(GameCharacter gc : gameChars) {

			if(!(gc instanceof DecoratedEnemyBoss)) {
				
				if(gc instanceof PlayerCharacter) {
					
					DecoratedPlayerCharacter dpc = (DecoratedPlayerCharacter) gc;
					int hCorr = dpc.getHCorrector();
					
			    	g2.drawImage(
			    			pcImage,   
			    			xPC,
			    			yPC-hCorr, 
							UNIT_NORMAL, 
							UNIT_MEGA+hCorr,
							null); 


					//-------------------------
				} else {
					
					DecoratedNonPlayableCharacter dnpc = (DecoratedNonPlayableCharacter) gc;
					BufferedImage biDnpc = dnpc.getImage();
					if(bFlash && dnpc.getInvincibility()>0) biDnpc = ColorTwister.grayer(biDnpc);
					
					g2.drawImage(
							biDnpc,
			    			dnpc.getXPanel(), 
			    			dnpc.getYPanel(),
							UNIT_NORMAL, 
							UNIT_MEGA,
							null);
					
					// ------ Draw Kant's Glasses
			    	if(kantGlasses) {
						g2.setColor(Color.RED);
						for(CartesianCoordinate c : gc.getActualSolidPoints()) {
							g2.drawRect(
									c.getX(),
									c.getY(), 
									5, 
									5);
						}
			    	}
					
//					System.out.println(
//							dnpc.getActivityTimer()
//							);
				
					// ------ Gravity Centers
//					CartesianCoordinate c = gc.getGravityCoords();
//					g2.setColor(Color.BLUE);
//					g2.drawRect(
//							c.getX(), 
//							c.getY(), 
//							50, 
//							50);
//					g2.setColor(Color.BLUE);
//					for(CartesianCoordinate c : gc.getActualSolidPoints()) {
//						g2.drawRect(
//								c.getX(),
//								c.getY(), 
//								5, 
//								5);
//					}
					//-----------------------
				}
			}
		}
			
		for(GameCharacter gc : gameChars) {
			if(gc instanceof DecoratedEnemyBoss) {
				DecoratedEnemyBoss db = (DecoratedEnemyBoss) gc;
				BufferedImage biDnpc = db.getImage();
				if(bFlash && db.getInvincibility()>0) biDnpc = ColorTwister.grayer(biDnpc);
				g2.drawImage(
						biDnpc,
		    			db.getXPanel(), 
		    			db.getYPanel(),
						UNIT_MEGA*4, 
						UNIT_MEGA*3,
						null);
				// ------ 
//				g2.setColor(Color.BLUE);
//				for(CartesianCoordinate c : gc.getActualSolidPoints()) {
////					CartesianCoordinate c = gc.getGravityCoords();
//					g2.drawRect(
//							c.getX(),
//							c.getY(), 
//							5, 
//							5);
//				}
				//-----------------------
			}
				
		}
		
		return g2;
	}

	

}
