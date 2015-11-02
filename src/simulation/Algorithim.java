package simulation;

/**
 *
 * @author PedroVega
 */
import java.util.*;

import model.Holding;

public interface Algorithim {

	public Holding simulate(int percentage, int type, int intervals, Holding holding);
}
