package neuralNetwork;

public class Synapse {
	double value;
	public Synapse(){
		this.value=0.0;
	}
	public void input(double input){
		this.value=input;
	}
	public double outputValue(){
		return value;
	}
}
