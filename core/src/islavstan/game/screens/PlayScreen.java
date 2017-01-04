package islavstan.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import islavstan.game.MarioBros;
import islavstan.game.scenes.Hud;


public class PlayScreen implements Screen {
private MarioBros game;
  //  Texture texture;
    private Hud hud;
    private OrthographicCamera camera;
    private Viewport gamePort;//используется для лучшего отображения на разных размерах экранов

    //-------------Tiled map variables----------------
    private TmxMapLoader mapLoader;//загружает карту в игру
    private TiledMap map;//ссылка на саму карту
    private OrthogonalTiledMapRenderer renderer;
    //-------------Tiled map variables----------------


    //-------------Box2d variables----------------
    private World world;
    private Box2DDebugRenderer b2dr;
    //-------------Box2d variables----------------


    public PlayScreen (MarioBros game){
        this.game=game;
        hud=new Hud(game.batch);

        //texture=new Texture("badlogic.jpg");
        // gamePort=new StretchViewport(800,480,camera);//изображение будет растягиваться при изменении размера экрана
        // gamePort=new ScreenViewport(camera);

        camera=new OrthographicCamera();//создаём камеру которая будет следовать за марио
        gamePort=new FitViewport(MarioBros.V_WIDTH,MarioBros.V_HEIGHT,camera);//изображение масштабируется под размер экрана
        mapLoader =new TmxMapLoader();
        map=mapLoader.load("level1.tmx");//инициализируем карту
        renderer=new OrthogonalTiledMapRenderer(map);//отрисовываем
        camera.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);//устанавливаем камеру


      /*  Конструктор World принимает два параметра: вектор направления силы тяготения
        и флаг, указывающий на то, стоит ли симулировать бездействующие тела. Если с первым
        параметром всё ясно, то со вторым не очень. В принципе, этот параметр всегда можно в
        true ставить, для большей продуктивности.*/

        world = new World(new Vector2(0,0),true);//контейнер для объектов
        b2dr=new Box2DDebugRenderer();

        BodyDef bodyDef =new BodyDef();//содержит все данные, необходимые для построения твердого тела
        PolygonShape shape =new PolygonShape();
        FixtureDef fdef = new FixtureDef();//used to create a fixture
        Body body;


        //create body and fixture for all object in map
        //создаём землю
        for(MapObject object:map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){//получаем землю, она вторая в списке, счёт с нуля
            Rectangle rect =((RectangleMapObject)object).getRectangle();

            bodyDef.type=BodyDef.BodyType.StaticBody;
            bodyDef.position.set(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2);

            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape=shape;
            body.createFixture(fdef);

        }

//создаём трубы
        for(MapObject object:map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =((RectangleMapObject)object).getRectangle();

            bodyDef.type=BodyDef.BodyType.StaticBody;
            bodyDef.position.set(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2);

            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape=shape;
            body.createFixture(fdef);

        }
//создаём ящики для монет
        for(MapObject object:map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =((RectangleMapObject)object).getRectangle();

            bodyDef.type=BodyDef.BodyType.StaticBody;
            bodyDef.position.set(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2);

            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape=shape;
            body.createFixture(fdef);

        }

        //создаём просто ящики
        for(MapObject object:map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =((RectangleMapObject)object).getRectangle();

            bodyDef.type=BodyDef.BodyType.StaticBody;
            bodyDef.position.set(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2);

            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape=shape;
            body.createFixture(fdef);

        }

    }


    public void update(float dt){
        handleInput(dt);
        camera.update();//нужно обновлять камеру каждый раз когда происходит какоето движение, нажатие на клавиши
        renderer.setView(camera);
    }

    public void handleInput(float dt) {
        if(Gdx.input.isTouched())//если произошло нажатие
            camera.position.x +=100*dt;//меняем расположение камеры
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(24/255f, 242/255f, 224/255f, 1);//задаём цвет
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);// Эта строка очищает экран
        //render game map
        renderer.render();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();


        //render box2DDebugLines
        b2dr.render(world,camera.combined);




      /*  game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(texture,0,0);//рисуем текстуру на экране,по иси x и y ставим 0
        game.batch.end();
*/
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
