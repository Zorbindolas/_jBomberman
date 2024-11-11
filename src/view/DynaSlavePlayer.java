package view;

import java.awt.Graphics2D;

import model.*;
/**
 * The Card in which the User can select the Player Character for the game session.
 * This Card is animated to show selected player character footage.
 */
public class DynaSlavePlayer extends DynaSlaveCard 
implements RelocateSpecialComponents, Animate {
	/**
	 * Serialized key for Serialization
	 */
	private static final long serialVersionUID = 4042842992157874752L;
	/**
	 * The nested panel that represents choosable player characters
	 */
	private DynaSlavePlayerNested selectionPanel;
	/**
	 * tenths of a second counter
	 */
	private int modulusAnimations;
	/**
	 * refreshing animations timer
	 */
	private final int tenthsOfRefreshAnimation = 3; // refresh animation every 3 tenths of second
	/**
	 * second tenths to pause non-looped animations
	 */
	private int tenthsOfPause;
	/**
	 * second tenths to pause non-looped animations
	 */
	private final int limitPauseForAnimation = 10; // 1 second of pause for not-infinity looped animations
	/**
	 * Size of player character list. It regulates arrow pointer positioning.
	 */
	private int jListSize;
	/**
	 * Selected player character index
	 */
	private int actualIndex;
	/**
	 * Constructor.
	 * This card allows User to choose his character for the game session.
	 * @param master CardLayout Master reference
	 */
	public DynaSlavePlayer(DynaMasterCard master) {
		super(
				master, 
				3,
				new String[] {
						"I AM ",
						"SELECT NEXT CHARACTER",
						"BACK"}, 
				new int[] {7,8,9},
				"bgred"
				);

		// selectionPanel
		selectionPanel = new DynaSlavePlayerNested(this);
		selectionPanel.setBounds(0, 0, 10, 10);
		this.add(selectionPanel);
		
		relocateSpecialComponents();
		
		jListSize = selectionPanel.getJavaList().getModel().getSize();

	}
	
	/**
	 *Change the text of the first option consistently with the currently selected character
	 * @param nameCharacterSelected selected player character name
	 */
	public void setTextOptionOfAgreedSelection(String nameCharacterSelected) {
		
		String newOptionText = "I AM "+ nameCharacterSelected;
		
		changeTextOfAnOptionAtRuntime(0,newOptionText);
		
	}
	
	
	@Override
	public void relocateComponents() {
		super.relocateComponents();
		if(selectionPanel!=null) relocateSpecialComponents();
	}
	
	@Override
	public void relocateSpecialComponents() {
		
		int spacer = 30;
		
		CartesianCoordinate newOrigin = new CartesianCoordinate(spacer,meterW);
		selectionPanel.setOrigin(newOrigin);
		selectionPanel.setWidth(meterW*8);
		selectionPanel.setHeight(meterH*6);

		selectionPanel.setBounds(
				selectionPanel.getOrigin().getX(),
				selectionPanel.getOrigin().getY(),
				selectionPanel.getWidth(),
				selectionPanel.getHeight()
				);
	}
	
	@Override
	public void optionSwitcher(int pointer) {
		switch(pointer) {
		case 0 -> {
			master.getFrame().setActualPC(
					selectionPanel.getActualHero());
			changePanel("stage");
			}
		case 1 -> {
			actualIndex++;
			if(actualIndex>=jListSize) actualIndex=0;
			selectionPanel.getJavaList().setSelectedIndex(actualIndex);
		}
		case 2 -> escFunction();
		default -> {}
		}
	}
	
	@Override
	public void escFunction() {
		super.changePanel("start");
		
	}

	@Override
	protected Graphics2D drawStuff(Graphics2D g2) {
		return g2;
	}

	@Override
	public void animate(Object g) {
		modulusAnimations++;
		/* refresh of the master state is every tenth of second
		 I want animation change its frame every [tenthsOfRefreshAnimation] of second */
		if(modulusAnimations>=tenthsOfRefreshAnimation) { // 3thsSec
			modulusAnimations = 0;
			selectionPanel.incFootageSelectedCharacter();
			
			// If animation is not infinite looped then start ++ counter of pause
			if(selectionPanel.isFootageLoopDone()) {
				tenthsOfPause++;
				// if pause is completed then restart the not infinite looped animation
				if(tenthsOfPause>=limitPauseForAnimation) {
					resetTenthsOfPause(); // reset the counter
					selectionPanel.resetFootageLoop(); // reset the animation
				}
			}
			
		}
	}
	/**
	 * reset tenthOfPause to zero.
	 */
	public void resetTenthsOfPause() {tenthsOfPause = 0;}
	
	

}
