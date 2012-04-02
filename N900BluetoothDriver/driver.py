import threading
import nxt_comm
import time
import accel

NXT_BT_ADDR = "00:16:53:08:D0:95"   # Hardcoded BT address of my NXT
WAIT_TIME = 0.025   # Time to wait until another reading is read and sent

class Driver ( threading.Thread ):
    """
    Sends accelerometer data to a connected NXT to drive it
    """

    def __init__(self):
        self.acc_sensors = accel.AccelRead()
        self.nxt = None
        self.connected = False
        threading.Thread.__init__(self)
        self.x = self.y = self.z = 0

    def run (self):
        """
        Main thread where reading acc data and sending it over BT happens.
        """
        while not self.connected:
            time.sleep(0.1)

        while self.connected:
            self.x, self.y, self.z = self.acc_sensors.getPos()
            self.nxt.send("%d;%d;%d" % (self.x, self.y, self.z))
            time.sleep(WAIT_TIME)

    def close(self):
        """
        Closes connection to NXT.
        """
        self.connected = False

        if self.nxt:
            self.nxt.send("QUIT");
            self.nxt.close()

    def connect(self):
        """
        Connects to a waiting NXT and receive init message.
        """
        self.nxt = nxt_comm.NXTComm(NXT_BT_ADDR)

        connectError = self.nxt.connect()
        if connectError:
            return connectError

        self.nxt.receive()

        self.connected = True
        return "Connected!"

    def calibrate(self):
        """
        Calibrate acc_sensors
        """
        self.acc_sensors.calibrate()
        return "Calibrated!"