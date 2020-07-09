package core.enginetester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import terrain.Terrain;
import textures.ModelTexture;
import utils.OBJLoader;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		
		RawModel model = OBJLoader.loadObjModel("models/treeblend", loader);
		
		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("textures/tex")));
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for(int i=0;i<500;i++){
			entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
		}
		
		Light light = new Light(new Vector3f(2000,2000,2000), new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(-1,-1,loader,new ModelTexture(loader.loadTexture("textures/tex")));
		Terrain terrain2 = new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("textures/tex")));
		
		Camera camera = new Camera();	
		MasterRenderer renderer = new MasterRenderer();
		
		while(!Display.isCloseRequested()){
			camera.move();
			
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			for(Entity entity:entities){
				renderer.processEntity(entity);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}

		renderer.cleanup();
		loader.cleanup();
		DisplayManager.closeDisplay();

	}

}