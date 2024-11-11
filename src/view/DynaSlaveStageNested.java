package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.CartesianCoordinate;
import model.Theater;
import model.Theater.Stage;
/**
 * This panel shows selected stage and permits to change it. 
 * It has current stage description.
 */
public class DynaSlaveStageNested extends JPanel {
	/**
	 * Serialized key for Serialization
	 */
	private static final long serialVersionUID = 3516257877071345701L;
	/**
	 * DynaSlaveCard container of this nested panel
	 */
	private DynaSlaveStage dss;
	/**
	 * Map between Stage label and the relative image
	 */
	private Map<Stage,ImageIcon> stagePresentations;
	/**
	 * Cursor Pointer images
	 */
	private ImageIcon iconLeftArrow, iconRightArrow;
	/**
	 * Cursor pointer width
	 */
	private final int WIDTH_BUTTON = 50;
	/**
	 * Cursor pointer height
	 */
	private final int HEIGHT_BUTTON = 50;
	/**
	 * Stage values
	 */
	private final Stage[] stageValues = Stage.values();
	/**
	 * Maximum index of stage values
	 */
	private final int stageSize = stageValues.length -1 ;
	/**
	 * Pointer of current Stage
	 */
	private int pointer = 0;
	/**
	 * Label for current stage name
	 */
	private JLabel stageName;
	/**
	 * Panel in which stage image is drawn
	 */
	private JPanel centerPanel;
	/**
	 * Label with stage image
	 */
	private JLabel paintLabel;
	/**
	 * Left arrow pointer
	 */
	private JButton leftArrow;
	/**
	 * Right arrow cursor
	 */
	private JButton rightArrow;
	/**
	 * description of current stage
	 */
	private JLabel stageDescription;
	
//	private final String test = 
//			"<html><body>"
//			+ "<p> another day, another story--------------------- </p>"
//			+ "<p> pantagruelic adventures pantagruelic adventures </p>"
//			+ "<p> pantagruelic adventures pantagruelic adventures </p>"
//			+ "<p> pantagruelic adventures pantagruelic adventures </p>"
//			+ "<p> pantagruelic adventures pantagruelic adventures </p> </body></html>";
	/**
	 * Description for City Stage
	 */
	private final String cityDescription = 
			"<html><body>"
			+ "<p> Panic spreading throughout the city.            </p>"
			+ "<p> Aliens actually exist and they have just invaded</p>"
			+ "<p> Planet Earth. They're eating our brains,        </p>"
			+ "<p> Bomberman please rescue us all !                </p>"
			+ "<p> </p>"
			+ "<p> Difficulty MEDIUM                               </p> </body></html>";
	/**
	 * Description for Jungle Stage
	 */
	private final String jungleDescription = 
			"<html><body>"
			+ "<p> Among jungle's deep backlines,  </p>"
			+ "<p> a terroristic cell is trying to create the ultimate bomberman. </p>"
			+ "<p> You have got to find this prototype and destroy it before massive production. </p>"
			+ "<p> Pay attention to its supersonic weapons. </p>"
			+ "<p> </p>"
			+ "<p> Difficulty HARD </p> </body></html>";
	/**
	 * Description for Classical Stage
	 */
	private final String classicalDescription = 
			"<html><body>"
			+ "<p> Another story for another day. </p>"
			+ "<p> A pantagruelic adventure into  </p>"
			+ "<p> your Self now begins. </p>"
			+ "<p> Your mission is to win against The Null. </p>"
			+ "<p> </p>"
			+ "<p> Difficulty EASY                </p> </body></html>";
	
	// Parameters of this component in the DynaSlavePlayer
	/**
	 * Cartesian point of origin of this nested panel in its container
	 */
	private CartesianCoordinate origin;
	/**
	 * Height of this nested panel
	 */
	private int height;
	/**
	 * Width of this nested panel
	 */
	private int width;
	/**
	 * Constructor.
	 * This panel shows selected stage and permits to change it. 
	 * @param dss DynaSlaveCard in which this panel is nested
	 */
	public DynaSlaveStageNested(DynaSlaveStage dss) {

		this.dss = dss;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Color.LIGHT_GRAY);
		this.stagePresentations = new HashMap<>();
		
		// Load images ---------------------------------------------
		try {
			for(Stage s : Theater.Stage.values()) {
				stagePresentations.put(
						s, 
						new ImageIcon(
								ImageIO.read(getClass()
									.getResourceAsStream(
											"/stagePresentation/"
													+ s.getStageName()
													+ "Stage.png"
											))
								)
						);
			}
			
			iconLeftArrow = new ImageIcon(
									ImageIO.read(getClass()
											.getResourceAsStream(
													"/iconsClassical/leftArrow.png"
													)));
			
			iconRightArrow = new ImageIcon(
									ImageIO.read(getClass()
											.getResourceAsStream(
													"/iconsClassical/rightArrow.png"
													)));

		} catch (IOException e) {e.printStackTrace();}
		
		// North ---------------------------------------------------
		
		this.add(Box.createRigidArea(new Dimension(5, 0)));
		
		stageName = new JLabel();
		stageName.setAlignmentX(CENTER_ALIGNMENT);
		stageName.setAlignmentY(CENTER_ALIGNMENT);
		this.add(stageName);
		
		this.add(Box.createRigidArea(new Dimension(5, 0)));
		
		// ----- CENTER PANEL --------------------------------------
		centerPanel = new JPanel();
		centerPanel.setBackground(Color.BLUE);
		centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,30));
		
		// west
		this.leftArrow = new JButton();
		leftArrow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				decPointer();
				dss.getMaster().requestFocusInWindow();
			}
			
		});
		leftArrow.setOpaque(true);
		leftArrow.setBackground(Color.BLUE);
		leftArrow.setBorderPainted(false);
		leftArrow.setPreferredSize(new Dimension(WIDTH_BUTTON,HEIGHT_BUTTON));
		iconLeftArrow = new ImageIcon(
				iconLeftArrow.getImage()
				.getScaledInstance( WIDTH_BUTTON, HEIGHT_BUTTON, Image.SCALE_SMOOTH ));
		leftArrow.setIcon(iconLeftArrow);
		centerPanel.add(leftArrow);
		
		centerPanel.add(Box.createHorizontalStrut(30));
		
		// Center
		this.paintLabel = new JLabel();
		centerPanel.add(paintLabel);
		
		centerPanel.add(Box.createHorizontalStrut(30));
		
		// East
		this.rightArrow = new JButton();
		rightArrow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				incPointer();
				dss.getMaster().requestFocusInWindow();
			}
			
		});
		rightArrow.setOpaque(true);
		rightArrow.setBackground(Color.BLUE);
		rightArrow.setBorderPainted(false);
		rightArrow.setPreferredSize(new Dimension(WIDTH_BUTTON,HEIGHT_BUTTON));
		iconRightArrow = new ImageIcon(
				iconRightArrow.getImage()
				.getScaledInstance( WIDTH_BUTTON, HEIGHT_BUTTON, Image.SCALE_SMOOTH ));
		rightArrow.setIcon(iconRightArrow);
		centerPanel.add(rightArrow);
		
		// Add centerPanel to this
		centerPanel.setAlignmentX(CENTER_ALIGNMENT);
		centerPanel.setAlignmentY(CENTER_ALIGNMENT);
		this.add(centerPanel);
		// ---------------------------------------------------------
		
		// South
		this.add(Box.createRigidArea(new Dimension(5, 0)));
		
		this.stageDescription = new JLabel("", SwingConstants.CENTER);
		stageDescription.setAlignmentX(CENTER_ALIGNMENT);
		stageDescription.setAlignmentY(CENTER_ALIGNMENT);
//		stageDescription.setPreferredSize(new Dimension(100,100));
		stageDescription.setOpaque(true);
		stageDescription.setBackground(Color.WHITE);
		this.add(stageDescription);

		this.add(Box.createRigidArea(new Dimension(5, 0)));
		
		// ---------------------------------------------------------
		
		this.refresh();
	}
	
	// Getters
	@Override
	public int getWidth() {return width;}
	@Override
	public int getHeight() {return height;}
	/**
	 * Getter of this panel origin point
	 * @return its origin point
	 */
	public CartesianCoordinate getOrigin() {return origin;}
	/**
	 * Getter of DynaSlaveStage
	 * @return its related DynaSlaveStage
	 */
	public DynaSlaveStage getDSS() {return dss;}
	// Setters
	/**
	 * Setter of origin point
	 * @param origin new origin point
	 */
	public void setOrigin(CartesianCoordinate origin) {this.origin = origin;}
	/**
	 * Setter of this panel width
	 * @param width new width value
	 */
	public void setWidth(int width) { this.width = width;}
	/**
	 * Setter of this panel height
	 * @param height new height value
	 */
	public void setHeight(int height) {this.height = height;}
	
	// Pointer Methods
	/**
	 * Increment current pointer by one
	 */
	public void incPointer() {
		if(pointer>=stageSize) {
			pointer = 0;
		} else {
			pointer++;
		}
		refresh();
	}
	/**
	 * Decrement current pointer by one
	 */
	public void decPointer() {
		if(pointer<=0) {
			pointer = stageSize;
		} else {
			pointer--;
		}
		refresh();
	}
	/**
	 * Getter of selected stage
	 * @return the selected stage
	 */
	public Stage getSelectedStage() { return stageValues[pointer];}
	
	// Private Methods
	/**
	 * Setter of stageDescription text
	 * @param stageDescription the new description to set
	 */
	private void descriptionSetter(JLabel stageDescription) {
		switch(getSelectedStage()) {
		case STAGE_1 -> {stageDescription.setText(cityDescription);}
		case STAGE_2 -> {stageDescription.setText(jungleDescription);}
		case STAGE_3 -> {stageDescription.setText(classicalDescription);}
		default -> {}
		}
	}
	/**
	 * Update this View with current values
	 */
	private void refresh() {
		stageName.setText(getSelectedStage().getStageName().toUpperCase()+" STAGE");
		paintLabel.setIcon(stagePresentations.get(getSelectedStage()));
		descriptionSetter(stageDescription);
	}

}
