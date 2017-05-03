package handlers;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.List;

import java.util.Observable;
import java.util.Observer;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import parser.ArtifactLibrary;

public class reqlist implements Observer{

	protected Shell shell;
	ArtifactLibrary lib;
	List list;// = new List(shell, SWT.BORDER);

	/**
	 * Open the window.
	 */
	public void open(ArtifactLibrary lib) {
		this.lib=lib;
		lib.addObserver(this);
		Display display = Display.getDefault();
		createContents();
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
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(499, 401);
		shell.setText("SWT Application");
		
		list = new List(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		list.setBounds(10, 41, 479, 328);
		updateContents();
		Label lblReqlist = new Label(shell, SWT.CENTER);
		lblReqlist.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 20, SWT.NORMAL));
		lblReqlist.setBounds(10, 10, 479, 25);
		lblReqlist.setText("Requirement Reference List");

	}
	
	public void updateContents() {
		for (Entry<String, String> R : lib.getRequirements().map.entrySet()) {
			list.add(R.getKey()+": "+R.getValue());
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if(o instanceof ArtifactLibrary){
			updateContents();
		}
	}
}