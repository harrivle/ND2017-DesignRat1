package driver;

import handlers.codewindow;
import handlers.crudwindow;
import handlers.librarywindow;
import handlers.reqwindow;
import parser.ArtifactLibrary;

public class driver {
	public static void main(String[] args) {
		ArtifactLibrary lib = new ArtifactLibrary();

		try {
			librarywindow window = new librarywindow(lib);
			window.open();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * try { crudwindow window = new crudwindow(); window.open(lib); } catch
		 * (Exception e) { e.printStackTrace(); }
		 */
		/*
		 * try { reqwindow window = new reqwindow(); window.open(lib); } catch
		 * (Exception e) { e.printStackTrace(); } try { codewindow window = new
		 * codewindow(); window.open(); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
	}
}