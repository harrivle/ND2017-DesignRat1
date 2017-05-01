package handlers;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.List;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;

import parser.ArtifactLibrary;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class librarywindow implements Observer{
	
	public librarywindow(ArtifactLibrary lib1){
		lib = lib1;
	}
	ArtifactLibrary lib;
	librarywindow o = this;
	protected Shell shell;
	List LibraryList;
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
	public void open(){//ArtifactLibrary lib) {
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
	protected void createContents(){//ArtifactLibrary lib) {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		shell.setLayout(null);
		
		LibraryList = new List(shell, SWT.BORDER | SWT.V_SCROLL);
		LibraryList.setBounds(10, 51, 426, 191);
		
		//add stuff to list from library
		//int i=0;
		//Iterator it = lib.getDesignReqLink().map.entrySet().iterator();
		
	    /*while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        LibraryList.add(pair.getKey().toString()+" "+pair.getValue().toString(), i++);
	        it.remove();
	    }*/
	    
	    for(Entry<String, HashSet<String>> R : lib.getDesignReqLink().map.entrySet()) {
	    	LibraryList.add(R.getKey().toString()+" "+R.getValue().toString());// + ": " + R.getValue().description);
	    }
    
		
		Label titleLabel = new Label(shell, SWT.CENTER);
		titleLabel.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 20, SWT.NORMAL));
		titleLabel.setBounds(104, 10, 227, 24);
		titleLabel.setText("Design Decision Library");
		
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
				w1.addObserver(o);
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

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		//if(o instanceof reqwindow){
			//System.out.println("reqwindow change");
		//}
		if(o instanceof crudwindow){
			//System.out.println("crudwindow changed");
			LibraryList.removeAll();
			//ArtifactLibrary libNew = new ArtifactLibrary();
			for(Entry<String, HashSet<String>> R : lib.getDesignReqLink().map.entrySet()) {
		    	LibraryList.add(R.getKey().toString()+" "+R.getValue().toString());// + ": " + R.getValue().description);
		    }
			
		}
		
	}
}
