package neuralNetwork;
import java.util.ArrayList;
import java.util.Random;

public class NeuralNetworkFactory {
	int inputN,hideN,hideLevalN,outputN;
	int mutationMax=1;
	int mutationRate;//out of 100
	ArrayList<ArrayList<Double>> inputWeight;
	ArrayList<ArrayList<ArrayList<Double>>> hideWeight;
	Random mutationSource=new Random();
	public NeuralNetworkFactory(int inputN,int hideN,int hideLevalN,int outputN){
		this.inputN=inputN;
		this.hideN=hideN;
		this.hideLevalN=hideLevalN;
		this.outputN=outputN;
		inputWeight=new ArrayList<ArrayList<Double>>();
		hideWeight=new ArrayList<ArrayList<ArrayList<Double>>>();
		mutationRate=2;
		
		ArrayList<Double> oneWeight=new ArrayList<Double>();
		for(int j=0;j<hideN;j++){
			for(int i=0;i<inputN;i++){
				oneWeight.add(mutation(0));
			}
			inputWeight.add(oneWeight);
			oneWeight=new ArrayList<Double>();
		}
		
		ArrayList<ArrayList<Double>> Weight=new  ArrayList<ArrayList<Double>>();
		oneWeight=new ArrayList<Double>();
		for(int l=0;l<hideLevalN-1;l++){
			for(int j=0;j<hideN;j++){
				for(int i=0;i<hideN;i++){
					oneWeight.add(mutation(0));
				}
				Weight.add(oneWeight);
				oneWeight=new ArrayList<Double>();
			}
			hideWeight.add(Weight);
			Weight=new ArrayList<ArrayList<Double>>();
		}
	}
	public NeuralNetworkFactory(int inputN,int hideN,int hideLevalN,int outputN,int mutationRate,
								ArrayList<ArrayList<Double>> iW,
								ArrayList<ArrayList<ArrayList<Double>>> hW)
	{
		this.inputN=inputN;
		this.hideN=hideN;
		this.hideLevalN=hideLevalN;
		this.outputN=outputN;
		inputWeight=new ArrayList<ArrayList<Double>>();
		hideWeight=new ArrayList<ArrayList<ArrayList<Double>>>();
		this.mutationRate=mutationRate;
		
		ArrayList<Double> oneWeight=new ArrayList<Double>();
		for(int j=0;j<hideN;j++){
			for(int i=0;i<inputN;i++){
				oneWeight.add(mutation(iW.get(j).get(i)));
			}
			inputWeight.add(oneWeight);
			oneWeight=new ArrayList<Double>();
		}
		
		ArrayList<ArrayList<Double>> Weight=new  ArrayList<ArrayList<Double>>();
		oneWeight=new ArrayList<Double>();
		for(int l=0;l<hideLevalN-1;l++){
			for(int j=0;j<hideN;j++){
				for(int i=0;i<hideN;i++){
					oneWeight.add(mutation(hW.get(l).get(j).get(i)));
				}
				Weight.add(oneWeight);
				oneWeight=new ArrayList<Double>();
			}
			hideWeight.add(Weight);
			Weight=new ArrayList<ArrayList<Double>>();
		}
	}

	public NeuralNetwork init(){
		NeuralNetwork output=new NeuralNetwork();
		/***********input***********/
		ArrayList<Synapse> inputSignal = new ArrayList<Synapse>();
		ArrayList<Neuron> inputLevel = new ArrayList<Neuron>();
		ArrayList<Synapse> inputConnect = new ArrayList<Synapse>();
		for(int i=0;i<inputN;i++){
			Synapse in=new Synapse();
			inputSignal.add(in);
			Neuron inCell=new Neuron();
			inCell.takeConnect(in, 1.0);
			inputLevel.add(inCell);
			Synapse out=inCell.makeConnect();
			inputConnect.add(out);
		}
		output.addInputLevel(inputLevel);
		output.addInputSignal(inputSignal);
		/************input hide**********/
		ArrayList<Neuron> inputHideLevel = new ArrayList<Neuron>();
		ArrayList<Synapse> connectS = new ArrayList<Synapse>();
		for(int j=0;j<hideN;j++){
			Neuron hideCell=new Neuron();
			hideCell.addInput(inputConnect, inputWeight.get(j));
			Synapse out=hideCell.makeConnect();
			inputHideLevel.add(hideCell);
			connectS.add(out);
		}
		output.addHideLevel(inputHideLevel);
		/************normal hide**********/
		ArrayList<Synapse> connectSBuff = new ArrayList<Synapse>();
		ArrayList<Neuron> hideLevel = new ArrayList<Neuron>();
		for(int l=0;l<hideLevalN-1;l++){
			for(int j=0;j<hideN;j++){
				Neuron hideCell=new Neuron();
				hideCell.addInput(connectS, hideWeight.get(l).get(j));
				Synapse out=hideCell.makeConnect();
				hideLevel.add(hideCell);
				connectSBuff.add(out);
			}
			output.addHideLevel(hideLevel);
			connectS=connectSBuff;
			connectSBuff= new ArrayList<Synapse>();
			hideLevel = new ArrayList<Neuron>();
		}
	
		/************output**********/
		ArrayList<Synapse> outputSignal = new ArrayList<Synapse>();
		ArrayList<Neuron> outputLevel = new ArrayList<Neuron>();
		for(int i=0;i<outputN;i++){
			Neuron outputCell=new Neuron();
			outputCell.addInput(connectS, hideWeight.get(hideWeight.size()-1).get(i));
			Synapse out=outputCell.makeConnect();
			outputLevel.add(outputCell);
			outputSignal.add(out);
		}
		output.addOutputLevel(outputLevel);
		output.addOutputSignal(outputSignal);
		/**********************/
		return output;
	}
	
	public double mutation(double parent){
		double newWeight=parent+(double)(mutationSource.nextInt(mutationRate*2)-mutationRate)/10;
		if(newWeight>mutationMax)return mutationMax;
		else if(newWeight<-mutationMax)return -mutationMax;
		return newWeight;
	}
	public NeuralNetworkFactory reproduction(){
		NeuralNetworkFactory child=new NeuralNetworkFactory(inputN,hideN,hideLevalN,outputN,
															mutationRate,inputWeight,hideWeight);
		return child;
	}
}
