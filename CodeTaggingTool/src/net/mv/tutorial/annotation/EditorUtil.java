package net.mv.tutorial.annotation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

public class EditorUtil {

	public static void executeAction(final ArtifactInfo info, String DesignChoice, String Comments) {
		String project = info.getAttribute(AnnotationConstants.PROJECT);
		final String line = info.getAttribute(AnnotationConstants.LINE);
		String file = info.getAttribute(AnnotationConstants.FILE);

		IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IProject myWebProject = myWorkspaceRoot.getProject(project);

		String sourceFolder = "src";

		file = file.replace(".java", "");
		final String fileName = file.substring(file.lastIndexOf(".") + 1, file.length());
		String packageName = project.replace('.', '/');

		final IFile javaFile = myWebProject
				.getFile(sourceFolder + "/" /*+ package + "/"*/ + file.replace('.', '/') + ".java");

		if (!javaFile.exists()) {
			System.err.println("Error  - File not found " + javaFile.getRawLocation().toOSString());
			return;
		}

		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {

				try {
					IEditorPart editor = open(javaFile);
					highlight(editor, javaFile, line, info, DesignChoice, Comments);
					Display.getCurrent().getActiveShell().forceActive();

				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	

	protected static IEditorPart open(IFile javaFile) throws PartInitException {

		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = IDE.openEditor(page, javaFile);
		return editor;
	}

	protected static void highlight(IEditorPart editor, IFile javaFile, String toHighlight, ArtifactInfo info, String DesignChoice, String Comments)
			throws JavaModelException, CoreException {

		AnnotationHelper helper = new AnnotationHelper((CompilationUnitEditor) editor, javaFile, DesignChoice, Comments);
		helper.parse();
		helper.highlightMethod(toHighlight, info);
	}

}
