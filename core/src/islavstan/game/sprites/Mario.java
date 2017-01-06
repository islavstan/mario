package islavstan.game.sprites;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import islavstan.game.MarioBros;
import islavstan.game.screens.PlayScreen;
//https://github.com/BrentAureli/SuperMario/tree/4bf1375d459fd0e61219772b2c2f9a441c0ad030/core/src/com/brentaureli/mariobros
public class Mario extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion marioStand;//текстура не движущегося марио

    public Mario(World world, PlayScreen playScreen){
       super(playScreen.getAtlas().findRegion("little_mario"));
        this.world=world;
        defineMario();
        marioStand=new TextureRegion( getTexture(),1,11,16,16);
        setBounds(0, 0, 16 / MarioBros.PPM, 16 / MarioBros.PPM);//задаём размер на экране
        setRegion(marioStand);

    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }

    private void defineMario() {
        BodyDef bDef = new BodyDef();//создаём марио
        bDef.position.set(32/ MarioBros.PPM,32/ MarioBros.PPM);//start position mario
        bDef.type=BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bDef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape =new CircleShape();//создаём круг пока вместо марио
        shape.setRadius(6/ MarioBros.PPM);

        fdef.shape=shape;
        b2body.createFixture(fdef);

    }


}
