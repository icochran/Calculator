// Postfix Calculator Applet
//
// CS 201 HW 8  -  Skeleton
// Issy Cochran

// This was a homework assignment I got full credit on
// This function as a type of calculator with some differences that I can not remember how to describe 
// them

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*; // for Stack

public class Calc extends Applet implements ActionListener {

    private static final long serialVersionUID = 1L; // to avoid Eclipse warning
    
    // instance variables
    
    protected Label result;         // label used to show result
    protected Stack<Integer> stack; // stack used for calculations
    protected int current;          // current number being entered
    protected boolean entered;      // if current number has been entered, show number in red

    // local color constants
    static final Color black = Color.black;
    static final Color white = Color.white;
    static final Color red = Color.red;
    static final Color blue = Color.blue;
    static final Color yellow = Color.yellow;
    static final Color orange = Color.orange;
    static final Color lblue = new Color(200, 220, 255);
    static final Color dred = new Color(160, 0, 100);
    static final Color dgreen = new Color(0, 120, 90);
    static final Color dblue = Color.blue.darker();

    public void init() {
    	
        this.setFont(new Font("TimesRoman", Font.BOLD, 28));
        this.setBackground(blue);
        current = 0;
        stack = new Stack<Integer>();
       
        Panel p2 = new Panel(new GridLayout(1, 2, 3, 0)); // bottom part 
        p2.add(CButton("Enter", dblue, lblue));           // of calculator
        p2.add(CButton("Clear", dblue, lblue));
        
        Panel p3 = new Panel(new BorderLayout());                    // upper part 
        result = new Label(Integer.toString(current), Label.RIGHT);  // of calculator
        result.setForeground(dgreen);                                // starts here
        result.setBackground(white);
        result.setFont(new Font("TimesRoman", Font.BOLD, 28));
        p3.setFont(new Font("TimesRoman", Font.BOLD, 12));
        p3.add("North", new Label());
        p3.add("South", new Label());
        p3.add("East", new Label());
        p3.add("West", new Label());
        p3.add("Center", result);              

        this.setLayout(new BorderLayout()); // put all three panels created to make 
        this.add("South", p2);              // the calculator. 
        this.add("North", p3);
        this.add("Center", P1()); // call helper function to make center panel of calc
    }
    
    // Use helper function CButton to make this helper function that sets up center panel
    protected Panel P1() {
    	Panel p1 = new Panel(new GridLayout(4, 1, 3, 3));
        p1.add(CButton("7", dgreen, yellow));
        p1.add(CButton("8", dgreen, yellow));
        p1.add(CButton("9", dgreen, yellow));
        p1.add(CButton("+", dblue, orange));
        p1.add(CButton("4", dgreen, yellow));
        p1.add(CButton("5", dgreen, yellow));
        p1.add(CButton("6", dgreen, yellow));
        p1.add(CButton("-", dblue, orange));
        p1.add(CButton("1", dgreen, yellow));
        p1.add(CButton("2", dgreen, yellow));
        p1.add(CButton("3", dgreen, yellow));
        p1.add(CButton("*", dblue, orange));
        p1.add(CButton("0", dgreen, yellow));
        p1.add(CButton("(-)", dred, yellow));
        p1.add(CButton("Pop", dred, yellow));
        p1.add(CButton("/", dblue, orange));
        return p1;
    }

    // create a colored button
    protected Button CButton(String s, Color fg, Color bg) {
        Button b = new Button(s);
        b.setBackground(bg);
        b.setForeground(fg);
        b.addActionListener(this);
        return b;
    }

    // handle button clicks
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Button) {
            String label = ((Button)e.getSource()).getLabel();
            if (label.equals("Enter")) {
            	enter();
            }
            else if (label.equals("Clear")) {
            	result.setForeground(dgreen);
            	clear();
            }
            else if (label.equals("(-)")) {
            	negative();
            }
            else if (label.equals("Pop")) {
            	popButton();
            }
            else if (label.equals("+"))
                add();
            else if (label.equals("-"))
                sub();
            else if (label.equals("*"))
                mult();
            else if (label.equals("/")) {
            	divide();
            }
            else {     // number button
            	result.setForeground(dgreen);
                int n = Integer.parseInt(label);
                number(n);
            }
        }
    }
    
    // when enter is pressed add the current value to stack, show it, and set current = 0 
    protected void enter() {
    	result.setForeground(red);
    	if (entered == true) {
    		show(current);
    	}
    	entered = true;
    	stack.add(current);
    	current = 0;
    }
    
    // Helper function for the operation buttons that handles when only one number is in
    // the stack and computes the value to be shown then either uses show() or 
    // another helper function to add the value to stack and show that value
    protected void operation(String op) {
    	int value;
    	if (stack.empty()) {
    		enter();
    	}
    	else {
    		int second = stack.pop();
    	    // handles when only one value in stack
    		if (stack.empty()) {
    			if (op.equals("+")) {
    				stack.push(second);
    			}
    			else if (op.equals("-")) {
    				current = second *-1;
    				stack.push(current);
    				show(stack.peek());
    				current = 0;
    			}
    			else if (op.equals("*")) {
    				stack.push(0);
    				show(stack.peek());
    			}
    			else {
    				stack.push(0);
    				show(stack.peek());
    			}
    		}
    	    // handles the other cases
    		else {
    			if (op.equals("+")) {
    				value = second + stack.pop();
    				displayOperatedValue(value);
    			}
    			else if (op.equals("-")) {
    				value = stack.pop() - second;
    				displayOperatedValue(value);
    			}
    			else if (op.equals("*")) {
    				value = stack.pop() * second;
    				displayOperatedValue(value);
    			}
    			else {
    				value = stack.pop() / second;
    				displayOperatedValue(value);
    			}
    		}
    	}
    }
    
    // helper function to add the given value to stack and show that value
    protected void displayOperatedValue(int value) {
    	stack.push(value);
    	current = value;
    	show(current);
    	current = 0;
    }
    
    // handle add button
    protected void add() {
    	ifNotEntered();
    	operation("+");
    }
    
    // handle subtraction button
    protected void sub() {
    	ifNotEntered();
    	operation("-");
    }
    
    // handle multiplication button
    protected void mult() {
    	ifNotEntered();
    	operation("*");
    }
    
    // handle divide button
    protected void divide() {
    	ifNotEntered();
    	operation("/");
    }
    
    // handles clear button by emptying the stack, setting current = 0 and showing it
    protected void clear() {
    	while (!stack.empty()) {
    		stack.pop();
    	}
    	current = 0;
    	show(current);
    }
    
    // handle negative button
    protected void negative() {
    	if (!entered) {
    		current = current * -1;
    		show(current);
    		enter();
    	}
    	else if (stack.empty()) {
    		enter();
    	}
    	else {
    		int value = stack.pop() * -1;
    		displayOperatedValue(value);
    	}
    }
    
    // handle pop button which removes last number pressed and 
    // shows the number before it
    protected void popButton() {
    	ifNotEntered();
    	stackEmptyButton();
    	if (!stack.empty()) {
    		current = stack.peek();
    	}
    	show(current);
    }
    
    // helper function for popButton
    protected void stackEmptyButton() {
    	if (stack.empty()) {
    		enter();
    	}
    	else {
    		stack.pop();
    		if (stack.empty()) {
    			enter();
        	}
    	}
    }
    
    // helper method that checks if enter has been pressed and calls enter() if not
    protected void ifNotEntered() {
    	if (!entered)
    		enter();
    }
    
    // display number n in result label
    protected void show(int n) {
        result.setText(Integer.toString(n));
    }

    // handle number buttons
    protected void number(int n) {
    	entered = false;
    	current = 10 * current + n;
        show(current);
    }
}

