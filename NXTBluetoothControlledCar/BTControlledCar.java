
import lejos.nxt.*;

/**
 * Bluetooth device controlled car main class.
 * This class contains the main loop that handles receiving
 * data from a Bluetooth device and commandin the car to move.
 */
public class BTControlledCar {

    public static void main(String[] args) {

        PCComm pcConn = new PCComm();
        Car car = new Car();
        car.start();

        pcConn.send("Welcome to NXT!");     // Send an init message to controlling device

        while (true) {
            
            String command = pcConn.receive();

            if (command.equals("QUIT")) {
                break;
            }

            // Parse position from command. If parsing fails, read next command
            int[] pos = new int[3];
            if (!parsePos(command, pos)) {
                continue;
            }

            car.readings(pos[0], pos[1], pos[2]);

            if (Button.ENTER.isPressed()) {
                break;
            }
        }

        pcConn.closeConnection();
        LCD.clear();
        System.exit(0);
    }

    /**
     * Parses device orientation from a Bluetooth message.
     * The message is assumed to be of form "100;-1220;320". Otherwise parsing fails.
     * @param command Bluetooth message where coords are parsed.
     * @param coords Table where the coords are stored.
     * @return  True if parsing was succesfull, false otherwise.
     */
    private static boolean parsePos(String command, int[] coords) {
        // Returns false if parsing fails
        
        String[] coordsStr = Tools.split(command, ';');

        if (coordsStr.length != 3) {
            return false;
        }

        for (int i = 0; i < 3; i++) {
            try {
                coords[i] = Integer.parseInt(coordsStr[i]);
            } catch (NumberFormatException numberFormatException) {
                return false;
            }
        }

        return true;
    }
}
