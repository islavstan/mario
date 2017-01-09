package islavstan.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import islavstan.game.MarioBros;


public class Brick  extends InteractiveTailObject {
    public Brick(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(MarioBros.BRICK_BIT);

    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("brick","касание");
        setCategoryFilter(MarioBros.DESTROYED_BIT);//если ударяем головой по brick
        getCell().setTile(null);//удаляем brick
    }
}
