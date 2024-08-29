package org.aesgard.caninoengine.samples.ProgramacaoGamesComJava.chapter03;

import java.awt.Color;

import org.aesgard.caninoengine.CaninoGameEngine2D;

public class Asteroids extends CaninoGameEngine2D {
	
	//toggle for drawing bounding boxes
    boolean showBounds = false;

    //create the asteroid array
    private int ASTEROIDS;
    private Asteroid[] ast;

    //create the bullet array
    private int BULLETS;
    private Bullet[] bullet;
    private int currentBullet = 0;

    //the player's ship
    private Ship ship;
    
    public void gameInit() {
    	ASTEROIDS = 20;
    	BULLETS = 10;
    	
    	ast    = new Asteroid[ASTEROIDS];
    	bullet = new Bullet[BULLETS];
    	ship   = new Ship();
    	
        //set up the ship
        ship.setX(320);
        ship.setY(240);

        //set up the bullets
        for (int n = 0; n<BULLETS; n++) {
            bullet[n] = new Bullet();
        }

        //create the asteroids
        for (int n = 0; n<ASTEROIDS; n++) {
            ast[n] = new Asteroid();
            ast[n].setRotationVelocity(rand.nextInt(3)+1);
            ast[n].setX((double)rand.nextInt(600)+20);
            ast[n].setY((double)rand.nextInt(440)+20);
            ast[n].setMoveAngle(rand.nextInt(360));
            double ang = ast[n].getMoveAngle() - 90;
            ast[n].setVelX(calcAngleMoveX(ang));
            ast[n].setVelY(calcAngleMoveY(ang));
        }

    }

    public void gameDraw() {
        //start off transforms at identity
    	getScreen().setTransform(identity);

        //erase the background
    	getScreen().setPaint(Color.BLACK);
    	getScreen().fillRect(0, 0, getGameWidth(), getGameHeight());

        //print some status information
    	getScreen().setColor(Color.WHITE);
    	getScreen().drawString("Ship: " + Math.round(ship.getX()) + "," +
            Math.round(ship.getY()) , 5, 100);
    	getScreen().drawString("Move angle: " + Math.round(
            ship.getMoveAngle())+90, 5, 35);
    	getScreen().drawString("Face angle: " +  Math.round(
            ship.getFaceAngle()), 5, 50);

        //draw the game graphics
        drawShip();
        drawBullets();
        drawAsteroids();
    }

    /*****************************************************
     * drawShip called by component update event
     *****************************************************/
    public void drawShip() {
    	getScreen().setTransform(identity);
    	getScreen().translate(ship.getX(), ship.getY());
    	getScreen().rotate(Math.toRadians(ship.getFaceAngle()));
    	getScreen().setColor(Color.ORANGE);
    	getScreen().fill(ship.getShape());
    }

    /*****************************************************
     * drawBullets called by component update event
     *****************************************************/
    public void drawBullets() {

		//iterate through the array of bullets
        for (int n = 0; n < BULLETS; n++) {

			//is this bullet currently in use?
            if (bullet[n].isAlive()) {

                //draw the bullet
            	getScreen().setTransform(identity);
            	getScreen().translate(bullet[n].getX(), bullet[n].getY());
            	getScreen().setColor(Color.MAGENTA);
            	getScreen().draw(bullet[n].getShape());
            }
        }
    }

    /*****************************************************
     * drawAsteroids called by component update event
     *****************************************************/
    public void drawAsteroids() {

		//iterate through the asteroids array
        for (int n = 0; n < ASTEROIDS; n++) {

			//is this asteroid being used?
            if (ast[n].isAlive()) {

                //draw the asteroid
            	getScreen().setTransform(identity);
            	getScreen().translate(ast[n].getX(), ast[n].getY());
            	getScreen().rotate(Math.toRadians(ast[n].getMoveAngle()));
            	getScreen().setColor(Color.DARK_GRAY);
            	getScreen().fill(ast[n].getShape());

            }
        }
    }

    /*****************************************************
     * move and animate the objects in the game
     *****************************************************/
    protected void gameUpdate() {
        updateShip();
        updateBullets();
        updateAsteroids();
        checkCollisions();
    }

    /*****************************************************
     * Update the ship position based on velocity
     *****************************************************/
    public void updateShip() {

        //update ship's X position
        ship.incX(ship.getVelX());

        //wrap around left/right
        if (ship.getX() < -10)
            ship.setX(getGameWidth() + 10);
        else if (ship.getX() > getGameWidth() + 10)
            ship.setX(-10);

        //update ship's Y position
        ship.incY(ship.getVelY());

        //wrap around top/bottom
        if (ship.getY() < -10)
            ship.setY(getGameHeight() + 10);
        else if (ship.getY() > getGameHeight() + 10)
            ship.setY(-10);
    }

    /*****************************************************
     * Update the bullets based on velocity
     *****************************************************/
    public void updateBullets() {

        //move each of the bullets
        for (int n = 0; n < BULLETS; n++) {

			//is this bullet being used?
            if (bullet[n].isAlive()) {

                //update bullet's x position
                bullet[n].incX(bullet[n].getVelX());

                //bullet disappears at left/right edge
                if (bullet[n].getX() < 0 ||
                    bullet[n].getX() > getGameWidth())
                {
                    bullet[n].setAlive(false);
                }

                //update bullet's y position
                bullet[n].incY(bullet[n].getVelY());

                //bullet disappears at top/bottom edge
                if (bullet[n].getY() < 0 ||
                    bullet[n].getY() > getGameHeight())
                {
                    bullet[n].setAlive(false);
                }
            }
        }
    }

    /*****************************************************
     * Update the asteroids based on velocity
     *****************************************************/
    public void updateAsteroids() {

        //move and rotate the asteroids
        for (int n = 0; n < ASTEROIDS; n++) {

			//is this asteroid being used?
            if (ast[n].isAlive()) {

                //update the asteroid's X value
                ast[n].incX(ast[n].getVelX());

                //warp the asteroid at screen edges
                if (ast[n].getX() < -20)
                    ast[n].setX(getGameWidth() + 20);
                else if (ast[n].getX() > getGameWidth() + 20)
                    ast[n].setX(-20);

                //update the asteroid's Y value
                ast[n].incY(ast[n].getVelY());

                //warp the asteroid at screen edges
                if (ast[n].getY() < -20)
                    ast[n].setY(getGameHeight() + 20);
                else if (ast[n].getY() > getGameHeight() + 20)
                    ast[n].setY(-20);

                //update the asteroid's rotation
                ast[n].incMoveAngle(ast[n].getRotationVelocity());

                //keep the angle within 0-359 degrees
                if (ast[n].getMoveAngle() < 0)
                    ast[n].setMoveAngle(360 - ast[n].getRotationVelocity());
                else if (ast[n].getMoveAngle() > 359)
                    ast[n].setMoveAngle(ast[n].getRotationVelocity());
            }
        }
    }

    /*****************************************************
     * Test asteroids for collisions with ship or bullets
     *****************************************************/
    public void checkCollisions() {

        //iterate through the asteroids array
        for (int m = 0; m<ASTEROIDS; m++) {

			//is this asteroid being used?
            if (ast[m].isAlive()) {

				/*
                 * check for collision with bullet
                 */
                for (int n = 0; n < BULLETS; n++) {

					//is this bullet being used?
                    if (bullet[n].isAlive()) {

                        //perform the collision test
                        if (ast[m].getBounds().contains(
                                bullet[n].getX(), bullet[n].getY()))
                        {
                            bullet[n].setAlive(false);
                            ast[m].setAlive(false);
                            continue;
                        }
                    }
                }

				/*
                 * check for collision with ship
                 */
                if (ast[m].getBounds().intersects(ship.getBounds())) {
                    ast[m].setAlive(false);
                    ship.setX(320);
                    ship.setY(240);
                    ship.setFaceAngle(0);
                    ship.setVelX(0);
                    ship.setVelY(0);
                    continue;
                }
            }
        }
    }
    
    public void doKeyLeft() {
        //left arrow rotates ship left 5 degrees
        ship.incFaceAngle(-5);
        if (ship.getFaceAngle() < 0) ship.setFaceAngle(360-5);
    }
    
    public void doKeyRight() {
        //right arrow rotates ship right 5 degrees
        ship.incFaceAngle(5);
        if (ship.getFaceAngle() > 360) ship.setFaceAngle(5);
    }
    
    public void doKeyUp() {
        //up arrow adds thrust to ship (1/10 normal speed)
        ship.setMoveAngle(ship.getFaceAngle() - 90);
        ship.incVelX(calcAngleMoveX(ship.getMoveAngle()) * 0.1);
        ship.incVelY(calcAngleMoveY(ship.getMoveAngle()) * 0.1);
    }
    
    public void doMouseButton1Pressed() {
    	fire();
    }
    
    public void doKeySpace() {
        fire();
    }

	private void fire() {
		//fire a bullet
        currentBullet++;
        if (currentBullet > BULLETS - 1) currentBullet = 0;
        bullet[currentBullet].setAlive(true);

        //point bullet in same direction ship is facing
        bullet[currentBullet].setX(ship.getX());
        bullet[currentBullet].setY(ship.getY());
        bullet[currentBullet].setMoveAngle(ship.getFaceAngle() - 90);

        //fire bullet at angle of the ship
        double angle = bullet[currentBullet].getMoveAngle();
        double svx = ship.getVelX();
        double svy = ship.getVelY();
        bullet[currentBullet].setVelX(svx + calcAngleMoveX(angle) * 2);
        bullet[currentBullet].setVelY(svy + calcAngleMoveY(angle) * 2);
	}

    /*****************************************************
     * calculate X movement value based on direction angle
     *****************************************************/
    public double calcAngleMoveX(double angle) {
        return (double) (Math.cos(angle * Math.PI / 180));
      }

    /*****************************************************
     * calculate Y movement value based on direction angle
     *****************************************************/
    public double calcAngleMoveY(double angle) {
        return (double) (Math.sin(angle * Math.PI / 180));
    }
    
    public static void main(String[] args)
    {
    	new Asteroids().start();
    }

}
