package org.aesgard.caninoengine.samples.ProgramacaoGamesComJava.chapter03;

/*****************************************************
 * Beginning Java Game Programming, 2nd Edition
 * by Jonathan S. Harbour
 * Bullet class - polygonal shape of a bullet
 *****************************************************/

import java.awt.Rectangle;

import org.aesgard.caninoengine.entity2d.base.BaseVectorShape;

/*********************************************************
 * Bullet class derives from BaseVectorShape
 **********************************************************/
public class Bullet extends BaseVectorShape {

    //bounding rectangle
    public Rectangle getBounds() {
        Rectangle r;
        r = new Rectangle((int)getX(), (int) getY(), 1, 1);
        return r;
    }

    Bullet() {
        //create the bullet shape
        setShape(new Rectangle(0, 0, 1, 1));
        setAlive(false);
    }
}
