import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Smiley32 on 16/09/2016.
 */
public class SimpleSlickGame extends BasicGame {
    public SimpleSlickGame(String gamename) {
        super(gamename);
    }

    private GameContainer container;

    // Carte
    private TiledMap map;

    private float x = 300, y = 300;
    private float xCamera = x, yCamera = y;
    private int direction = 0;
    private int oldDirection = -1;
    private boolean moving = false;
    private Animation[] animations = new Animation[8];

    @Override
    public void init(GameContainer gc) throws SlickException {
        this.container = gc;
        this.map = new TiledMap("resources/test.tmx");

        SpriteSheet spriteSheet = new SpriteSheet(("resources/perso/character.png"), 64, 64);

        this.animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
        this.animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
        this.animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
        this.animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
        this.animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
        this.animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
        this.animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
        this.animations[7] = loadAnimation(spriteSheet, 1, 9, 3);
    }

    private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 100);
        }
        return animation;
    }

    @Override
    public void keyPressed(int key, char c) {
        switch(key) {
            case Input.KEY_UP:
                if(this.moving) {
                    this.oldDirection = this.direction;
                }
                this.direction = 0;
                this.moving = true;
                break;
            case Input.KEY_LEFT:
                if(this.moving) {
                    this.oldDirection = this.direction;
                }
                this.direction = 1;
                this.moving = true;
                break;
            case Input.KEY_DOWN:
                if(this.moving) {
                    this.oldDirection = this.direction;
                }
                this.direction = 2;
                this.moving = true;
                break;
            case Input.KEY_RIGHT:
                if(this.moving) {
                    this.oldDirection = this.direction;
                }
                this.direction = 3;
                this.moving = true;
                break;
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        switch(key) {
            case Input.KEY_UP:
                if(this.direction == 0) {
                    if(this.oldDirection != -1) {
                        this.direction = this.oldDirection;
                        this.oldDirection = -1;
                    } else {
                        this.moving = false;
                    }
                }
                if(this.oldDirection == 0) {
                    this.oldDirection = -1;
                }
                break;
            case Input.KEY_LEFT:
                if(this.direction == 1) {
                    if(this.oldDirection != -1) {
                        this.direction = this.oldDirection;
                        this.oldDirection = -1;
                    } else {
                        this.moving = false;
                    }
                }
                if(this.oldDirection == 1) {
                    this.oldDirection = -1;
                }
                break;
            case Input.KEY_DOWN:
                if(this.direction == 2) {
                    if(this.oldDirection != -1) {
                        this.direction = this.oldDirection;
                        this.oldDirection = -1;
                    } else {
                        this.moving = false;
                    }
                }
                if(this.oldDirection == 2) {
                    this.oldDirection = -1;
                }
                break;
            case Input.KEY_RIGHT:
                if(this.direction == 3) {
                    if(this.oldDirection != -1) {
                        this.direction = this.oldDirection;
                        this.oldDirection = -1;
                    } else {
                        this.moving = false;
                    }
                }
                if(this.oldDirection == 3) {
                    this.oldDirection = -1;
                }
                break;
        }
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        // Si le personnage est censé bouger, on le déplace
        if(this.moving) {
            switch(this.direction) {
                case 0:
                    this.y -= .1f * i;
                    break;
                case 1:
                    this.x -= .1f * i;
                    break;
                case 2:
                    this.y += .1f * i;
                    break;
                case 3:
                    this.x += .1f * i;
                    break;
            }
        }

        // Camera
        int w = gc.getWidth() / 4;
        if(this.x > this.xCamera + w) {
            this.xCamera = this.x - w;
        }
        if(this.x < this.xCamera - w) {
            this.xCamera = this.x + w;
        }
        int h = gc.getHeight() / 4;
        if(this.y > this.yCamera + h) {
            this.yCamera = this.y - h;
        }
        if(this.y < this.yCamera - h) {
            this.yCamera = this.y + h;
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) {

        g.translate(gc.getWidth() / 2 - (int) this.xCamera, gc.getHeight() / 2 - (int) this.yCamera);

        this.map.render(0, 0);
        g.drawString("Hello World!", 100, 100);

        // Personnage :
        g.setColor(new Color(0, 0, 0, .5f));
        g.fillOval(x - 16, y - 8, 32, 16);
        g.drawAnimation(animations[direction + (moving ? 4 : 0)], x - 32, y - 60);
    }

    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new SimpleSlickGame("Premier jeu Slick"));
            appgc.setDisplayMode(1280, 720, false);
            appgc.start();
        } catch (SlickException e) {
            Logger.getLogger(SimpleSlickGame.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
