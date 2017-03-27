package neuralNetwork;
import java.util.ArrayList;
import java.util.Random;

public class Neuron {
	ArrayList<Synapse> inputSignal;
	ArrayList<Double> inputWeight;
	ArrayList<Synapse> outputSignal;
	boolean active;
	int bias;
	public Neuron(){
		inputSignal=new ArrayList<Synapse>();
		outputSignal=new ArrayList<Synapse>();
		inputWeight=new ArrayList<Double>();
		active=false;
		Random biasGenerator=new Random();
		int buff=0;
		while(buff==0){
			buff=biasGenerator.nextInt(6)-3;
		}
		bias=buff;
	}
	public Neuron(ArrayList<Synapse> input){
		active=false;
		inputSignal=input;
		outputSignal=new ArrayList<Synapse>();
		Random biasGenerator=new Random();
		int buff=0;
		while(buff==0){
			buff=biasGenerator.nextInt(4)-2;
		}
		bias=buff;
	}
	public Synapse makeConnect(){
		Synapse newSynapse=new Synapse();
		outputSignal.add(newSynapse);
		return newSynapse;
	}
	public boolean takeConnect(Synapse newSynapse,double weight){
		if(newSynapse==null)return false;
		inputSignal.add(newSynapse);
		inputWeight.add(new Double(weight));
		return true;
	}
	public void addInput(ArrayList<Synapse> inputSignal,ArrayList<Double> inputWeight){
		this.inputSignal=inputSignal;
		this.inputWeight=inputWeight;
	}
	public void action(){
		double buff=0.0;
		for(int i=0;i<inputSignal.size();i++){
			Synapse signal=inputSignal.get(i);
			buff+=signal.outputValue()*inputWeight.get(i);
		}
		buff+=bias;
		buff = excitation(buff);
		if(buff>0.5)active=true;
		for(int i=0;i<outputSignal.size();i++){
			outputSignal.get(i).input(buff);
		}
	}
	public double excitation(double input){
		double buff=Math.pow(Math.E, (input*-1))+1;
		return 1/buff;
	}
	public void feedback(double loss){
		if(active){
			ArrayList<Double> newInputWeight=new ArrayList<Double>();
			for(int i=0;i<inputWeight.size();i++){
				newInputWeight.add(inputWeight.get(i)-loss);
			}
			inputWeight=newInputWeight;
		}
		else{
			ArrayList<Double> newInputWeight=new ArrayList<Double>();
			for(int i=0;i<inputWeight.size();i++){
				newInputWeight.add(inputWeight.get(i)+loss);
			}
			inputWeight=newInputWeight;
		}
		active=false;
	}
}
