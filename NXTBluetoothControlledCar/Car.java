
import lejos.nxt.*;

/**
 * Handles setting motors speed and calculating them from accelerometer readings.
 *
 */
public class Car extends Thread {
    private Motor leftMotor;
    private Motor rightMotor;
    private int leftSpeed;
    private int rightSpeed;

    private static final int SPEED_TRESHOLD = 100;
    private static final int TURN_TRESHOLD = 50;
    private static final int MAX_SPEED = 1000;
    private static final int MAX_TURN = 2000;
    
    /**
     * Calculates speed and turning from accelerometer readings.
     * This class has been made for the readings from Nokia N900.
     * @param x Devices orientation on the x-axle. Turning is calculated from this.
     * @param y Devices orientation on the y-axle. Speed is calculated from this.
     * @param z Devices orientation on the z-axle. Unused.
     */
    public void readings(int x, int y, int z) {
        // y defines the speed of the car
        y *= 1.5;

        if (y > MAX_SPEED) y = MAX_SPEED;
        else if (y < -MAX_SPEED) y = -MAX_SPEED;

        if (y > -SPEED_TRESHOLD && y < SPEED_TRESHOLD) y = 0;

        // x defines the turning of the car
        x *= 3;

        if (x > MAX_TURN) x = MAX_TURN;
        else if (x < -MAX_TURN) x = -MAX_TURN;

        if (x > -TURN_TRESHOLD && x < TURN_TRESHOLD) x = 0;

        // Set the speed of motors based on x and y
        if (y > 0) {                // Move forward
            if (x > 0) {            // Turn left
                leftSpeed = y - x;
                rightSpeed = y;
            } else if (x < 0) {     // Turn right
                leftSpeed = y;
                rightSpeed = y + x;
            } else {                // x = 0, don't turn
                leftSpeed = y;
                rightSpeed = y;
            }
        } else if (y < 0) {         // Move backward
            if (x > 0) {            // Turn left
                leftSpeed = y + x;
                rightSpeed = y;
            } else if (x < 0) {     // Turn right
                leftSpeed = y;
                rightSpeed = y - x;
            } else {                // x = 0, don't turn
                leftSpeed = y;
                rightSpeed = y;
            }
        } else {                    // y = 0, speed is zero
            leftSpeed = 0;
            rightSpeed = 0;
        }
    }

    /**
     * Sets motors speeds to ones calculated by <code>readings()</code>.
     */
    @Override
    public void run() {
        leftMotor = Motor.B;
        rightMotor = Motor.A;
        leftSpeed = 0;
        rightSpeed = 0;

        while (true) {
            leftMotor.setSpeed(leftSpeed);
            if (leftSpeed > 0) leftMotor.forward();
            else if (leftSpeed < 0) leftMotor.backward();
            else leftMotor.flt();   // leftSpeed = 0

            rightMotor.setSpeed(rightSpeed);
            if (rightSpeed > 0) rightMotor.forward();
            else if (rightSpeed < 0) rightMotor.backward();
            else rightMotor.flt();  // rightSpeed = 0
        }
    }
}
