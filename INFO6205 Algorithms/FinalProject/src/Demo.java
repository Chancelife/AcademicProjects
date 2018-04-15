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
	public static int GENERATION=0;
	public class OddGeneTask implements Runnable{
		
		private ArrayList<Generic> gs;
		public OddGeneTask() {
			
		}
		@Override
		public void run() {
			for(int i =0;i<gs.size();i+=2) {
				gs.get(i).Move();
				pq.add(gs.get(i));
			}
				
		}
		
		
	}
	
public class EvenGeneTask implements Runnable{
		
		private ArrayList<Generic> gs;
		public EvenGeneTask() {
			
		}
		@Override
		public void run() {
			for(int j =1;j<gs.size();j+=2) {
				gs.get(j).Move();
				pq.add(gs.get(j));
			}
				
		}
			
	}

	public class GeneTask implements Runnable{
		
		int lo;
		int hi;
		public GeneTask(int i, int j) {
			lo=i;
			hi=j;
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
			GENERATION += 1;

		do {
			bestOfGeneration = pq.peek();
//			System.out.println("Generation "+GENERATION+", Position: "
//				+bestOfGeneration.mt.getX()+","+bestOfGeneration.mt.getY()
//				+" Status:"+bestOfGeneration.getState() + " Score:"+bestOfGeneration.getScore()+" GetKey:"+bestOfGeneration.mt.getKey());
			
			if(bestOfGeneration!=null) {
				System.out.print("Thread: "+Thread.currentThread().getName());
				System.out.printf(" Generation %5d, Position: %2d,%2d Step: %2d Status:%3d Score:%7.2f FoundGate:%b GetKey:%b OpenGate:%b\n",
						GENERATION, bestOfGeneration.mt.getX(), bestOfGeneration.mt.getY(),bestOfGeneration.getStep(), bestOfGeneration.getState(), bestOfGeneration.getScore(), 
						bestOfGeneration.mt.foundGate(), bestOfGeneration.mt.getKey(), bestOfGeneration.mt.openGate());
				doNextGen();	
			}else
				break;
			
			
		}while(bestOfGeneration.getState()!=3);
	}
}

	public static void main(String[] args) { 
//		Demo d = new Demo();
//		EvenGeneTask even = d.new EvenGeneTask();
//		OddGeneTask odd = d.new OddGeneTask();
//		// Do generation 0;
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
		//task1.name="ThreadA....";
		GeneTask task2 =  d.new GeneTask(0,100);
		//task2.name="ThreadB....";
		e.execute(task1);
		e.execute(task2);
		e.shutdown();
	}
	
	public static void doNextGen() {
		ArrayList<Generic> parents = new ArrayList<>();
		//ArrayList<Generic> children = new ArrayList<>();
		Generic first = pq.peek();
		for(int i=0;i<5;i++) {
			try {
				parents.add(pq.poll());
			}catch(Exception e) {
				break;
			}
			
		}
		
		//pq.clear();
		pq.add(first);
//		System.out.println(first.getScore());
		for(Generic f:parents) { 
			for(Generic m:parents) {
				 for(int j=0;j<4;j++) {
					 try {
						 Generic generic = new Generic(f, m);
							generic.Mutate();
							//children.add(generic);
							generic.Move();
							pq.add(generic);
					 }catch(Exception e) {
							break;
						}
					
				 }
			}
		}
//		even.gs=children;
//		odd.gs=children;
//		ExecutorService e = Executors.newFixedThreadPool(2);
//		e.execute(even);
//		e.execute(odd);
//		e.shutdown();
//		parents.clear();
		// System.out.println(pq.size());
		GENERATION+=1;
		
	}
}
