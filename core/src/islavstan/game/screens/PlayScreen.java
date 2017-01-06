package islavstan.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureArray;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import islavstan.game.sprites.Mario;
import islavstan.game.tools.B2WorldCreator;


public class PlayScreen implements Screen {
private MarioBros game;
    private TextureAtlas atlas;

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

    Mario player;

    public PlayScreen (MarioBros game){
        atlas=new TextureAtlas("mario.pack");
        this.game=game;
        hud=new Hud(game.batch);


        //texture=new Texture("badlogic.jpg");
        // gamePort=new StretchViewport(800,480,camera);//изображение будет растягиваться при изменении размера экрана
        // gamePort=new ScreenViewport(camera);

        camera=new OrthographicCamera();//создаём камеру которая будет следовать за марио
        gamePort=new FitViewport(MarioBros.V_WIDTH/MarioBros.PPM,MarioBros.V_HEIGHT/MarioBros.PPM,camera);//изображение масштабируется под размер экрана
        mapLoader =new TmxMapLoader();
        map=mapLoader.load("level1.tmx");//инициализируем карту
        renderer=new OrthogonalTiledMapRenderer(map,1/MarioBros.PPM);//отрисовываем
        camera.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);//устанавливаем камеру


      /*  Конструктор World принимает два параметра: вектор направления силы тяготения
        и флаг, указывающий на то, стоит ли симулировать бездействующие тела. Если с первым
        параметром всё ясно, то со вторым не очень. В принципе, этот параметр всегда можно в
        true ставить, для большей продуктивности.*/

        world = new World(new Vector2(0,-10),true);//контейнер для объектов
        b2dr=new Box2DDebugRenderer();



      new B2WorldCreator(world,map);
        player=new Mario(world,this);

    }


    public void update(float dt){
        handleInput(dt);
        world.step(1/60f,6,2);// указывает частоту, с которой обновлять физику
        player.update(dt);
        camera.position.x=player.b2body.getPosition().x;
        camera.update();//нужно обновлять камеру каждый раз когда происходит какоето движение, нажатие на клавиши
        renderer.setView(camera);

    }

    public void handleInput(float dt) {
      /*  if(Gdx.input.isTouched())//если произошло нажатие
            camera.position.x +=100*dt/ MarioBros.PPM;//меняем расположение камеры*/

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){//одноразовое нажатие
            player.b2body.applyLinearImpulse(new Vector2(0,3f),player.b2body.getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x<=2){
            player.b2body.applyLinearImpulse(new Vector2(0.1f,0),player.b2body.getWorldCenter(),true);

        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x>= -2){
            player.b2body.applyLinearImpulse(new Vector2(-0.1f,0),player.b2body.getWorldCenter(),true);

        }
    }


    public TextureAtlas getAtlas(){
        return  atlas;
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
        game.batch.setProjectionMatrix(camera.combined);//the combined projection and view matrix

        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();
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
      map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
