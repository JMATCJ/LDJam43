package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.LDJam43;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Switch extends Entity {
    private Color color;

    public Switch(double xPos, double yPos) {
        super(xPos, yPos, 15.0, 15.0);
        this.color = Color.BLACK;
    }

    public void toggleSwitch() {
        if (color == Color.BLACK) {
            color = Color.GREEN;
            LDJam43.getGame().updateSwitchCount();
        }
    }

    @Override
    public void draw(GraphicsContext gc, Game g) {
        gc.save();
        drawSquare(gc, color, false);
        gc.restore();
    }
}
