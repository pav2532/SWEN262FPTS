import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

public class SimulationView extends JFrame implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private simulationController simControl;


	public SimulationView(Portfolio portfolio){
		this.simControl = new simulationController(portfolio);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}
