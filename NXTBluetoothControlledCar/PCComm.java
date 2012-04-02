import lejos.nxt.comm.*;

/**
 * Handles Bluetooth communications with a controlling device. Bluetooth messages
 * with the controlling device are always <code>MSG_SIZE</code> long. Longer or
 * shorter messages are cut or padded with spaces. Received messages are always
 * trimmed of extra whitespaces.
 */
public class PCComm {

    private NXTConnection conn;
    /**
     * The size of messages sent over Bluetooth in bytes.
     */
    public static final int MSG_SIZE = 16;

    /**
     * Construct the object and wait for Bluetooth connection.
     * Returns only after connection has been established.
     */
    public PCComm() {
        System.out.println("Waiting...");
        this.conn = Bluetooth.waitForConnection(0, NXTConnection.RAW);
        System.out.println("Connected!");
    }

    /**
     * Sends <code>msg</code> over bluetooth to the connected device. String is
     * turned to bytes using us-ascii encoding. Behavior with non-ascii
     * characters is undefined.
     * @param msg The message to be sent. If message is longer than <code>MSG_SIZE
     * </code>, only first <code>MSG_SIZE</code> letters are sent.
     */
    public void send(String msg) {
        if (msg.length() > MSG_SIZE) {
            msg = msg.substring(0, MSG_SIZE);
        } else {
            while (msg.length() < MSG_SIZE) {
                msg += " ";
            }
        }

        byte[] b;

         /* Encoding parameter of getBytes method is ignored by LeJos, us-ascii
          * always used, but something must be passed.
          * In Lejos, getBytes doesn't throw a checked exception.
          */
        b = msg.getBytes("US-ASCII");
        conn.write(b, b.length);
    }

    /**
     * Waits for a message to be sent from controlling device.
     * @return Message received.
     */
    public String receive() {
        byte[] b = new byte[MSG_SIZE];
        conn.read(b, b.length);
        String s = new String(b);
        s = Tools.trim(s);
        return s;
    }

    /**
     * Closes the connection. Sending and receiving with this object isn't
     * possible after this.
     */
    public void closeConnection() {
        System.out.println("Closing...");
        conn.close();
    }
}