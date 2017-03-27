package neuralNetwork;
import java.awt.geom.*;
import java.util.*;
import java.awt.*;
public class Creation {
	public boolean isAlive=false;
	NeuralNetwork brain;
	NeuralNetworkFactory reproduction;
	public Creation(int input,int hiddenX,int hiddenY,int output){
		reproduction=new NeuralNetworkFactory(input,hiddenX,hiddenY,output);
		brain=reproduction.init();
		isAlive=true;
	}
	public Creation(NeuralNetworkFactory reproduction,double size,Point location,Color c){
		brain=reproduction.init();
		Random random=new Random();
		this.reproduction=reproduction;
		isAlive=true;
	}
	public ArrayList<Double> tarining(ArrayList<ArrayList<Double>> inputdata,ArrayList<Integer>targetValue){
		 ArrayList<Double> lossList=new ArrayList<Double>();
		if(inputdata.size()!=targetValue.size())return null;
		for(int i=0;i<inputdata.size();i++){
			brain.action(inputdata.get(0));
			inputdata.remove(0);
			ArrayList<Double> output=brain.output();
			//System.out.println(output);
			int answer=(targetValue.get(i)==1)?0:1;
			//System.out.println(answer+"  "+targetValue.get(i));
			double loss=(output.get(targetValue.get(i))-output.get(answer))*100;
			lossList.add(loss);
			System.out.println(loss);
			brain.updataLoss(loss);
		}
		return lossList;
	}
	public Creation reproduction(){
		Random random=new Random();
		Creation child=new Creation(reproduction.reproduction(),20,new Point(random.nextInt(1800)+40,random.nextInt(1000)+40), null);
		return child;
	}
	public double excitation(double input){
		double buff=Math.pow(Math.E, input)+1;
		return 1/buff;
	}
}
