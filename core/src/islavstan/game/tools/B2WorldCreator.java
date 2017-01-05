package islavstan.game.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import islavstan.game.MarioBros;
import islavstan.game.sprites.Brick;
import islavstan.game.sprites.Coin;


public class B2WorldCreator {

    public B2WorldCreator(World world, TiledMap map){
        BodyDef bodyDef =new BodyDef();//содержит все данные, необходимые для построения твердого тела
        PolygonShape shape =new PolygonShape();
        FixtureDef fdef = new FixtureDef();//used to create a fixture
        Body body;


        //create body and fixture for all object in map
        //создаём землю
        for(MapObject object:map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){//получаем землю, она вторая в списке, счёт с нуля
            Rectangle rect =((RectangleMapObject)object).getRectangle();

           /* Тип объекта задаётся с помощью BodyType. Тела бывают трёх типов:
            static — нулевая масса, нулевая скорость, передвинуть можно лишь программно;
            kinematic — нулевая масса, ненулевая скорость, может быть сдвинут;
            dynamic — положительная масса, ненулевая скорость, может быть сдвинут.*/

            bodyDef.type=BodyDef.BodyType.StaticBody;//установить тип тела

            bodyDef.position.set((rect.getX()+rect.getWidth()/2)/ MarioBros.PPM,(rect.getY()+rect.getHeight()/2)/MarioBros.PPM);

            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth()/2/MarioBros.PPM,rect.getHeight()/2/MarioBros.PPM);
            fdef.shape=shape;
            body.createFixture(fdef);

        }

//создаём трубы
        for(MapObject object:map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =((RectangleMapObject)object).getRectangle();

            bodyDef.type=BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX()+rect.getWidth()/2)/MarioBros.PPM,(rect.getY()+rect.getHeight()/2)/MarioBros.PPM);

            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth()/2/MarioBros.PPM,rect.getHeight()/2/MarioBros.PPM);
            fdef.shape=shape;
            body.createFixture(fdef);

        }
//создаём ящики для монет
        for(MapObject object:map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =((RectangleMapObject)object).getRectangle();

          new Coin(world,map,rect);
        }

        //создаём просто ящики
        for(MapObject object:map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =((RectangleMapObject)object).getRectangle();

            new Brick(world,map,rect);

        }
    }
}
