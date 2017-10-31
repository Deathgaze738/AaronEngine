package utils;

public class Pokemon {
	
	private int id;
	private int level;
	private int currentExp;
	private int nature;
	private int[] ivs = new int[7];
	private int[] evs = new int[7];
	private int[] baseStats = new int[7];
	private int[] attacks = new int[4];
	
	//Overloaded constructor for preknown pokemon
	public Pokemon(int id, int level, int nature, int currentExp, int[] ivs, int[] evs, int[] baseStats, int[] attacks){
		this.id = id;
		this.level = level;
		this.nature = nature;
		this.currentExp = currentExp;
		this.ivs = ivs;
		this.evs = evs;
		this.attacks = attacks;
		this.baseStats = baseStats;
	}
	
	public int[] getStats(){
		int[] stats = new int[7];
		//HP
		stats[0] = ((2 * baseStats[0] + ivs[0] + (evs[0]/4) * level)/100) + level + 10;
		for(int i = 1; i < 7; i++){
			stats[i] = ((2 * baseStats[i] + ivs[i] + (evs[i]/4) * level)/100) + 5;
		}
		return stats;
	}
}
