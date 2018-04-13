public class Generic implements Comparable{
	//init state = 0; state=1 means that he knows that he needs a key. state=2 means he has the key. state = 3 means mission complete. 
	private Gene[] genearr;
	private int dx;
	private int dy;
	private int state;
	final double pro = 0.05;
	private int score=0;
	private int step=0;
	private boolean alive =true;  
	
	
	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public int getStep() {
		return step;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Gene[] getGenearr() {
		return genearr;
	}

	public void setGenearr(Gene[] genearr) {
		this.genearr = genearr;
	}


	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Generic(int x, int y) {
		genearr = new Gene [100];
		state =0;
		dx=x;
		dy=y;
	}
	
	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public Generic(Generic father, Generic mother) {
		for(int i =0;i<100;i++) {
			int res=0;
			if(father.genearr[i].getGene()==mother.genearr[i].getGene())
				res= father.genearr[i].getGene();
			else  {
				double flag=Math.random();
				if(flag<0.5)
					res=father.genearr[i].getGene();
				else
					res = mother.genearr[i].getGene();
			}
			this.genearr[i]= new Gene(res);
		}
		if(father.getState()>mother.getState())
			this.state=father.getState();
		else
			this.state=mother.getState();
		if(this.state>1)
			this.state=1;
	}
	
	public void Mutate(Generic generic) {
		for(Gene g : generic.genearr) {
			double f = Math.random();
			if(f<=pro) {
				double c = Math.random();
				if(c<0.25)
					g.setGene(0);
				else if(c<0.5)
					g.setGene(1);
				else if(c<0.75)
					g.setGene(2);
				else
					g.setGene(3);
			}	
		}
	}
	
	public void Move() {
		while(this.state>=0&&this.step<100&&this.isAlive()) {
			switch(this.getGenearr()[step].getGene()) {
			case 0: this.dy++; break;
			case 1: this.dx--; break;
			case 2: this.dy--;break;
			case 3: this.dx++; break;
			}
			//call map to update alive or state
		 if(this.state==3) {
				break;
			}
		 step++;
		}
		//call fitness function
	}

	@Override
	public int compareTo(Object o) {
		Generic that = (Generic)o;
		return this.getScore()-that.getScore();
	}
}
