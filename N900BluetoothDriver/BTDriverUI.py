import driver
import sys
from PySide.QtCore import *
from PySide.QtGui import *
from PySide.QtDeclarative import *

WINDOW_TITLE = "BT Driver"

class DriverInterface(QObject):
    """
    Interface class to communicate with the QML UI. Contains slots that UIs
    signals connect to.
    """

    @Slot(result=str)
    def connect_(self):
        """
        Called when Connect button pressed in UI. Returns message to be shown
        on UIs status area.
        """
        if btDriver.connected:
            return "Already connected!"
 
        return btDriver.connect()

    @Slot(result=str)
    def calibrate(self):
        """
        Called when calibration button pressed in UI. Returns message to be shown
        on UIs status area.
        """
        return btDriver.calibrate()

    @Slot(result=int)
    def getXCoord(self):
        """Returns device position on x-axle to UI."""
        return btDriver.x

    @Slot(result=int)
    def getYCoord(self):
        """Returns device position on y-axle to UI."""
        return btDriver.y

    @Slot(result=int)
    def getZCoord(self):
        """Returns device position on z-axle to UI."""
        return btDriver.z

app = QApplication(sys.argv)
btDriver = driver.Driver()
view = QDeclarativeView()
url = QUrl('BTDriverUI.qml')
view.setSource(url)
driverInterface = DriverInterface()

# Sets DriverInterface object to UIs context so that the UI sees the interface.
context = view.rootContext()
context.setContextProperty("driver", driverInterface)

view.setWindowTitle(WINDOW_TITLE)

view.show()
btDriver.start()
app.exec_()

btDriver.close()
sys.exit()
