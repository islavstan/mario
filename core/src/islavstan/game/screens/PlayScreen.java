package islavstan.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
    public PlayScreen (MarioBros game){
        this.game=game;
        hud=new Hud(game.batch);
        //texture=new Texture("badlogic.jpg");
        camera=new OrthographicCamera();
       // gamePort=new StretchViewport(800,480,camera);//изображение будет растягиваться при изменении размера экрана
       // gamePort=new ScreenViewport(camera);
        gamePort=new FitViewport(MarioBros.V_WIDTH,MarioBros.V_HEIGHT,camera);//изображение масштабируется под размер экрана
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(24/255f, 242/255f, 224/255f, 1);//задаём цвет
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);// Эта строка очищает экран
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();


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
