package com.joyousjake.sandbox

import java.awt.*
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JFrame.EXIT_ON_CLOSE
import javax.swing.JPanel
import javax.swing.Timer

/**
 * Created by jacob on 5/11/17.
 */

fun main(args: Array<String>) {
    with(frame) {
        layout = BorderLayout()
        defaultCloseOperation = EXIT_ON_CLOSE
        val drawPanel = DrawPanel()
        add(drawPanel, BorderLayout.NORTH)
        add(FieldsPanel(drawPanel.timer), BorderLayout.SOUTH)
        pack()
        isVisible = true
    }
}

val frame: JFrame = JFrame("Sandbox")

class FieldsPanel(val timer: Timer): JPanel(BorderLayout()) {
    init {
        val object1Panel = objectPanel("Object 1")
        add(object1Panel, BorderLayout.WEST)

        val buttons = JPanel(GridLayout(2,1))
        with(buttons) {
            val startButton = JButton("Start")
            startButton.addActionListener { timer.start() }
            add(startButton)

            val stopButton = JButton("Stop")
            stopButton.addActionListener { timer.stop() }
            add(stopButton)
        }

        val object2Panel = objectPanel("Object 2")
        add(object2Panel, BorderLayout.EAST)

    }
}

fun objectPanel(name: String): JPanel {
    val objectPanel = JPanel(GridBagLayout())
    val constraints: GridBagConstraints = GridBagConstraints()
    with(constraints) {
        gridheight = 4
        gridwidth = 2

        gridy = 0
        gridx = 0
        objectPanel.add(Label(name),this)

        gridx = 0
        gridy = 1
        objectPanel.add(Label("Mass"), this)
        gridx = 1
        gridy = 1
        objectPanel.add(TextField(), this)

        gridx = 0
        gridy = 2
        objectPanel.add(Label("CoR"), this)
        gridx = 1
        gridy = 2
        objectPanel.add(TextField(), this)

        gridx = 0
        gridy = 3
        objectPanel.add(Label("Initial Velocity"), this)
        gridx = 1
        gridy = 3
        objectPanel.add(TextField(), this)
    }
    return objectPanel
}


class DrawPanel : JPanel() {
    val timer = Timer(50, ActionListener {  })
    override fun paintComponent(graphics: Graphics?) {
        graphics?.let{
            it.color = Color.BLACK
            it.fillOval(0,0,50,50)
        }
    }

    override fun update(graphics: Graphics?) = repaint()
}

fun Container.size(width: Int, height: Int) {
    size = Dimension(width, height)
}
