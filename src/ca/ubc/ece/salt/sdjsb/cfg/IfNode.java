package ca.ubc.ece.salt.sdjsb.cfg;

import org.mozilla.javascript.ast.AstNode;

/**
 * A CFGNode that has two exit edges: one for the true condition and one for
 * the false condition.
 * @author qhanam
 */
public class IfNode extends CFGNode {
	
	private CFGNode trueBranch;
	private CFGNode falseBranch;
	public CFGNode mergeNode;
	
	public IfNode(AstNode statement) {
		super(statement);
	}
	
	public void setTrueBranch(CFGNode trueBranch) {
		this.trueBranch = trueBranch;
	}
	
	public CFGNode getTrueBranch() {
		return this.trueBranch;
	}

	public void setFalseBranch(CFGNode falseBranch) {
		this.falseBranch = falseBranch;
	}
	
	public CFGNode getFalseBranch() {
		return this.falseBranch;
	}
	
	@Override
	public void mergeInto(CFGNode nextNode) {
		
		/* If either of the branches is null, there is no subgraph for that
		 * path, so we merge this node directly into the next node. */
		
		this.mergeNode = nextNode;
		
		if(this.trueBranch == null) {
			this.trueBranch = nextNode;
		}

		if(this.falseBranch == null) {
			this.falseBranch = nextNode;
		}
		
	}

	@Override
	public String printSubGraph(CFGNode mergeNode) {

        String s;
        
        if(this.getFalseBranch() != this.mergeNode && this.getTrueBranch() != this.mergeNode) {
            /* There is a then branch and an else branch. */
            s = this.toString() + "?{" + this.getTrueBranch().printSubGraph(this.mergeNode) + ":" + this.getFalseBranch().printSubGraph(this.mergeNode) + "}";
        }
        else if(this.getFalseBranch() == this.mergeNode && this.getTrueBranch() != this.mergeNode) {
            /* There is a then branch but no else branch. */
            s = this.toString() + "?{" + this.getTrueBranch().printSubGraph(this.mergeNode) + "}";
        }
        else if(this.getFalseBranch() != this.mergeNode && this.getTrueBranch() == this.mergeNode) {
            /* There is an else branch but no then branch. */
            s = this.toString() + "?{" + this.getTrueBranch().printSubGraph(this.mergeNode) + "}";
        }
        else {
            s = "ERROR";
        }

        if(mergeNode == this.mergeNode) {
            /* We are not at the bottom level of the merge. */
            return s;
        } 

        /* We are at the bottom level of the merge. */
        String subGraph = this.mergeNode.printSubGraph(mergeNode);
        if(subGraph.charAt(0) == '}') {
            return s + subGraph;
        }
        return s + "->" + subGraph;

	}

}
