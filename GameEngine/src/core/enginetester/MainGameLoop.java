package core.enginetester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import core.render.DisplayManager;
import core.render.Loader;
import core.render.MasterRenderer;
import entities.Camera;
import entities.Entity;
import entities.Light;
import model.RawModel;
import model.TexturedModel;
import textures.ModelTexture;
import utils.OBJLoader;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		RawModel model = OBJLoader.loadObjModel("lptree", loader);
		
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("white")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		
		Entity entity = new Entity(staticModel, new Vector3f(0,-5,-25),0,0,0,1);
		Light light = new Light(new Vector3f(200,200,100), new Vector3f(1,1,1));		
		
		Camera camera = new Camera();
		
		MasterRenderer renderer = new MasterRenderer();
		
		while(!Display.isCloseRequested()) {
			entity.increaseRotation(0, 1, 0);
			camera.move();
			renderer.processEntity(entity);
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
			
			
		}
		
		renderer.cleanup();
		loader.cleanup();
		DisplayManager.closeDisplay();
		
	}

}
