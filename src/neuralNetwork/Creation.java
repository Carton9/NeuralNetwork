package neuralNetwork;
import java.awt.geom.*;
import java.util.*;
import java.awt.*;
public class Creation {
	public boolean isAlive=false;
	NeuralNetwork brain;
	NeuralNetworkFactory reproduction;
	public Creation(int input,int hideX,int hideY,int output){
		reproduction=new NeuralNetworkFactory(input,hideX,hideY,output);
		brain=reproduction.init();
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
			/*outputs[j] * (1 - outputs[j]) * (targets[j] - outputs[j])
			for (int j = 0; j < outputs.length; j++)   {
				outputError[j] =outputs[j] * (1 - outputs[j]) * (targets[j] - outputs[j]);
				}*/
			double loss=1-output.get(targetValue.get(i));
			lossList.add(loss);
			System.out.println(loss);
			brain.updataLoss(loss);
		}
		return lossList;
	}
	public double excitation(double input){
		double buff=Math.pow(Math.E, input)+1;
		return 1/buff;
	}
}
