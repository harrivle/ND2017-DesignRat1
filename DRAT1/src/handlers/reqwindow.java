package handlers;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

import parser.ArtifactLibrary;
import parser.Requirement;

import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.viewers.ListViewer;

public class reqwindow extends Observable {

	protected Shell shell;
	String[] saveList;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	/*
	 * public static void main(String[] args) { try { reqwindow window = new
	 * reqwindow(); window.open(); } catch (Exception e) { e.printStackTrace();
	 * } }
	 */

	/**
	 * Open the window.
	 */
	public void open(ArtifactLibrary lib, String designId) {
		Display display = Display.getDefault();
		createContents(lib, designId);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	protected void createContents(ArtifactLibrary lib, String designId) {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");

		Label lblReqwindow = new Label(shell, SWT.CENTER);
		lblReqwindow.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 20, SWT.NORMAL));
		lblReqwindow.setBounds(23, 10, 391, 24);
		lblReqwindow.setText("Select Requirements for " + designId);

		List wholeList = new List(shell, SWT.BORDER | SWT.V_SCROLL);
		wholeList.setBounds(23, 47, 142, 221);

		for (Entry<String, String> R : lib.getRequirements().map.entrySet()) {
			wholeList.add(R.getKey());// + ": " + R.getValue().description);
		}

		List list = new List(shell, SWT.BORDER | SWT.V_SCROLL);
		list.setBounds(262, 47, 152, 187);

		for (String str : lib.getReqIdList(designId)) {
			list.add(str);
		}

		Button moveRightButton = new Button(shell, SWT.CENTER);
		moveRightButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = wholeList.getSelectionIndex();
				list.add(wholeList.getItem(index));
			}
		});
		moveRightButton.setBounds(171, 82, 86, 28);
		moveRightButton.setText("----->");

		Button button = new Button(shell, SWT.NONE);
		button.setBounds(171, 174, 86, 28);
		button.setText("<------");

		Button btnSave = new Button(shell, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// on save, add items to set and send to library
				saveList = list.getItems();
				HashSet<String> saveSet = new HashSet<String>();
				for (String id : saveList)
					saveSet.add(id);
				lib.setReqIdList(designId, saveSet);
				
				String libraryFileName = "artifactLibrary.ser";
				FileOutputStream fos = null;
                ObjectOutputStream out = null;
                try {
                        fos = new FileOutputStream("src/articfacts/" + libraryFileName);
                        out = new ObjectOutputStream(fos);
                        out.writeObject(lib);

                        out.close();
                } catch (Exception ex) {
                        ex.printStackTrace();
                }
				setChanged();
				notifyObservers();
				shell.close();
			}
		});
		btnSave.setBounds(295, 240, 95, 28);
		btnSave.setText("Save");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index2 = list.getSelectionIndex();
				list.remove(list.getItem(index2));
			}
		});

	}
}
