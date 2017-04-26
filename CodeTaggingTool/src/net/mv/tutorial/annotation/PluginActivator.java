package net.mv.tutorial.annotation;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.osgi.framework.BundleContext;

public class PluginActivator extends Plugin {

	public PluginActivator() {
		// TODO Auto-generated constructor stub
		
		
	}
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		registerJavaOpenListener();
		
	}

	private  void registerJavaOpenListener() {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(new IPartListener() {
			
			@Override
			public void partOpened(IWorkbenchPart part) {
		
				if(part instanceof CompilationUnitEditor){
					CompilationUnitEditor javaEditor = (CompilationUnitEditor) part;
					String classname = javaEditor.getTitle();
					String file  = ((FileEditorInput) javaEditor.getEditorInput()).getFile().getRawLocation().toOSString();
					//do something here....
					System.out.println("JAVA File opened: "+ classname +" @"+file);
				}
				
				
			}
			
			@Override
			public void partDeactivated(IWorkbenchPart part) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void partClosed(IWorkbenchPart part) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void partBroughtToTop(IWorkbenchPart part) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void partActivated(IWorkbenchPart part) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
}
