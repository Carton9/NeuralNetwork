package neuralNetwork;
import java.awt.geom.*;
import java.util.*;
import java.awt.*;
public class Creation {
	public boolean isAlive=false;
	public Ellipse2D appear;
	NeuralNetwork brain;
	NeuralNetworkFactory reproduction;
	public double livePoint=0;
	Color c;
	public Creation(double size,Point location){
		reproduction=new NeuralNetworkFactory(3,10,10,4);
		brain=reproduction.init();
		isAlive=true;
		appear=new Ellipse2D.Double(location.getX()+size,location.getY()+size,size,size);
		Random random=new Random();
		c=new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
	}
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
		appear=new Ellipse2D.Double(location.getX()+size,location.getY()+size,size,size);
		this.c=new Color(Math.abs(c.getRed()+random.nextInt(1)-1),Math.abs(c.getGreen()+random.nextInt(1)-1),Math.abs(c.getBlue()+random.nextInt(1)-1));
	}
	public Color getColor(){
		return c;
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
			System.out.println(answer+"  "+targetValue.get(i));
			double loss=(output.get(targetValue.get(i))-output.get(answer))*10;
			lossList.add(loss);
			System.out.println(loss);
			brain.updataLoss(loss);
		}
		return lossList;
	}
	public void action(ArrayList<Creation> list,ArrayList<Plant> listP){
		Creation target=see(list);
		Plant targetP=seeP(listP);
		ArrayList<Double> inputdata=new ArrayList<Double>();
		inputdata.add(excitation(getDistence(target)));
		inputdata.add(excitation(appear.getCenterX()-target.appear.getCenterX()));
		inputdata.add(excitation(appear.getCenterY()-target.appear.getCenterY()));
		brain.action(inputdata);
		ArrayList<Double> output=brain.output();
		int place=-1;
		double maxValue=-2;
		for(int i=0;i<output.size();i++){
			if(maxValue<output.get(i)){
				place=i;
				maxValue=output.get(i);
			}
		}
		/*
		 switch(place){
			case 1:
			{
				move(0,-1,100);
				break;
			}
			case 2:
			{
				move(0,1,100);
				break;
			}
			case 3:
			{
				move(-1,0,100);
				break;
			}
			case 4:
			{
				move(1,0,100);
				break;
			}
		}
		 */
		
		
	}
	public Plant seeP(ArrayList<Plant> list){
		double disdent=Double.MAX_VALUE;
		Plant target=null;
		for(int i=0;i<list.size();i++){
			Plant newTarget=list.get(i);
			double disdentbuff=Math.sqrt(Math.pow(appear.getCenterX()-newTarget.appear.getCenterX(), 2)
										+Math.pow(appear.getCenterY()-newTarget.appear.getCenterY(), 2));
			//System.out.println(disdentbuff);
			//if(newTarget==this)continue;
			if(disdent>disdentbuff&&newTarget.isAlive){
				disdent=disdentbuff;
				target=newTarget;
			}
		}
		
		return target;
	}
	public Creation see(ArrayList<Creation> list){
		double disdent=Double.MAX_VALUE;
		Creation target=null;
		for(int i=0;i<list.size();i++){
			Creation newTarget=list.get(i);
			double disdentbuff=Math.sqrt(Math.pow(appear.getCenterX()-newTarget.appear.getCenterX(), 2)
										+Math.pow(appear.getCenterY()-newTarget.appear.getCenterY(), 2));
			//System.out.println(disdentbuff);
			if(newTarget==this)continue;
			if(disdent>disdentbuff&&newTarget.isAlive){
				disdent=disdentbuff;
				target=newTarget;
			}
		}
		
		return target;
	}
	public void eat(Creation newTarget){
		double disdentbuff=Math.pow(appear.getCenterX()-newTarget.appear.getCenterX(), 2)
				+Math.pow(appear.getCenterY()-newTarget.appear.getCenterY(), 2);
		if(appear.contains(new Point2D.Double(newTarget.appear.getCenterX(),newTarget.appear.getCenterY()))&&newTarget.isAlive){
			//System.out.println("AAA"+disdentbuff+" "+appear.contains(new Point2D.Double(newTarget.appear.getCenterX(),newTarget.appear.getCenterY())));
			newTarget.isAlive=false;
			livePoint+=newTarget.livePoint+10;
		}
	}
	public void eatP(Plant newTarget){
		
		double disdentbuff=Math.pow(appear.getCenterX()-newTarget.appear.getCenterX(), 2)
				+Math.pow(appear.getCenterY()-newTarget.appear.getCenterY(), 2);
		if(appear.contains(new Point2D.Double(newTarget.appear.getCenterX(),newTarget.appear.getCenterY()))&&newTarget.isAlive){
			//System.out.println("AAA"+disdentbuff+" "+appear.contains(new Point2D.Double(newTarget.appear.getCenterX(),newTarget.appear.getCenterY())));
			newTarget.isAlive=false;
			livePoint++;
		}
	}
	public void move(double x,double y,double speed){
		Ellipse2D newAppear=new Ellipse2D.Double((appear.getCenterX()+x)%1860,(appear.getCenterY()+y)%1040,appear.getHeight(),appear.getHeight());
		this.appear=newAppear;
	}
	public void die(){
		isAlive=false;
	}
	public Creation reproduction(){
		Random random=new Random();
		Creation child=new Creation(reproduction.reproduction(),20,new Point(random.nextInt(1800)+40,random.nextInt(1000)+40),this.getColor());
		return child;
	}
	public double excitation(double input){
		double buff=Math.pow(Math.E, input)+1;
		return 1/buff;
	}
	public double getDistence(Creation newTarget){
		double disdentbuff=Math.sqrt(Math.pow(appear.getCenterX()-newTarget.appear.getCenterX(), 2)
				+Math.pow(appear.getCenterY()-newTarget.appear.getCenterY(), 2));
		return disdentbuff;
	}
}
