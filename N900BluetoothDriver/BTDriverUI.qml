import QtQuick 1.0

Rectangle {
    id: baseRect
    width: 800
    height: 420
    color: "#000000"

    Item {
        Timer {     // Fetch new x, y and z data to display every 0,25 seconds
            interval:  250
            running: true
            repeat: true
            onTriggered: {
                xCoord.text = driver.getXCoord()
                yCoord.text = driver.getYCoord()
                zCoord.text = driver.getZCoord()
            }
        }
    }

    Text {      // Connect button
        id: connectText
        y: 180
        color: "#ffffff"
        text: "Connect to NXT"
        font.pointSize: 28
        style: Text.Sunken
        anchors.left: parent.left
        anchors.leftMargin: 90
        anchors.verticalCenter: parent.verticalCenter
        styleColor: "#000000"
        smooth: true

        Rectangle {
            id: connectRect
            x: 0
            y: -95
            width: 290
            height: 60
            radius: 6
            anchors.centerIn: parent
            border.width: 2
            gradient: Gradient {
                GradientStop {
                    position: 0
                    color: "#646464"
                }

                GradientStop {
                    position: 1
                    color: "#000000"
                }
            }
            z: -1   // Move behind text
            border.color: "#ffffff"

            MouseArea {
                id: connectBtn
                anchors.fill: parent
                onClicked: {
                    statusText.text = "Connecting..."
                    statusText.text = driver.connect_()
                }
            }
        }
    }

    Text {      // Calibrate button
        id: calibrateText
        x: 4
        y: 177
        color: "#ffffff"
        text: "Calibrate Sensors"
        font.pointSize: 28
        style: Text.Sunken
        anchors.right: parent.right
        anchors.rightMargin: 90
        smooth: true
        styleColor: "#000000"
        anchors.verticalCenter: parent.verticalCenter

        Rectangle {
            id: calibrateRect
            width: connectRect.width
            height: connectRect.height
            radius: 6
            anchors.centerIn: parent
            border.width: 2
            gradient: Gradient {
                GradientStop {
                    position: 0
                    color: "#646464"
                }

                GradientStop {
                    position: 1
                    color: "#000000"
                }
            }
            z: -1
            border.color: "#ffffff"

            MouseArea {
                id: calibrateBtn
                anchors.fill: parent
                onClicked: {
                    statusText.text = driver.calibrate()
                }
            }
        }
    }

    Text {      // Status text
        id: statusText
        x: 82
        y: 275
        width: parent.width
        height: 150
        color: "#ffffff"
        text: "Not connected"
        smooth: true
        horizontalAlignment: Text.AlignHCenter
        font.pointSize: 14
        anchors.bottom: parent.bottom
        anchors.bottomMargin: 0
        anchors.horizontalCenter: parent.horizontalCenter
    }

    Text {      // X coordinate text
        id: xLabel
        x: yLabel.x - 100   // 100 pixels left of yLabel
        y: 90

        width: 80
        height: 20
        color: "#ffffff"
        text: "X:"
        smooth: true
        font.pointSize: 14

        Text {
            id: xCoord
            color: "#ffffff"
            text: ""
            font.pointSize: 14
            anchors.leftMargin: 20
            anchors.fill: parent
        }
    }

    Text {      // Y coordinate text
        id: yLabel
        y: 90
        width: 80
        height: 20
        color: "#ffffff"
        text: "Y:"
        anchors.horizontalCenterOffset: 0       // Center the label horizontally
        anchors.horizontalCenter: parent.horizontalCenter
        smooth: true
        font.pointSize: 14

        Text {
            id: yCoord
            color: "#ffffff"
            text: ""
            anchors.fill: parent
            font.pointSize: 14
            anchors.leftMargin: 20
        }
    }

    Text {      // Z coordinate text
        id: zLabel
        x: yLabel.x + 100   // 100 pixels right of yLabel
        y: 90
        width: 80
        height: 20
        color: "#ffffff"
        text: "Z:"
        smooth: true
        font.pointSize: 14

        Text {
            id: zCoord
            x: 20
            y: 2
            color: "#ffffff"
            text: ""
            anchors.fill: parent
            font.pointSize: 14
            anchors.leftMargin: 20
        }
    }
}
