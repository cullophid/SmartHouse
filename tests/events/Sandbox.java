package events;

import timer.Sleeper;

public class Sandbox {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        testInterruptedSleeper();
    }
    
    public static void testInterruptedSleeper() {
        Sleeper s = new Sleeper(42, 10);
        System.out.println("Alive? " + s.isAlive());
        System.out.println("Interrupted? " + s.isInterrupted());
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Alive? " + s.isAlive());
        System.out.println("Interrupted? " + s.isInterrupted());
    }

}
