package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import model.Gravedigger;
/**
 * Decorated Gravedigger related to a score acquisition by the player event.
 */
public class DecoratedGravediggerScore extends DecoratorGravedigger {
	/**
	 * Images of the orange version numbers of this score
	 */
	protected List<BufferedImage> orangeNumbers;
	/**
	 * Images of the orange version numbers of this score
	 */
	protected List<BufferedImage> blueNumbers;
	/**
	 * State in which ending footage condition became true
	 */
	private final static int finalState = 16;
	/**
	 * Constructor 
	 * @param component Gravedigger component to decorate
	 */
	public DecoratedGravediggerScore(Gravedigger component) {
		super(component);
		String scoreString =component.getScoreString();
		this.orangeNumbers = bufferedImagesConverter(scoreString,"/orangeScoreNumber/orangeScoreNumber");
		this.blueNumbers = bufferedImagesConverter(scoreString,"/blueScoreNumber/blueScoreNumber");
		
	}
	/**
	 * Stream method to load numbers images from the score String.
	 * @param scoreString String that gets the number
	 * @param path images path for the requested color version
	 * @return footage as a numbers images list
	 */
	private List<BufferedImage> bufferedImagesConverter(String scoreString, String path){
		return Arrays.asList(scoreString.split(""))
				.stream()
				.map(s -> {
							try {
								return ImageIO.read(getClass()
										.getResourceAsStream(path+s+".png"));
							} catch (IOException e) {e.printStackTrace();}
							return null;
						})
				.collect(Collectors.toCollection(ArrayList::new));
	}
	/**
	 * Boolean state is used to switch from one color to the other
	 * @return list of other colored numbers images
	 */
	public List<BufferedImage> getListImages() {
		if(boolState) {
			return orangeNumbers;
		} else {
			return blueNumbers;
		}
	}
	
	@Override
	public boolean endFootage() {
		return actualState>=finalState;
	}

}
