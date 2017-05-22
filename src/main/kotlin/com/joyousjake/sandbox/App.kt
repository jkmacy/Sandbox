package com.joyousjake.sandbox

import java.awt.*
import java.awt.event.ActionListener
import javax.swing.*
import javax.swing.JFrame.EXIT_ON_CLOSE

/**
 * Created by jacob on 5/11/17.
 */

fun main(args: Array<String>) {
    with(frame) {
        layout = BorderLayout()
        defaultCloseOperation = EXIT_ON_CLOSE
        add(drawPanel, BorderLayout.NORTH)
        add(fieldsPanel, BorderLayout.SOUTH)
        pack()
        isVisible = true
    }
}
val object1Properties = mutableListOf(0.0,0.0,0.0)
val object2Properties = mutableListOf(0.0,0.0,0.0)

val frame: JFrame = JFrame("Sandbox")
val drawPanel = DrawPanel()
val timer = Timer(50, ActionListener { drawPanel.update(drawPanel.graphics) })
val fieldsPanel = FieldsPanel()

class FieldsPanel: JPanel(BorderLayout(10, 10)) {
    val object1Fields = listOf(JTextField("1"), JTextField("1"), JTextField("1"))
    val object2Fields = listOf(JTextField("1"), JTextField("1"), JTextField("1"))
    init {
        val object1Panel = objectPanel("Object 1", object1Fields)
        add(object1Panel, BorderLayout.WEST)

        val buttons = JPanel(GridLayout(2,1))
        with(buttons) {
            val startButton = JButton("Start")
            startButton.addActionListener {
                drawPanel.pos1 = 0.0
                drawPanel.pos2 = 800.0
                for (i in 0..2) {
                    object1Properties[i] = fieldsPanel.object1Fields[i].text.toDouble()
                    object2Properties[i] = fieldsPanel.object2Fields[i].text.toDouble()
                }
                val fVels = finalVelocities(m1 = object1Properties[0], m2 = object2Properties[0], e1 = object1Properties[1],
                        e2 = object2Properties[1], v1 = object1Properties[2], v2 = object2Properties[2])
                drawPanel.fVel1 = fVels.first
                drawPanel.fVel2 = fVels.second

                drawPanel.vel1 = object2Properties[2]
                drawPanel.vel2 = object2Properties[2]
                timer.start()
            }
            add(startButton)

            val stopButton = JButton("Stop")
            stopButton.addActionListener { timer.stop() }
            add(stopButton)
        }
        add(buttons, BorderLayout.CENTER)

        val object2Panel = objectPanel("Object 2", object2Fields)
        add(object2Panel, BorderLayout.EAST)

        size(400, 200)
    }
}

fun objectPanel(name: String, fields: List<JTextField>): JPanel {
    val tempPanel = JPanel(GridBagLayout())

    val insets = Insets(10,10,10,10)
    var constraints = GridBagConstraints()

    constraints.insets = insets
    constraints.gridy = 0
    constraints.gridx = 0
    tempPanel.add(JLabel(name), constraints)

    constraints = GridBagConstraints()
    constraints.insets = insets
    constraints.gridx = 0
    constraints.gridy = 1
    tempPanel.add(JLabel("Mass"), constraints)
    constraints.gridx = 1
    constraints.gridy = 1
    constraints.ipadx = 50
    tempPanel.add(fields[0], constraints)

    constraints.gridx = 0
    constraints.gridy = 2
    tempPanel.add(JLabel("CoR"), constraints)
    constraints.gridx = 1
    constraints.gridy = 2
    constraints.ipadx = 50
    tempPanel.add(fields[1], constraints)

    constraints.gridx = 0
    constraints.gridy = 3
    tempPanel.add(JLabel("Initial Velocity"), constraints)
    constraints.gridx = 1
    constraints.gridy = 3
    constraints.ipadx = 50
    tempPanel.add(fields[2], constraints)
    return tempPanel
}


class DrawPanel : JPanel() {
    var pos1 = 0.0
    var vel1 : Double = 0.0
    var fVel1 : Double = 0.0

    var pos2 = 770.0
    var vel2 : Double = 0.0
    var fVel2 : Double = 0.0
    init {
        size(800,30)
    }

    override fun paintComponent(graphics: Graphics?) {
        graphics?.let {
            it.color = Color.WHITE
            it.fillOval((pos1-vel1).toInt(), 0, 30, 30)
            it.fillOval((pos2+vel2).toInt()-30, 0, 30, 30)

            it.color = Color.BLACK
            it.fillOval(pos1.toInt(), 0, 30, 30)
            it.fillOval(pos2.toInt()-30, 0, 30, 30)
        }
    }

    override fun update(graphics: Graphics?) {
        if (pos1 + 30 >= pos2) {
            vel1 = fVel1
            vel2 = fVel2
        }
        pos1 += vel1
        pos2 -= vel2
        repaint()
    }
}

fun Container.size(width: Int, height: Int) {
    preferredSize = Dimension(width, height)
}
