package islavstan.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
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

    protected Fixture fixture;
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
        fixture = body.createFixture(fdef);
    }

    public abstract void onHeadHit();

    public void setCategoryFilter(short filterBit){
        Filter filter =new Filter();
        filter.categoryBits =filterBit;
        fixture.setFilterData(filter);
    }
    public TiledMapTileLayer.Cell getCell(){
       TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);//в этом слое у нас графика
        return layer.getCell((int)(body.getPosition().x * MarioBros.PPM/16),(int)(body.getPosition().y * MarioBros.PPM / 16));//ширина и высота плитки 16 точек

    }
}
