package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.event.EventListener;
import com.github.jmatcj.ld43.gui.Drawable;
import com.github.jmatcj.ld43.tick.Updatable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Player implements EventListener, Updatable, Drawable {
    private double xPos;
    private double yPos;

    public Player(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Override
    public void handleEvent(InputEvent event) {
        if (event instanceof KeyEvent) {
            KeyEvent evt = (KeyEvent)event;
            switch(evt.getCode()) {
                case W:
                    yPos -= 1.0;
                    break;
                case A:
                    xPos -= 1.0;
                    break;
                case S:
                    yPos += 1.0;
                    break;
                case D:
                    xPos += 1.0;
                    break;
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(xPos, yPos, 10, 10);
    }

    @Override
    public void update(long ns) {
        // TODO
    }
}
