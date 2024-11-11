package model;

/**
 * Unequivocal label of a specific level inserted into a determined stage.
 * A Theater object is composed by two fields: stage field univocally means the stage,
 * scene field instead means that peculiar stage's level.
 * This way every single Theater means a different object.
 */
public enum Theater {
	/**
	 * LEVEL_1_1 Theater
	 */
	LEVEL_1_1(Stage.STAGE_1,Scene.SCENE_1),
	/**
	 * LEVEL_1_2 Theater
	 */
	LEVEL_1_2(Stage.STAGE_1,Scene.SCENE_2),
	/**
	 * LEVEL_1_3 Theater
	 */
	LEVEL_1_3(Stage.STAGE_1,Scene.SCENE_3),
	/**
	 * LEVEL_1_4 Theater
	 */
	LEVEL_1_4(Stage.STAGE_1,Scene.SCENE_4),
	/**
	 * LEVEL_1_5 Theater
	 */
	LEVEL_1_5(Stage.STAGE_1,Scene.SCENE_5),
	/**
	 * LEVEL_1_6 Theater
	 */
	LEVEL_1_6(Stage.STAGE_1,Scene.SCENE_6),
	/**
	 * LEVEL_1_7 Theater
	 */
	LEVEL_1_7(Stage.STAGE_1,Scene.SCENE_7),
	/**
	 * LEVEL_1_8 Theater
	 */
	LEVEL_1_8(Stage.STAGE_1,Scene.SCENE_8),
	/**
	 * LEVEL_2_1 Theater
	 */
	LEVEL_2_1(Stage.STAGE_2,Scene.SCENE_1),
	/**
	 * LEVEL_2_2 Theater
	 */
	LEVEL_2_2(Stage.STAGE_2,Scene.SCENE_2),
	/**
	 * LEVEL_2_3 Theater
	 */
	LEVEL_2_3(Stage.STAGE_2,Scene.SCENE_3),
	/**
	 * LEVEL_2_4 Theater
	 */
	LEVEL_2_4(Stage.STAGE_2,Scene.SCENE_4),
	/**
	 * LEVEL_2_5 Theater
	 */
	LEVEL_2_5(Stage.STAGE_2,Scene.SCENE_5),
	/**
	 * LEVEL_2_6 Theater
	 */
	LEVEL_2_6(Stage.STAGE_2,Scene.SCENE_6),
	/**
	 * LEVEL_2_7 Theater
	 */
	LEVEL_2_7(Stage.STAGE_2,Scene.SCENE_7),
	/**
	 * LEVEL_2_8 Theater
	 */
	LEVEL_2_8(Stage.STAGE_2,Scene.SCENE_8),
	/**
	 * LEVEL_3_1 Theater
	 */
	LEVEL_3_1(Stage.STAGE_3,Scene.SCENE_1),
	/**
	 * LEVEL_3_2 Theater
	 */
	LEVEL_3_2(Stage.STAGE_3,Scene.SCENE_2),
	/**
	 * LEVEL_3_3 Theater
	 */
	LEVEL_3_3(Stage.STAGE_3,Scene.SCENE_3),
	/**
	 * LEVEL_3_4 Theater
	 */
	LEVEL_3_4(Stage.STAGE_3,Scene.SCENE_4),
	/**
	 * LEVEL_3_5 Theater
	 */
	LEVEL_3_5(Stage.STAGE_3,Scene.SCENE_5),
	/**
	 * LEVEL_3_6 Theater
	 */
	LEVEL_3_6(Stage.STAGE_3,Scene.SCENE_6),
	/**
	 * LEVEL_3_7 Theater
	 */
	LEVEL_3_7(Stage.STAGE_3,Scene.SCENE_7),
	/**
	 * LEVEL_3_8 Theater
	 */
	LEVEL_3_8(Stage.STAGE_3,Scene.SCENE_8);
	
	/**
	 * This specific Theater stage
	 */
	Stage stage;
	/**
	 * This specific Theater stage's scene
	 */
	Scene scene;
	/**
	 * Theater constructor
	 * @param stage this theater stage
	 * @param scene this theater stage's scene
	 */
	Theater(Stage stage , Scene scene){
		this.stage = stage;
		this.scene = scene;
	}
	
	/**
	 * Stages are level sequences which are characterized by the very same play contexts.
	 */
	public static enum Stage {
		/**
		 * STAGE_1 Stage
		 */
		STAGE_1("1"), 
		/**
		 * STAGE_2 Stage
		 */
		STAGE_2("2"), 
		/**
		 * STAGE_3 Stage
		 */
		STAGE_3("3");
		/**
		 * This stage identifier
		 */
		private final String id;
		/**
		 * Stage constructor
		 * @param id identifier
		 */
		Stage(String id){this.id = id;}
		
		@Override
		public String toString() {
			return this.id;
		}
		/**
		 * Getter of name associated to stage
		 * @return stage name
		 */
		public String getStageName() {
			switch(this) {
			case STAGE_1 -> {return "city";}
			case STAGE_2 -> {return "jungle";}
			case STAGE_3 -> {return "classical";}
			default -> {return "";}
			}
		}
	}
	
	/**
	 * Scene indicates a specific level.
	 * According to the scene it is possible to understand level position
	 * inside stage's level sequence.
	 */
	public static enum Scene {
		/**
		 * SCENE_1 Scene
		 */
		SCENE_1("1"), 
		/**
		 * SCENE_2 Scene
		 */
		SCENE_2("2"), 
		/**
		 * SCENE_3 Scene
		 */
		SCENE_3("3"), 
		/**
		 * SCENE_4 Scene
		 */
		SCENE_4("4"),
		/**
		 * SCENE_5 Scene
		 */
		SCENE_5("5"), 
		/**
		 * SCENE_6 Scene
		 */
		SCENE_6("6"), 
		/**
		 * SCENE_7 Scene
		 */
		SCENE_7("7"), 
		/**
		 * SCENE_8 Scene
		 */
		SCENE_8("8");
		/**
		 * scene identifier
		 */
		private final String id;
		/**
		 * Scene constructor
		 * @param id this identifier
		 */
		Scene(String id){this.id = id;}
		
		@Override
		public String toString() {
			return this.id;
		}
	}
	/**
	 * Getter of Theater stage
	 * @return this stage
	 */
	public Stage getStage() {return stage;}
	/**
	 * Getter of Theater stage's scene
	 * @return this scene
	 */
	public Scene getScene() {return scene;}
	/**
	 * With Stage and Scene given, the specific Theater is then returned.
	 * @param stage given stage
	 * @param scene given scene
	 * @return returned Theater
	 */
	public final static Theater getTheater(Stage stage, Scene scene) {
		Theater t;
		try {
			t = Theater.valueOf("LEVEL_"+stage.toString()+"_"+scene.toString());
		} catch(IllegalArgumentException e) {
			t = Theater.LEVEL_1_1;
		}
		return t;
	}
	/**
	 * With Stage id and Scene id given, the specific Theater is then returned.
	 * @param stageId given stage identifier
	 * @param sceneId given scene identifier
	 * @return returned Theater
	 */
	public final static Theater getTheater(String stageId, String sceneId) {
		Theater t;
		try {
			t = Theater.valueOf("LEVEL_"+stageId+"_"+sceneId);
		} catch(IllegalArgumentException e) {
			t = Theater.LEVEL_1_1;
		}
		return t;
	}
	/**
	 * This method checks if there is the next Theater for this stage.
	 * @param stage actual stage
	 * @param scene actual scene
	 * @return true if there is the next theater
	 */
	public final static boolean hasNextTheaterSameStage(Stage stage, Scene scene) {
		if(nextTheaterSameStage(stage, scene)==null) {
			return false;
		}
		return true;
	}
	
	/**
	 * With actual Stage and actual Scene given, the next Theater is then returned.
	 * @param stage actual stage
	 * @param scene actual scene
	 * @return next Theater of null if there isn't
	 */
	public final static Theater nextTheaterSameStage(Stage stage, Scene scene) {
		Integer i = Integer.parseInt(scene.toString()); 
		String newSceneId = ++i + "";
		Theater t;
		try {
			t = Theater.valueOf("LEVEL_"+stage.toString()+"_"+newSceneId);
		} catch(IllegalArgumentException e) {
			t = null;
		}
		return t;
	}
	

	
//	public static void main(String[] args) {
//		System.out.println(Scene.SCENE_1);
//		Theater t = Theater.getTheater(Stage.STAGE_1,Scene.SCENE_2);
//		System.out.println(t);
//		t = Theater.nextTheaterSameStage(Stage.STAGE_1,Scene.SCENE_2);
//		System.out.println(t);
//		System.out.println(hasNextTheaterSameStage(t.getStage(),t.getScene()));
//		t = Theater.nextTheaterSameStage(Stage.STAGE_1,Scene.SCENE_8);
//		System.out.println(hasNextTheaterSameStage(Stage.STAGE_1,Scene.SCENE_8));
//		System.out.println(t);
//		
//		
//	}
	
}
