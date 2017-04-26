package handlers;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.List;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;

import parser.ArtifactLibrary;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class librarywindow {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	/*public static void main(String[] args) {
		try {
			librarywindow window = new librarywindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Open the window.
	 */
	public void open(ArtifactLibrary lib) {
		Display display = Display.getDefault();
		createContents(lib);
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
	protected void createContents(ArtifactLibrary lib) {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		shell.setLayout(null);
		
		List LibraryList = new List(shell, SWT.BORDER);
		LibraryList.setBounds(10, 51, 426, 191);
		
		//add stuff to list from library
		int i=0;
		Iterator it = lib.getDesignReqLink().map.entrySet().iterator();
		
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        LibraryList.add(pair.getKey().toString()+" "+pair.getValue().toString(), i++);
	        it.remove();
	    }

	    
		
		Label titleLabel = new Label(shell, SWT.NONE);
		titleLabel.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 20, SWT.NORMAL));
		titleLabel.setBounds(143, 10, 121, 24);
		titleLabel.setText("LibraryView");
		
		Button btnViewedit = new Button(shell, SWT.NONE);
		btnViewedit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = LibraryList.getSelectionIndex();
				String item= LibraryList.getItem(index);
				String delimeters = "[ ,]+";
				item = item.replace("[", "");
				item = item.replace("]", "");
				String [] items= item.split(delimeters);
				crudwindow w1 = new crudwindow();
				//w1.createContents(lib,items);
				//w1.open(lib);
				//w1.txtDesignName.setText(items[0]);
				w1.open(lib,items);
				
			}
		});
		btnViewedit.setBounds(10, 248, 95, 28);
		btnViewedit.setText("View/Edit");
		
		Button btnCreateNew = new Button(shell, SWT.NONE);
		btnCreateNew.setBounds(131, 248, 95, 28);
		btnCreateNew.setText("Create New");

	}
}
