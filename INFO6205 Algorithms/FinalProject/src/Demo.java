import java.util.ArrayList;
import java.util.PriorityQueue;

import javax.xml.ws.soap.MTOM;

public class Demo {
	public static int GENERATION = 0;
	public static final int MAXGENERATION = 20000;
	public static final int genericInGroup = 100;
	private static Generic bestOfGeneration;
	static PriorityQueue<Generic> pq = new PriorityQueue<>();
	
	public static void main(String[] args) { 
		// Do generation 0;
		for(int i=0;i<genericInGroup;i++) {
			Generic generic = new Generic();
			Gene[] genes = new Gene[100];
			// System.out.println("No."+i+" status:");
			for(int j=0;j<generic.getGenearr().length;j++) {
				genes[j] = new Gene();
			}
			generic.setGenearr(genes);
			generic.Mutate();
			generic.Move();
			pq.add(generic);
			// System.out.println("No."+i+" Score:"+generic.getScore());
			// System.out.println("Done");
			// Log function here
		}
		GENERATION += 1;
		
		do {
			bestOfGeneration = pq.peek();
//			System.out.println("Generation "+GENERATION+", Position: "
//				+bestOfGeneration.mt.getX()+","+bestOfGeneration.mt.getY()
//				+" Status:"+bestOfGeneration.getState() + " Score:"+bestOfGeneration.getScore()+" GetKey:"+bestOfGeneration.mt.getKey());
			System.out.printf("Generation %5d, Position: %2d,%2d Status:%3d Score:%7.2f FoundGate:%b GetKey:%b OpenGate:%b\n",
				GENERATION, bestOfGeneration.mt.getX(), bestOfGeneration.mt.getY(), bestOfGeneration.getState(), bestOfGeneration.getScore(), 
				bestOfGeneration.mt.foundGate(), bestOfGeneration.mt.getKey(), bestOfGeneration.mt.openGate());
			doNextGen();
		}while(bestOfGeneration.getState()!=3||GENERATION<MAXGENERATION);
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
		GENERATION += 1;
	}
}
