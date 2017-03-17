package neuralNetwork;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class Plant {
	public boolean isAlive=false;
	Ellipse2D appear;
	double livePoint=0;
	int age=0;
	public Plant(Point2D location){
		appear=new Ellipse2D.Double(location.getX(),location.getY(),5,5);
		isAlive=true;
	}
	public void die(){
		isAlive=false;
	}
	public ArrayList<Plant> action(){
		ArrayList<Plant> child=new ArrayList<Plant>();
		if(isAlive){
			livePoint++;
			age++;
			Random random=new Random();
			if(age>=200+(random.nextInt(10)-10)){
				for(int i=0;i<5;i++)child.add(new Plant(new Point2D.Double((appear.getCenterX()+(double)(random.nextInt(100)-100))%1860
																				, (appear.getCenterY()+(double)(random.nextInt(100)-100))%1040)));
				age=0;
			}
		}
		return child;
	}
	public Plant reproduction(){
		Random random=new Random();
		Random random2=new Random();
		Plant child=new Plant(new Point2D.Double(
								appear.getCenterX()+(double)(random.nextInt(500*2)-500)%1920,
								appear.getCenterY()+(double)(random2.nextInt(500*2)-500)%1080
								));
		
		return null;
	}
}
