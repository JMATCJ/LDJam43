package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.util.Util;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import static com.github.jmatcj.ld43.stat.Stat.*;

public class Enemy extends StatableEntity {
    private double velocity;

    public Enemy(double xPos, double yPos, double velocity, int bHealth, int bAttack, int bSpeed, int bBulletSpeed) {
        super(xPos, yPos, 10, 10, bHealth, bAttack, bSpeed, bBulletSpeed);
        this.velocity = velocity;
    }

    @Override
    public void draw(GraphicsContext gc, Game g) {
        gc.save();
        Util.drawText(gc, Color.BLACK, 12, TextAlignment.CENTER, getStatValue(HP) + "", (int)xPos + 5, (int)yPos - 5);
        Util.rotate(gc, Math.toDegrees(Math.atan2(yPos - g.player.getY(), xPos - g.player.getX())), this);
        drawSquare(gc, Color.ORANGE, true);
        gc.restore();
    }

    @Override
    public void update(long ns, Game g) {
        if (prevNS == 0) {
            updateNS(ns);
        }

        double dx = (xPos > g.player.getX()) ? -Math.cos(Math.atan2(Math.abs(yPos - g.player.getY()), Math.abs(xPos - g.player.getX()))) * velocity : Math.cos(Math.atan2(Math.abs(yPos - g.player.getY()), Math.abs(xPos - g.player.getX()))) * velocity;
        double dy = (yPos > g.player.getY()) ? -Math.sin(Math.atan2(Math.abs(yPos - g.player.getY()), Math.abs(xPos - g.player.getX()))) * velocity : Math.sin(Math.atan2(Math.abs(yPos - g.player.getY()), Math.abs(xPos - g.player.getX()))) * velocity;

        double frameMovement = Util.getFracOfTimeElapsed(prevNS, ns) * ((getStatValue(SPEED) - 1) / 10.0 + 1);
        xPos += dx * frameMovement;
        yPos += dy * frameMovement;

        keepInBounds(); // If the enemy has moved out-of-bounds, move them back in
        
        g.spawnEntity(new Projectile(xPos, yPos, xPos + 10, yPos + 10, 500 * ((getStatValue(BULLETSPEED) - 1) / 10.0 + 1), getStatValue(ATTACK), false, true));


        Entity collision = checkCollision(g);
        if (collision != null) {
            if (collision instanceof Projectile) {
                Projectile p = (Projectile)collision;
                if (!p.enemyIgnore()) {
                    g.removeEntity(p);
                    if (addToStat(HP, -p.getDamage()) == 0) {
                        Platform.exit(); // END THE GAME
                    }
                }
            }
            if (collision instanceof Player) {
                xPos -= dx * frameMovement;
                yPos -= dy * frameMovement;
            }
        }

        updateNS(ns);
    }
}
