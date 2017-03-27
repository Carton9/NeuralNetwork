import java.util.ArrayList;
import java.util.Random;
import neuralNetwork.*;
public class MainTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Creation test1=new Creation(2,2,2,2);
		//AdvFrame frame=new AdvFrame(1000,2000,"test2");
		//frame.setVisible(true);
		//LinearGraph panel=new LinearGraph();
		//frame.paneFrence.add(panel);
		//panel.setGraph();
		Random generater=new Random();
		//panel.setGraph();
		while(true){
			ArrayList<ArrayList<Double>> inputdata=new ArrayList<ArrayList<Double>>();//  
			ArrayList<Integer>targetValue=new ArrayList<Integer>();//  0/1
			for(int i=0;i<100;i++){
				ArrayList<Double> example=new ArrayList<Double>();
				double left=(double)generater.nextInt(100)/10;
				double right=10-left;
				//System.out.println(left);
				example.add(left);
				example.add(right);
				int answer=(left>right)?0:1;
				//System.out.println(answer);
				inputdata.add(example);
				targetValue.add(answer);
			}
			test1.tarining(inputdata, targetValue);
			//Thread.sleep(100);	
		}
	}

}
