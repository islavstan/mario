package islavstan.game.sprites;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;

import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;


import islavstan.game.MarioBros;
import islavstan.game.screens.PlayScreen;


public class Mario extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING}

    ;
    public State currentState;
    public State previousState;

    private Animation marioRun;
    private Animation marioJump;

    private boolean runningRight;
    private float stateTimer; //счётчик состояния марио

    public World world;
    public Body b2body;
    private TextureRegion marioStand;//текстура не движущегося марио

    public Mario(World world, PlayScreen playScreen) {
        super(playScreen.getAtlas().findRegion("little_mario"));
        this.world = world;

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        //инициализируем анимацию бега
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 4; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 11, 16, 16));
        marioRun = new Animation(0.1f, frames);
        frames.clear();
        //инициализируем анимацию прыжка
        for (int i = 4; i < 6; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 11, 16, 16));
        marioJump = new Animation(0.1f, frames);


        defineMario();
        marioStand = new TextureRegion(getTexture(), 1, 11, 16, 16);//берём текстуру из файла
        setBounds(0, 0, 16 / MarioBros.PPM, 16 / MarioBros.PPM);//задаём размер марио  на экране
        setRegion(marioStand);

    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);//обновляем позицию марио
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = (TextureRegion) marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = (TextureRegion) marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;

    }

    private State getState() {
        if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))//значит что марио прыгает
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)//марио падает
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)//марио бежит
            return State.RUNNING;

        else return State.STANDING;

    }

    private void defineMario() {
        BodyDef bDef = new BodyDef();//создаём марио
        bDef.position.set(32 / MarioBros.PPM, 32 / MarioBros.PPM);//start position mario
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bDef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MarioBros.PPM);
        fdef.filter.categoryBits =MarioBros.MARIO_BIT;
        fdef.filter.maskBits = MarioBros.DEFAULT_BIT | MarioBros.COIN_BIT |MarioBros.BRICK_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);


//создаём фигуру для головы марио чтобы понимать что она ударяется о ящики
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / MarioBros.PPM, 7 / MarioBros.PPM), new Vector2(2 / MarioBros.PPM, 7 / MarioBros.PPM));
        fdef.shape = head;
         fdef.isSensor=true;
        b2body.createFixture(fdef).setUserData("head");
    }
}



