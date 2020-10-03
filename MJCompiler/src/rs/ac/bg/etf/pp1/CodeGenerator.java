package rs.ac.bg.etf.pp1;

import java.util.Stack;

import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.mj.runtime.*;

public class CodeGenerator extends VisitorAdaptor {
	private static final int DOLLAR = 100;
	private int mainPc;
	private Obj lastFactorObj;
	
	Stack<Stack<Integer>> stackOperations = new Stack<>();
	Stack<Stack<Obj>> stackSimbols = new Stack<>();

	public int getMainPc() {
		return mainPc;
	}

	public void visit(MethodName methodName) {
		if ("main".equalsIgnoreCase(methodName.getMethodName())) {
			mainPc = Code.pc;
		}
		methodName.obj.setAdr(Code.pc);

		int nPars = methodName.obj.getLevel();
		int nLocals = methodName.obj.getLocalSymbols().size();

		Code.put(Code.enter);
		Code.put(nPars);
		Code.put(nLocals);

		/*
		 * SyntaxNode methodNode = methodName.getParent(); VarCounter varCnt = new
		 * VarCounter(); methodNode.traverseTopDown(varCnt); Code.put(Code.enter);
		 * Code.put(0); Code.put(varCnt.getCount());
		 */
	}

	public void visit(MethodDecl methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(PrintStmt printStmt) {
		if (printStmt.getNumbersListOptional() instanceof NumbersListExists) {
			NumbersListExists param = (NumbersListExists) printStmt.getNumbersListOptional();
			Code.loadConst(param.getValue());
		}
		else {
			Code.loadConst(5);
		}
		if (printStmt.getExpr().struct == Tab.charType) {
			Code.put(Code.bprint);
		} else {
			Code.put(Code.print); // size of int in bytes
		}
	}

	public void visit(ReadStmt readStmt) {
		Obj desigObj = readStmt.getDesignator().obj;
		if (desigObj.getType().equals(Tab.charType)) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
		}
		Code.store(desigObj);
	}

	public void visit(Increment inc) {
		if (inc.getDesignator().obj.getKind() == Obj.Elem)
			Code.put(Code.dup2);
		Code.load(inc.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(inc.getDesignator().obj);
	}

	public void visit(Decrement dec) {
		if (dec.getDesignator().obj.getKind() == Obj.Elem)
			Code.put(Code.dup2);
		Code.load(dec.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(dec.getDesignator().obj);
	}

	public void visit(Const cnst) {
		Obj con = Tab.insert(Obj.Con, "$", cnst.struct);
		con.setLevel(0);
		con.setAdr(cnst.getValue());
		Code.load(con);
		lastFactorObj = null;
	}

	public void visit(Char chr) {
		Obj con = Tab.insert(Obj.Con, "$", chr.struct);
		con.setLevel(0);
		con.setAdr(chr.getValue());
		Code.load(con);
		lastFactorObj = null;
	}

	public void visit(Bool bool) {
		Obj con = Tab.insert(Obj.Con, "$", bool.struct);
		con.setLevel(0);
		con.setAdr(bool.getValue() ? 1 : 0);
		Code.load(con);
		lastFactorObj = null;
	}

	public void visit(ParenFactor parenFactor) {
		lastFactorObj = null;
	}
	
	public void visit(FactorArray factorArray) {
		lastFactorObj = null;
		Obj array = factorArray.getDesignator().obj;
		int index = factorArray.getValue();
		
		Code.load(array);
		Code.loadConst(index);	
		Code.put(Code.aload);
	
		Code.load(array);
		Code.load(array);
		Code.put(Code.arraylength);
		Code.loadConst(index);
		Code.put(Code.sub);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.put(Code.aload);
		
		Code.put(Code.add);
	}

	// creation of an array
	public void visit(NewFactor newFactor) {
		lastFactorObj = null;
		Code.put(Code.newarray);
		Code.put(newFactor.struct.getElemType().equals(Tab.charType) ? 0 : 1);
	}

	public void visit(DesigFactor des) {
		lastFactorObj = des.getDesignator().obj;
	}

	public void visit(PreArrIdxDummy preArrIdxDummy) {
		Obj arrObj = ((DesignatorArray) preArrIdxDummy.getParent()).getDesignator().obj;
		Code.load(arrObj);
	}

	public void visit(AssignmentDesignator assignmentDesignator) {
		Code.store(assignmentDesignator.getDesignator().obj);
	}

	public void visit(SingleTermTarMinus negExpression) {
		if (lastFactorObj != null) {
			Code.load(lastFactorObj);
			lastFactorObj = null;
		}
		Code.put(Code.neg);
	}

	public void visit(FactorMulOp factorMulOp) {
		if (factorMulOp.getMulOp() instanceof MulOp_MulOpLeft) {
			if (lastFactorObj != null) {
				Code.load(lastFactorObj);
				lastFactorObj = null;
			}
			MulOp_MulOpLeft mulop = (MulOp_MulOpLeft) factorMulOp.getMulOp();
			if (mulop.getMulOpLeft() instanceof MulOp_Multiply)
				Code.put(Code.mul);
			else if (mulop.getMulOpLeft() instanceof MulOp_Divide)
				Code.put(Code.div);
			else 
				Code.put(Code.rem);
		}
	}
	
	public void visit(FactorAtOp factorAtOp) {
		if (lastFactorObj != null) {
			Code.load(lastFactorObj);
			lastFactorObj = null;
		}
		Code.put(Code.dup);
		Code.put(Code.mul);
	}

	public void visit(TermTarOp termTarOp) {
		if (lastFactorObj != null) {
			Code.load(lastFactorObj);
			lastFactorObj = null;
		}
		// a#b=2*a-a*b+a+1 2*3-3*1+3+1=7
		
		Code.put(Code.enter);
		Code.put(2);
		Code.put(2);

		Code.loadConst(2);
		Code.put(Code.load_n);
		Code.put(Code.mul);
		Code.put(Code.load_n);
		Code.put(Code.load_1);
		Code.put(Code.mul);
		Code.put(Code.sub);
		Code.put(Code.load_n);
		Code.put(Code.add);
		Code.loadConst(1);
		Code.put(Code.add);

		Code.put(Code.exit);
	}
	
	public void visit(Tar tar) {
		if (lastFactorObj != null) {
			Code.load(lastFactorObj);
			lastFactorObj = null;
		}
	}
	
	public void visit(Expr expr) {
		if (lastFactorObj != null) {
			Code.load(lastFactorObj);
			lastFactorObj = null;
		}
		Stack<Integer> operations = stackOperations.pop();
		Stack<Obj> simbols = stackSimbols.pop();
		while (!simbols.isEmpty()) {
			int opCode = operations.pop();
			if (opCode == DOLLAR) {
//				a $= b -->  a = 4*a + 3*b - 2;
				Code.put(Code.enter);
				Code.put(2);
				Code.put(2);

				Code.loadConst(4);
				Code.put(Code.load_n);
				Code.put(Code.mul);
				
				Code.loadConst(3);
				Code.put(Code.load_1);
				Code.put(Code.mul);
				Code.put(Code.add);
				
				Code.loadConst(2);
				Code.put(Code.sub);

				Code.put(Code.exit);
			} else
				Code.put(opCode);
			Obj simbol = simbols.pop();
			if (simbol.getKind() == Obj.Elem) {
				Code.put(Code.dup_x2);
			} else {
				Code.put(Code.dup);
			}
			Code.store(simbol);
		}
		
	}
	
	public void visit(Empty empty) {
		stackOperations.push(new Stack<>());
		stackSimbols.push(new Stack<>());
	}

	public void visit(TermAddOp termAddOp) {
		if (termAddOp.getAddOp() instanceof AddOp_AddOpLeft) {
			if (lastFactorObj != null) {
				Code.load(lastFactorObj);
				lastFactorObj = null;
			}
			AddOp_AddOpLeft addop = (AddOp_AddOpLeft) termAddOp.getAddOp();
			if (addop.getAddOpLeft() instanceof AddOp_Minus)
				Code.put(Code.sub);
			else
				Code.put(Code.add);
		}
	}

	public void visit(MethodCall methCall) {
		int offset = methCall.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);

	}

	public void visit(DesStmMethodCall callFromCode) {
		if (((MethodCall) callFromCode.getMethCall()).getDesignator().obj.getType() != Tab.noType)
			Code.put(Code.pop);
	}
	
	public void visit(AddOp_AddOpRight addOp_AddOpRight) {
		if (lastFactorObj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(lastFactorObj);
		stackSimbols.peek().push(lastFactorObj);
		lastFactorObj = null;
		if (addOp_AddOpRight.getAddOpRight() instanceof AddOp_PlusEqual) {
			stackOperations.peek().push(Code.add);
		} else if (addOp_AddOpRight.getAddOpRight() instanceof AddOp_DolarEqual) {
			stackOperations.peek().push(DOLLAR);
		}
		else {
			stackOperations.peek().push(Code.sub);
		}
	}
	
	public void visit(MulOp_MulOpRight mulOp_MulOpRight) {
		if (lastFactorObj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(lastFactorObj);
		stackSimbols.peek().push(lastFactorObj);
		lastFactorObj = null;
		if (mulOp_MulOpRight.getMulOpRight() instanceof MulOp_MultiplyEqual) {
			stackOperations.peek().push(Code.mul);
		} else if (mulOp_MulOpRight.getMulOpRight() instanceof MulOp_DivideEqual) {
			stackOperations.peek().push(Code.div);
		} else {
			stackOperations.peek().push(Code.rem);
		}
	}
	
	public void visit(AddOp_AddOpLeft addOp_AddOpLeft) {
		if (lastFactorObj != null) {
			Code.load(lastFactorObj);
			lastFactorObj = null;
		}
	}
	
	public void visit(MulOp_MulOpLeft mulOp_MulOpLeft) {
		if (lastFactorObj != null) {
			Code.load(lastFactorObj);
			lastFactorObj = null;
		}
	}
	
	public void visit(AssignOp_AddOpRight assignOp_AddOpRight) {
		Obj obj = ((AssignmentDesignator)assignOp_AddOpRight.getParent().getParent()).getDesignator().obj;
		if (obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(obj);
	}
	
	public void visit(AssignOp_MulOpRight assignOp_MulOpRight) {
		Obj obj = ((AssignmentDesignator)assignOp_MulOpRight.getParent().getParent()).getDesignator().obj;
		if (obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(obj);
	}
	
	public void visit(Assignment_Expr assignment_Expr) {
		if (assignment_Expr.getAssignOp() instanceof AssignOp_AddOpRight) {
			AddOpRight addOpRight = ((AssignOp_AddOpRight)assignment_Expr.getAssignOp()).getAddOpRight();
			if (addOpRight instanceof AddOp_PlusEqual) {
				Code.put(Code.add);
			} else if (addOpRight instanceof AddOp_DolarEqual) {
//				a $= b -->  a = 4*a + 3*b - 2;
				Code.put(Code.enter);
				Code.put(2);
				Code.put(2);

				Code.loadConst(4);
				Code.put(Code.load_n);
				Code.put(Code.mul);
				
				Code.loadConst(3);
				Code.put(Code.load_1);
				Code.put(Code.mul);
				Code.put(Code.add);
				
				Code.loadConst(2);
				Code.put(Code.sub);

				Code.put(Code.exit);
			}
			else {
				Code.put(Code.sub);
			}
		} else if (assignment_Expr.getAssignOp() instanceof AssignOp_MulOpRight) {
			MulOpRight mulOpRight = ((AssignOp_MulOpRight)assignment_Expr.getAssignOp()).getMulOpRight();
			if (mulOpRight instanceof MulOp_MultiplyEqual) {
				Code.put(Code.mul);
			} else if (mulOpRight instanceof MulOp_DivideEqual) {
				Code.put(Code.div);
			} else {
				Code.put(Code.rem);
			}
		}
	}	

}