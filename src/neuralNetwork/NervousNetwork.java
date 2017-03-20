package neuralNetwork;
import java.util.ArrayList;

public class NervousNetwork {
	boolean isAlive;
	int inputN=0,hideN=0,outputN=0;
	ArrayList<Neuron> inputLevel;
	ArrayList<ArrayList<Neuron>> hideLevel;
	ArrayList<Neuron> outputLevel;
	ArrayList<Synapse> inputSignal;
	ArrayList<Synapse> outputSignal;
	public NervousNetwork(){
		inputLevel=new ArrayList<Neuron>();
		hideLevel=new ArrayList<ArrayList<Neuron>>();
		outputLevel=new ArrayList<Neuron>();
		isAlive=false;
		inputN=0;hideN=0;outputN=0;
	}
	public void addInputLevel(ArrayList<Neuron> inputLevel){
		this.inputLevel=inputLevel;
		inputN+=inputLevel.size();
	}
	public void addOutputLevel(ArrayList<Neuron> outputLevel){
		this.outputLevel=outputLevel;
		outputN+=outputLevel.size();
		isAlive=true;
	}
	public void addAllHideLevel(ArrayList<ArrayList<Neuron>> hideLevel){
		this.hideLevel=hideLevel;
		hideN=hideLevel.size()*hideLevel.get(0).size();
	}
	public void addHideLevel(ArrayList<Neuron> hideLevel){
		this.hideLevel.add(hideLevel);
		hideN+=hideLevel.size();
	}
	public boolean action(ArrayList<Double> inputdata){
		if(!isAlive||inputdata.size()!=inputSignal.size())return false;
		for(int i=0;i<inputdata.size();i++){
			inputSignal.get(i).input(inputdata.get(i));
		}
		for(int i=0;i<inputLevel.size();i++)inputLevel.get(i).action();
		for(int i=0;i<hideLevel.size();i++){
			ArrayList<Neuron> oneHideLevel=hideLevel.get(i);
			for(int j=0;j<oneHideLevel.size();j++)oneHideLevel.get(i).action();
		}
		for(int i=0;i<outputLevel.size();i++)outputLevel.get(i).action();
		return true;
	}
	public ArrayList<Double> output(){
		ArrayList<Double> output=new ArrayList<Double>();
		for(int i=0;i<outputSignal.size();i++){
			output.add(excitation(outputSignal.get(i).outputValue()));
		}
		return output;
	}
	
	public double excitation(double input){
		double buff=Math.pow(Math.E, (input*-1))+1;
		return 1/buff;
	}
	
	public void addInputSignal(ArrayList<Synapse> list){
		inputSignal=list;
	}
	public void addOutputSignal(ArrayList<Synapse> list){
		outputSignal=list;
	}
	public void updataLoss(double loss){
		//ArrayList<ArrayList<Neuron>> hideLevel;
		for(int i=0;i<hideLevel.size();i++){
			ArrayList<Neuron> subList=hideLevel.get(i);
			for(int j=0;j<subList.size();j++){
				subList.get(j).feedback(loss);
			}
		}
	}
}
