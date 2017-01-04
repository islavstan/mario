package islavstan.game.sprites;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import islavstan.game.MarioBros;

public class Mario extends Sprite {
    public World world;
    public Body b2body;

    public Mario(World world){
        this.world=world;
        defineMario();
    }

    private void defineMario() {
        BodyDef bDef = new BodyDef();//создаём марио
        bDef.position.set(32/ MarioBros.PPM,32/ MarioBros.PPM);//start position mario
        bDef.type=BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bDef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape =new CircleShape();//создаём круг пока вместо марио
        shape.setRadius(5/ MarioBros.PPM);

        fdef.shape=shape;
        b2body.createFixture(fdef);

    }


}
