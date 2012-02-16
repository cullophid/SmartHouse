import ai.impl.Ai;
import core.*;

	public class SmartHouse implements TimeoutListener {

	private static boolean debug = true;
	Connection conn = null;
	Statement stmt;
	Ai ai;
	EventList eventlist;
	Correlation correlation;
	Timer timer;
	List<Integer> timeout;
	int onTime;
	int punishmentTimeout;
	Map<Integer, Boolean> switchStatus;
	Map<Integer, Integer> firstSensorAfterTimeout;
	DecisionMatrix decisionMatrix; 
	public static void main(String[] args){
		SmartHouse sh = new SmartHouse(); 
	}

	/*
	 * Constructor for the class SmartHouse
	 * Handles the input and output for the ai
	 */
	public SmartHouse(){
		Config.loadConfig();
		try {
			debug = Config.debug;
			Class.forName("com.mysql.jdbc.Driver");//load the mysql driver
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kiiib?user=KIIIB&password=42");//connect to the database
			stmt = conn.createStatement();
			decisionMatrix = new DecisionMatrix();
			correlation = new Correlation();
			
			eventlist = new EventList();
			timer = new Timer();
			timeout = new ArrayList<Integer>(10);
			onTime = Config.defaultOnTime;
			punishmentTimeout = Config.punishmentTimeout;
			firstSensorAfterTimeout = new HashMap<Integer, Integer>();
			switchStatus = new HashMap<Integer, Boolean>();
		}
		catch (SQLException se){
			System.out.println("SQLException: " + se.getMessage());
			System.out.println("SQLState: " + se.getSQLState());
			System.out.println("VendorError: " + se.getErrorCode());

		}
		catch (Exception e){
			 e.printStackTrace();
	   }
	}

	public SmartHouse(Ai ai) {
		this();
		this.ai = ai;
	}

	/*
	 * Method called when a sensorevent occurs in the simulator
	 * @author Andreas møller & David Emil Lemvigh
	 */
	public void sensorEvent(int sensorId){
		try{
			System.out.println("Sensor "+sensorId+" fired!"); 
			eventlist.sensorEvent(sensorId);
			if (!debug)
				stmt.executeUpdate("INSERT INTO sensor_events VALUES("+sensorId+",NOW())");
			
			for (int sw : timeout) {
				if (!firstSensorAfterTimeout.containsKey(sw))
					firstSensorAfterTimeout.put(sw, sensorId);
			}
			for (int sw : correlation.getSwitches(sensorId, 0.5f)) {
				if (isOn(sw) && !timeout.contains(sw)) {
					float t = onTime * correlation.getCorrelation(sw, sensorId);
					System.out.printf("keep switch %d on (%d ms)\n", sw, (long) t);
					timer.updateTimeout(sw, (long) t, this);
				}
			}
			matrixLookUp();
		}
		catch(SQLException se){
			System.out.println("SQLException: " + se.getMessage());
			System.out.println("SQLState: " + se.getSQLState());
			System.out.println("VendorError: " + se.getErrorCode());
		}
	}
	/*
	 * Method called when a switch event occurs in the simulator
	 * @author Andreas Møller & David Emil Lemvigh
	 */
	public void switchEvent(int switchId, int status){
		try{
			System.out.println("Switch "+switchId+" turned "+status);
	//            System.out.println(eventlist);
			boolean cmd = (status == 1) ? true : false;
			
			if (cmd) {
				if (timeout.contains(switchId)) {
					timeout.remove((Object) switchId);
					timer.stop(switchId);
					if (firstSensorAfterTimeout.containsKey(switchId))
						correlation.increaseCorrelation(switchId, firstSensorAfterTimeout.get(switchId));
				}
				on(switchId);
				timer.setTimeout(switchId, onTime, this);
				
			} else {
				off(switchId);
			}
			if (!debug)
				stmt.executeUpdate("INSERT INTO switch_events VALUES("+switchId+","+status+",NOW())");
		}
		catch(SQLException se){
			System.out.println("SQLException: " + se.getMessage());
			System.out.println("SQLState: " + se.getSQLState());
			System.out.println("VendorError: " + se.getErrorCode());
		}
	}

	private Map<Integer, Boolean> testMap = new HashMap<Integer, Boolean>();

	   public void TimeoutEventOccurred(TimeoutEvent event) {
		System.out.println("I should probably turn off the light now");
		int id = (Integer) event.getSource(); 
		if (timeout.contains(id) && eventlist.getLastEvent() != null) {
			correlation.reduceCorrelation(id, eventlist.getLastEvent().getID());
			timeout.remove(event.getSource());
		} else {
			off(id);
			timeout.add(id);
			timer.setTimeout(id, punishmentTimeout, this);
		}
	}
	private void matrixLookUp(){
		KeyList keylist;
		int P;
		float value = 0;
		for (int sw : decisionMatrix.switches){
			keylist = new KeyList(eventlist);
			keylist.add(sw);
			if(switchStatus.get(sw)){
				if(decisionMatrix.off.containsKey(keylist)){
					value = decisionMatrix.off.get(keylist);

				}
				if(Config.useZones){
					if(decisionMatrix.zoneOff.containsKey(keylist)){
						value = Math.max(value,decisionMatrix.zoneOff.get(keylist));
					}
		
				} 
				if(value>Config.probabilityThreshold){
					off(sw);
				}
	
			}
			else{
				if(decisionMatrix.on.containsKey(keylist)){

					value = decisionMatrix.on.get(keylist);
				}
				
				if(Config.useZones){
					if(decisionMatrix.zoneOn.containsKey(keylist)){
						value = Math.max(value,decisionMatrix.zoneOn.get(keylist));
					}
				}
				if(value>Config.probabilityThreshold){
					on(sw);
				}
	
			}
		}
	}

	private void on(int id) {
		//ai.on(id);
		switchStatus.put(id, true);
	}

	private void off(int id) {
		//ai.off(id);
		switchStatus.put(id, false);
	}

	private boolean isOn(int id) {
		if (switchStatus.containsKey(id))
			return switchStatus.get(id);
		
		return false;
	}
}
