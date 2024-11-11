package view;

import java.awt.Graphics2D;
/**
 * The Arches with render specific format for Classical Stage context. 
 */
public class ArchesClassical extends Arches {
	/**
	 * Constructor for ArchesClassical
	 */
	public ArchesClassical() {
		super("/archesClassical/");
	}
	
	@Override
	public void render(Graphics2D g2) {
		super.render(g2);
		
//		BufferedImage arch;
		// Paint superior horizontal and inferior horizontal arcs
		for(int x=0; x<COLUMNS; x++) {
			
			g2.drawImage(
					midNordLeft,
					BORDER_WIDTH+x*UNIT_NORMAL,
					HEIGHT_DOCK,
					UNIT_NORMAL,
					UNIT_SMALL,
					null);
			
			g2.drawImage(
					midSudLeft,
					BORDER_WIDTH+x*UNIT_NORMAL,
					Y_SPAWN_ARCS_MID_SUD,
					UNIT_NORMAL,
					UNIT_SMALL,
					null);
		}
		
		
		// Paint side borders
		
		for(int y=0; y<ROWS-1; y++) {
			
			g2.drawImage(
					midOvestOne,
					0,
					Y_SPAWN_ARCS_MID_OVEST_EST_PARTIAL + UNIT_NORMAL*y,
					BORDER_WIDTH,
					UNIT_NORMAL,
					null);
			
			g2.drawImage(
					midEstOne,
					X_SPAWN_ARCH_NORD,
					Y_SPAWN_ARCS_MID_OVEST_EST_PARTIAL + UNIT_NORMAL*y,
					BORDER_WIDTH,
					UNIT_NORMAL,
					null);
		}
		
	}
	
}
