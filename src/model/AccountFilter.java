package model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains Stream methods to filter a Collection of Account
 */
public class AccountFilter {

	/**
	 * Types of filters
	 */
	public static enum Filters {
		/**
		 * NONE Filter
		 */
		NONE,
		/**
		 * FAVORITE_PLAYER_CHARACTER Filter
		 */
		FAVORITE_PLAYER_CHARACTER,
		/**
		 * AVERANGE_TOILETTE_TIME Filter
		 */
		AVERANGE_TOILETTE_TIME,
		/**
		 * CLUB_OF_THE_BROKEN_HEARTS Filter
		 */
		CLUB_OF_THE_BROKEN_HEARTS,
		/**
		 * SPEEDRUNNERS_RANKING Filter
		 */
		SPEEDRUNNERS_RANKING,
		/**
		 * SCORE_RANKING Filter
		 */
		SCORE_RANKING;

		
		@Override
		public String toString() {
			switch(this) {
			case FAVORITE_PLAYER_CHARACTER -> {return "Favorite PC and Accounts that use it the most";}
			case AVERANGE_TOILETTE_TIME -> {return "Worst of 3 by <Pause Time> ";}
			case CLUB_OF_THE_BROKEN_HEARTS -> {return "Club of The Broken Hearts";}
			case SPEEDRUNNERS_RANKING -> {return "Best of 3 by <Speedrun Time>";}
			case SCORE_RANKING -> {return "Best of 3 by <Best Score>";}
			case NONE -> {return "No filter";}
			default -> {return "No filter";}
			}
		}

	}
	
	// Selector Switcher Methods ------------------------------------------------------
	/**
	 * Manipulate the collection with the chosen filter
	 * @param inList Collection to filter
	 * @param filter filter to use
	 * @return Filtered Collection
	 */
	public static List<Account> filtering(
			List<Account> inList, Filters filter){
		
		switch(filter) {
		case FAVORITE_PLAYER_CHARACTER -> {return commonFavoritePCFilter(inList);}
		case AVERANGE_TOILETTE_TIME -> {return worstPauseTimeFilter(inList);}
		case CLUB_OF_THE_BROKEN_HEARTS -> {return clubBrokenHeartsFilter(inList);}
		case SPEEDRUNNERS_RANKING -> {return speedrunRankingFilter(inList);}
		case SCORE_RANKING -> { return scoreRankingFilter(inList);}
		case NONE -> {return inList;}
		default -> { return inList; }
		}
		
	}
	
	/**
	 * Generate a string answer to a specific chosen filter
	 * @param inList Collection to filter
	 * @param filter filter to use
	 * @return answer to the problem solved by the filter 
	 */
	public static String answer(List<Account> inList, Filters filter) {
		switch(filter) {
		case FAVORITE_PLAYER_CHARACTER -> {return "Favorite PC is "+favoritePCAnswer(inList);}
		case AVERANGE_TOILETTE_TIME -> {return "Averange Toilette time is "+averangeToiletteTime(inList);}
		case CLUB_OF_THE_BROKEN_HEARTS -> {return "Bigger Club of Accounts that share the same broken hearts value";}
		case SPEEDRUNNERS_RANKING -> {return "Complete a stage in the shortest time from lower to higher";}
		case SCORE_RANKING -> { return "Ranking Scores best of 3";}
		case NONE -> {
			int size = inList.size();
			switch(size) {
			case 0 -> {return "There aren't accounts";}
			case 1 -> {return "There is one account";}
			default -> {return "There are "+size+" accounts";}
			}
		}
		default -> { return "---"; }
		}
	}
	
	// Specific Methods for Filtering -------------------------------------------------------------
	/**
	 * Generic model methods for filter "best of 3" adjusted by an appropriate comparator
	 * @param <T> Type of element in the Collection (it's used to be Account)
	 * @param inList Collection in input
	 * @param comp Chosen comparator
	 * @return Collection of 3 elements resulted by the sorted stream
	 */
	private static <T> List<Account> genericFilter(List<Account> inList, Comparator<? super Account> comp){
		return inList.stream()
				   .sorted(comp)
				   .limit(3)
				   .collect(Collectors.toList());
	}
	
	/**
	 * Best of 3 by score
	 * @param inList Collection in input
	 * @return List of 3 Account with best score values
	 */
	private static List<Account> scoreRankingFilter(List<Account> inList) {
		Comparator<Account> scoreRankingComp = 
				(Account o1, Account o2)->-1*((Long)o1.getBestScore()).compareTo(o2.getBestScore());
		return genericFilter(inList,scoreRankingComp);
	}
	/**
	 * Calculate the three worst accounts by pause time
	 * @param inList Collection to filter
	 * @return List of 3 Account with worst pause time
	 */
	private static List<Account> worstPauseTimeFilter(List<Account> inList){
		Comparator<Account> worstPauseComp = 
				(Account o1, Account o2)->-1*((Integer)o1.getPauseTimeTotal()).compareTo(o2.getPauseTimeTotal());
		return genericFilter(inList,worstPauseComp);
	}
	
	/**
	 * Calculate the three best accounts by speed run Time
	 * @param inList Collection to filter
	 * @return List of 3 Account with best speed run Time
	 */
	private static List<Account> speedrunRankingFilter(List<Account> inList){
		Comparator<Account> speedrunComp = 
				(Account o1, Account o2)->((Integer)o1.getSpeedrunTime()).compareTo(o2.getSpeedrunTime());
		return genericFilter(inList,speedrunComp);
	}
	
	/**
	 * Calculate the list containing the accounts that share the same number of lost hearts 
	 * and this number is the most frequent among all the accounts
	 * 
	 * @param inList Collection to filter
	 * @return List of Accounts found
	 */
	private static List<Account> clubBrokenHeartsFilter(List<Account> inList){
		return inList.stream()
					 .collect(   // Map<Integer, List<Account>>
							Collectors.groupingBy(
									Account::getBrokenHeartsTotal,
									Collectors.mapping(
											x->x,
											Collectors.toList()
											)
									)
							)
					 .entrySet().stream()
					 .sorted(
							 (o1,o2) -> -1*((Integer)o1.getValue().size()).compareTo(o2.getValue().size())
							)
					 .findFirst().get().getValue();
				
	}
	
	/**
	 * Select only Accounts that prefer the favorite character of all accounts.
	 * @param inList Collection to filter
	 * @return List of Accounts that used the favorite character PC among all Accounts the most
	 */
	private static List<Account> commonFavoritePCFilter(List<Account> inList){
		Heroes hero = favoritePCAnswer(inList);
		return inList.stream()
					.filter(account -> account.getFavoritePC().equals(hero))
					.collect(Collectors.toList());
	}
	
	// Specific Methods for Answer ----------------------------------------------------------------
	
	/**
	 * average pause time among all players
	 * @param inList Collection to analyze
	 * @return averange pause time
	 */
	private static Double averangeToiletteTime(List<Account> inList){
		return inList.stream()
						.mapToInt(Account::getPauseTimeTotal)
						.average()
						.orElse(0.0);
	}
	
	/**
	 * Calculate the most used PC among all the Accounts
	 * @param inList Collection to analyze
	 * @return PC character used the most by all accounts
	 */
	private static Heroes favoritePCAnswer(List<Account> inList) {
		return inList.stream()
					 .collect(Collectors.toMap(
							 	Account::getFavoritePC,
								x->1,
								(x1,x2)->x1+x2
								)
							)
					 .entrySet().stream()
					 .sorted(
							 (o1,o2) -> -1*((Integer)o1.getValue()).compareTo(o2.getValue())
							)
					 .findFirst().get().getKey();
	}
	
	
}
