import bluetooth
from bluetooth.btcommon import BluetoothError

MSG_SIZE = 16   # Use a predefined message length

class NXTComm:
    """
    Handles Bluetooth communications with a NXT.
    """
    def __init__(self, nxt_bd_addr, port=1):
        self.nxt_bt_addr = nxt_bd_addr
        self.port = port
        
    def connect(self):
        """
        Connects to NXT. If something went wrong, return error message; otherwise
        return nothing.
        """
        try:
            self.s = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
            self.s.connect((self.nxt_bt_addr, self.port))
        except BluetoothError, ex:
            return str(ex)
        
    def send(self, msg):
        """
        Sends a message to NXT padding it with spaces until it's MSG_SIZE long.
        If message is longer, use first MSG_SIZE letters only.
        """
        msg = str(msg)
        if len(msg) > MSG_SIZE:
            msg = msg[:MSG_SIZE]
        else:
            msg = msg.ljust(MSG_SIZE)	# Pad message with spaces

        try:
            self.s.send(msg)
        except BluetoothError, ex:
            return str(ex)
    
    def receive(self):
        """
        Wait for a message to be sent by NXT. Returns an error code if something
        went wrong, otherwise returns the received data.
        """
        try:
            msg = self.s.recv(MSG_SIZE)
        except BluetoothError, ex:
            return str(ex)

        return msg
    
    def close(self):
        """
        Closes the connection to NXT. Communication is impossible until reconnected.
        """
        self.s.close()
