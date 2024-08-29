import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.aesgard.caninoengine.CaninoGameEngine3D;
import org.aesgard.caninoengine.texture.TextureLoader;

import static org.aesgard.caninoengine.glutil.APITranslations.*;
import static org.aesgard.caninoengine.glutil.MatrixMath.*;
import static org.aesgard.caninoengine.glutil.VectorMath.*;
import static org.aesgard.caninoengine.util.Constants.*;
import static org.aesgard.caninoengine.util.BufferUtils.*;
import static org.lwjgl.opengl.ARBMultisample.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.util.glu.GLU.*;
import static org.lwjgl.util.glu.Util.*;
import static org.lwjgl.opengl.ARBImaging.*;
import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.*;
import static org.lwjgl.opengl.EXTBgra.*;
import static org.aesgard.caninoengine.glutil.TessallationType.*;
import org.lwjgl.util.glu.*;
import org.aesgard.caninoengine.glutil.*;

@SuppressWarnings("unused")
public class Basic extends CaninoGameEngine3D {

	private TextureLoader loader = new TextureLoader();
	private boolean menuChanged = false;
	private int menuItem = 0;

	public Basic() {
		// TODO Auto-generated constructor stub
	}
	
	public Basic(String title) {
		super(title);
	}

	@Override
	public void RenderScene() {
		// TODO Auto-generated method stub

		if (menuChanged) {
			processMenu(menuItem);
			menuChanged = false;
		}
	}

	@Override
	public void SetupRC() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ChangeSize(int w, int h) {
		// TODO Auto-generated method stub

	}

	// /////////////////////////////////////////////////////////////////////////////
	// Reset flags as appropriate in response to menu selections
	private void processMenu(int value) {

		menuItem = value;

		switch (value) {
		case 1:
			break;

		case 2:
			break;

		case 3:
		default:
			break;
		}
	}

	private class PopupMenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			int iChoosed = menuItem;
			if (action.equalsIgnoreCase("item")) {
				iChoosed = 1;
			}
			if (iChoosed != menuItem) {
				menuItem = iChoosed;
				menuChanged = true;
			}
		}
	}
	
	protected void SpecialKeys() {
		super.SpecialKeys();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Basic game = new Basic();

		// Creating popup menu
		ActionListener listener = game.new PopupMenuActionListener();
		JMenuItem menuItem;
		JPopupMenu menu = new JPopupMenu();
		menuItem = new JMenuItem("item");
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		game.addPopup(menu);

		game.start(800, 600);
	}

}
