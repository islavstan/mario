package islavstan.game.sprites;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import islavstan.game.MarioBros;


//класс монет
public class Coin extends InteractiveTailObject {
    public Coin(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);


    }
}
