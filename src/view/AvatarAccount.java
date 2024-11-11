package view;
/**
 * Account Avatar.
 */
public enum AvatarAccount {
	/**
	 * AvatarAccount BALOON - Type
	 */
	BALOON("Baloon",11), 
	/**
	 * AvatarAccount BOMBERMAN - Type
	 */
	BOMBERMAN("Bomberman",10), 
	/**
	 * AvatarAccount DINAH - Type
	 */
	DINAH("Dinah",15), 
	/**
	 * AvatarAccount DROPPY - Type
	 */
	DROPPY("Droppy",10), 
	/**
	 * AvatarAccount DROWZEE - Type
	 */
	DROWZEE("Drowzee",11),
	/**
	 * AvatarAccount FLIPPER - Type
	 */
	FLIPPER("Flipper",11), 
	/**
	 * AvatarAccount FUNCKY_GALLO - Type
	 */
	FUNCKY_GALLO("FunckyGallo",10), 
	/**
	 * AvatarAccount PINGU - Type
	 */
	PINGU("Pingu",11), 
	/**
	 * AvatarAccount QUACK - Type
	 */
	QUACK("Quack",11), 
	/**
	 * AvatarAccount SCHWIFTY - Type
	 */
	SCHWIFTY("Schwifty",13), 
	/**
	 * AvatarAccount TIGRO - Type
	 */
	TIGRO("Tigro",10);
	/**
	 * Specific name to find this' path.
	 */
	private String namePath;
	/**
	 * Images total number.
	 */
	private Integer numberForms;
	/**
	 * Constructor of an Account Avatar.
	 * @param namePath this name to find its path
	 * @param numberForms this images total number
	 */
	private AvatarAccount(String namePath, Integer numberForms) {
		this.namePath = namePath;
		this.numberForms = numberForms;
	}
	/**
	 * Getter of path name.
	 * @return this path name
	 */
	public String getNamePath() {return namePath;}
	/**
	 * Getter of images total number.
	 * @return this images total number
	 */
	public Integer getNumberOfForms() {return numberForms;}
}
