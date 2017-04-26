package net.mv.tutorial.annotation;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor;
import org.eclipse.jface.text.Position;

@SuppressWarnings("restriction")
public class AnnotationHelper {

	private final Map<String, Position> methodTable = new HashMap<>();
	private CompilationUnitEditor javaEditor;
	ICompilationUnit input;
	private IFile javaFile;
	String DesignChoice;
	String Comments;

	public AnnotationHelper(CompilationUnitEditor javaEditor, IFile javaFile, String designchoice, String comments) {
		this.javaEditor = javaEditor;
		this.javaFile = javaFile;
		input = (ICompilationUnit) javaEditor.getViewPartInput();
		DesignChoice = designchoice;
		Comments = comments;
	}

	public void parse() {
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(input); // set source
		parser.setResolveBindings(true); // we need bindings later

		ASTNode node = parser.createAST(new NullProgressMonitor());
		node.accept(new ASTVisitor() {
			@Override
			public boolean visit(MethodDeclaration node) {
				methodTable.put(node.getName().toString(), new Position(node.getStartPosition(), node.getLength()));
				return true;
			}
		});
	}

	public void highlightMethod(String methodName, ArtifactInfo info) throws JavaModelException, CoreException {
		for (IType tp : input.getAllTypes()) {
			for (IJavaElement elem : tp.getChildren()) {
				if (elem instanceof IMethod) {
					IMethod meth = (IMethod) elem;
					if (meth.getElementName().equals(methodName)) {
						javaEditor.setSelection(meth);
						Position position = methodTable.get(meth.getElementName());

						IMarker marker = MarkerFactory.createMethodMarker(javaFile, position, meth, "TEST", "DATA",
								Boolean.parseBoolean(info.getAttribute(AnnotationConstants.HASERROR)), DesignChoice, Comments);
						javaEditor.setSelection(tp);
						javaEditor.gotoMarker(marker);
					}
				}
			}
		}
	}

}
