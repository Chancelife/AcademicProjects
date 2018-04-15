package Final;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo {
	//public  static int  GENERATION = 0;
	public static final int MAXGENERATION = 20000;
	public static final int genericInGroup = 100;
	private static Generic bestOfGeneration;
	static PriorityQueue<Generic> pq = new PriorityQueue<>();
	
	public class GeneTask implements Runnable{
		int lo;
		int hi;
		int generation =0;
		String name;
		public GeneTask(int lo, int hi) {
			this.lo=lo;
			this.hi=hi;
		}
		@Override
		public void run() {
			while(lo<hi) {
				Generic generic = new Generic();
				Gene[] genes = new Gene[150];
				// System.out.println("No."+i+" status:");
				for(int j=0;j<generic.getGenearr().length;j++) {
					genes[j] = new Gene();
				}
				generic.setGenearr(genes);
				generic.Mutate();
				generic.Move();
				pq.add(generic);
				lo++;
			}
			this.generation+=1;
			do {
				bestOfGeneration = pq.peek();
//				System.out.println("Generation "+GENERATION+", Position: "
//					+bestOfGeneration.mt.getX()+","+bestOfGeneration.mt.getY()
//					+" Status:"+bestOfGeneration.getState() + " Score:"+bestOfGeneration.getScore()+" GetKey:"+bestOfGeneration.mt.getKey());
				System.out.print("Thread: "+this.name+" ");
				System.out.printf("Generation %5d, Position: %2d,%2d Step: %2d Status:%3d Score:%7.2f FoundGate:%b GetKey:%b OpenGate:%b\n",
					this.generation, bestOfGeneration.mt.getX(), bestOfGeneration.mt.getY(),bestOfGeneration.getStep(), bestOfGeneration.getState(), bestOfGeneration.getScore(), 
					bestOfGeneration.mt.foundGate(), bestOfGeneration.mt.getKey(), bestOfGeneration.mt.openGate());
				doNextGen();
				this.generation++;
			}while(bestOfGeneration.getState()!=3);
			
		}
		
	}
	public static void main(String[] args) { 
		// Do generation 0;
//		for(int i=0;i<genericInGroup;i++) {
//			Generic generic = new Generic();
//			Gene[] genes = new Gene[150];
//			// System.out.println("No."+i+" status:");
//			for(int j=0;j<generic.getGenearr().length;j++) {
//				genes[j] = new Gene();
//			}
//			generic.setGenearr(genes);
//			generic.Mutate();
//			generic.Move();
//			pq.add(generic);
//			// System.out.println("No."+i+" Score:"+generic.getScore());
//			// System.out.println("Done");
//			// Log function here
//		}
//		GENERATION += 1;
//		
//		do {
//			bestOfGeneration = pq.peek();
////			System.out.println("Generation "+GENERATION+", Position: "
////				+bestOfGeneration.mt.getX()+","+bestOfGeneration.mt.getY()
////				+" Status:"+bestOfGeneration.getState() + " Score:"+bestOfGeneration.getScore()+" GetKey:"+bestOfGeneration.mt.getKey());
//			System.out.printf("Generation %5d, Position: %2d,%2d Step: %2d Status:%3d Score:%7.2f FoundGate:%b GetKey:%b OpenGate:%b\n",
//				GENERATION, bestOfGeneration.mt.getX(), bestOfGeneration.mt.getY(),bestOfGeneration.getStep(), bestOfGeneration.getState(), bestOfGeneration.getScore(), 
//				bestOfGeneration.mt.foundGate(), bestOfGeneration.mt.getKey(), bestOfGeneration.mt.openGate());
//			doNextGen();
//		}while(bestOfGeneration.getState()!=3);
		ExecutorService e = Executors.newFixedThreadPool(2);
		Demo d = new Demo();
		GeneTask task1 =  d.new GeneTask(0,100);
		task1.name="ThreadA....";
		GeneTask task2 =  d.new GeneTask(0,100);
		task2.name="ThreadB....";
		e.execute(task1);
		e.execute(task2);
		e.shutdown();
	}
	
	public static void doNextGen() {
		ArrayList<Generic> parents = new ArrayList<>();
		Generic first = pq.peek();
		for(int i=0;i<5;i++) {
			parents.add(pq.poll());
		}
		
		pq.clear();
		pq.add(first);
//		System.out.println(first.getScore());
		for(Generic f:parents) { 
			for(Generic m:parents) {
				 for(int j=0;j<4;j++) {
					Generic generic = new Generic(f, m);
					// generic.Mutate();
					generic.Move();
					pq.add(generic);
				 }
			}
		}
		parents.clear();
		// System.out.println(pq.size());
		
	}
}
