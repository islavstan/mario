package islavstan.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import islavstan.game.MarioBros;

//родительский класс для coin and brick
public abstract class InteractiveTailObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;

    public InteractiveTailObject(World world,TiledMap map, Rectangle bounds){
        this.world =world;
        this.map =map;
        this.bounds =bounds;

        BodyDef bodyDef =new BodyDef();
        FixtureDef fdef =new FixtureDef();
        PolygonShape shape =new PolygonShape();
        bodyDef.type= BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX()+bounds.getWidth()/2)/ MarioBros.PPM,(bounds.getY()+bounds.getHeight()/2)/MarioBros.PPM);
        body = world.createBody(bodyDef);
        shape.setAsBox(bounds.getWidth()/2/MarioBros.PPM,bounds.getHeight()/2/MarioBros.PPM);
        fdef.shape=shape;
        body.createFixture(fdef);
    }
}
