package smarthouse;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timer.TimeoutEvent;
import timer.TimeoutListener;
import timer.Timer;

import events.*;
import config.Config;
import core.*;

public class SmartHouse implements TimeoutListener {

	private static boolean debug = true;
	Connection conn = null;
	Statement stmt;
	AI ai;
	EventList eventlist,zoneeventlist;
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
			zoneeventlist = new EventList(true);
			timer = new Timer();
			timeout = new ArrayList<Integer>(10);
			onTime = Config.defaultOnTime;
			punishmentTimeout = Config.punishmentTimeout;
			firstSensorAfterTimeout = new HashMap<Integer, Integer>();
			switchStatus = new HashMap<Integer, Boolean>();
			for (int sw : decisionMatrix.switches){
				switchStatus.put(sw,false);
			}
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

	public SmartHouse(AI ai) {
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
			zoneeventlist.sensorEvent(sensorId);

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
		}
		catch(SQLException se){
			System.out.println("SQLException: " + se.getMessage());
			System.out.println("SQLState: " + se.getSQLState());
			System.out.println("VendorError: " + se.getErrorCode());
		}
		matrixLookUp();
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
			correlation.reduceCorrelation(id, eventlist.getLastEvent().getID());// adjust for zoneeventlist
			timeout.remove(event.getSource());
		} else {
			off(id);
			timeout.add(id);
			timer.setTimeout(id, punishmentTimeout, this);
		}
	}
	private void matrixLookUp(){
		try{
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
					System.out.println("probability value : "+value);
					if(value>Config.probabilityThreshold){
						off(sw);
					}
					if(Config.useZones){
						if(decisionMatrix.off.containsKey(keylist)){
							keylist = new KeyList(zoneeventlist);
							keylist.add(sw);
							value = decisionMatrix.off.get(keylist);
						}
 
					}
		
				}
				else{
					if(decisionMatrix.on.containsKey(keylist)){

						value = decisionMatrix.on.get(keylist);
					}
					if(Config.useZones){
						if(decisionMatrix.on.containsKey(keylist)){
							keylist = new KeyList(zoneeventlist);
							keylist.add(sw);
							value = decisionMatrix.on.get(keylist);
						}
 
					}
					System.out.println("probability value for switch  "+sw+" : "+value);
					if(value>Config.probabilityThreshold){
						on(sw);
					}
		
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private void on(int id) {
		System.out.println("Turning switch "+id+" on");
		ai.on(id);
		switchStatus.put(id, true);
	}

	private void off(int id) {
		System.out.println("Turning switch "+id+" off");
		ai.off(id);
		switchStatus.put(id, false);
	}

	private boolean isOn(int id) {
		if (switchStatus.containsKey(id))
			return switchStatus.get(id);
		
		return false;
	}
}
