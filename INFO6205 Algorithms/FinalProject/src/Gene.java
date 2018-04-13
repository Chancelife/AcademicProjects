public class Gene {
	public static final int up = 0;
	public static final int left = 1;
	public static final int down = 2;
	public static final int right = 3;
	public Gene(int i) {
		this.gene=i;
	}
	private int gene;
	public int getGene() {
		return gene;
	}
	public void setGene(int gene) {
		this.gene = gene;
	}
	
	
}
