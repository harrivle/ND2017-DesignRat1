package driver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import handlers.codewindow;
import handlers.crudwindow;
import handlers.librarywindow;
import handlers.reqwindow;
import parser.ArtifactLibrary;

public class driver {
	public static void main(String[] args) {
		ArtifactLibrary lib = new ArtifactLibrary();
		String libraryFileName = "artifactLibrary.ser";
		File f = new File("src/articfacts/" + libraryFileName);
		
		if (f.exists() && !f.isDirectory()) {
			FileInputStream fis = null;
			ObjectInputStream in = null;
			try {
				fis = new FileInputStream(libraryFileName);
				in = new ObjectInputStream(fis);
				lib = (ArtifactLibrary) in.readObject();
				in.close();
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		try {
			librarywindow window = new librarywindow(lib);
			window.open();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * try { crudwindow window = new crudwindow(); window.open(lib); } catch
		 * (Exception e) { e.printStackTrace(); } try { reqwindow window = new
		 * reqwindow(); window.open(lib); } catch (Exception e) {
		 * e.printStackTrace(); } try { codewindow window = new codewindow();
		 * window.open(); } catch (Exception e) { e.printStackTrace(); }
		 */
	}
}