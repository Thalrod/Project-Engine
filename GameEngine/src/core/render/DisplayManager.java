package core.render;

import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {

	private static final int WIDTH = 1280, HEIGHT = 720, FPS_CAP = 60;
	private static final String TITLE = "Project Engine";

	public static void createDisplay() {

		ContextAttribs attribs = new ContextAttribs(3, 2)
				.withForwardCompatible(true)
				.withProfileCompatibility(true);

		try {

			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle(TITLE);

		} catch (Exception e) {
			e.printStackTrace();
		}

		GL11.glViewport(0, 0, WIDTH, HEIGHT);

	}

	public static void updateDisplay() {

		Display.sync(FPS_CAP);
		Display.update();

	}

	public static void closeDisplay() {
		Display.destroy();
	}

}