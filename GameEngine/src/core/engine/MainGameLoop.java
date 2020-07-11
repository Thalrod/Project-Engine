package core.engine;

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
import modules.terrain.Terrain;
import textures.ModelTexture;
import utils.OBJLoader;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		
		RawModel tree = OBJLoader.loadObjModel("models/treeblend", loader);
		RawModel grass = OBJLoader.loadObjModel("models/grassModel", loader);
		
		TexturedModel treeModel = new TexturedModel(tree,new ModelTexture(loader.loadTexture("textures/tree")));
		TexturedModel grassModel = new TexturedModel(grass, new ModelTexture(loader.loadTexture("textures/herb")));
		
		grassModel.getTexture().setUseFakeLighting(true);
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for(int i=0;i<300;i++){
			entities.add(new Entity(treeModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
			entities.add(new Entity(grassModel, new Vector3f(random.nextFloat() * 800 - 400,0,random.nextFloat() * -600),0,0,0,2));
		}
		
		Light light = new Light(new Vector3f(2000,2000,2000), new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("textures/tree")));
		Terrain terrain2 = new Terrain(1,0,loader,new ModelTexture(loader.loadTexture("textures/tree")));
		
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