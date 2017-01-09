package islavstan.game.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import islavstan.game.sprites.InteractiveTailObject;


public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
//вызывается когда две fixture начинают контакт
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if(fixA.getUserData()=="head"||fixB.getUserData()=="head"){
            Fixture head = fixA.getUserData()== "head" ?  fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA ;
            if(object.getUserData() !=null && InteractiveTailObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveTailObject)object.getUserData()).onHeadHit();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
//вызывается когда контакт дисконект
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
