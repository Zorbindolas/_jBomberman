package view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
/**
 * Personalized CellRenderer for the JList used in DynaSlavePlayerNested.
 */
public class AvatarJListRenderer extends JLabel implements ListCellRenderer<AvatarHeroItem>{
	/**
	 * serialized key for serialization
	 */
	private static final long serialVersionUID = -7503268176743540120L;
	/**
	 * Constructor of this CellRender. It's opaque.
	 */
	public AvatarJListRenderer() {
		this.setOpaque(true); // i need to show the icon, not the background
	}

	@Override
	public Component getListCellRendererComponent(
			JList<? extends AvatarHeroItem> list, 
			AvatarHeroItem value, 
			int index,
			boolean isSelected, 
			boolean cellHasFocus) {
		
		AvatarHeroItem avatar = (AvatarHeroItem)value;
		this.setText(avatar.getCoolPhrase());
		this.setIcon(avatar.getIcon());
		
		if(isSelected) { // the background color changes if selected
			this.setBackground(Color.RED);
			this.setForeground(Color.WHITE);
		} else {
			this.setBackground(Color.WHITE);
			this.setForeground(Color.RED);
		}
		
		if(cellHasFocus) { // the border color changes if selected
			setBorder(BorderFactory.createLineBorder(Color.GREEN));
		} else {
			setBorder(BorderFactory.createEmptyBorder());
		}
				
		return this;
	}
}
