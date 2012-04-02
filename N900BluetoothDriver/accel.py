class AccelRead():
    """
    Reads accelerometer data from a Nokia N900.
    """
    def __init__(self):
        """
        Set calibration values to zero on initialization.
        """
        self.cal_x = self.cal_y = self.cal_z = 0

    def getPos(self):
        """
        Reads accelerometer data, adjusts it with calibration values and returns it.
        If something went wrong, returns error code.
        """
        try:
            # This is a one line file containing device orientation on x, y and z axis
            f = open("/sys/class/i2c-adapter/i2c-3/3-001d/coord", 'r')
            x, y, z = [int(w) for w in f.readline().split()]
            f.close()
        except IOError, ex:
            print "Couldn't read accelerometer data!"
            print ex
            return (0, 0, 0)

        x = x - self.cal_x
        y = y -self.cal_y
        z = z - self.cal_z
        return x, y, z

    def calibrate(self):
        """
        Sets current position as the position when device is lying on a table.
        Theoretical values when device is on a table: x: 0, y: 0, z: -1000
        """
        self.cal_x = self.cal_y = self.cal_z = 0
        self.cal_x, self.cal_y, self.cal_z = self.getPos()
        self.cal_z += 1000