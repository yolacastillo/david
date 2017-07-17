package org;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.lsmp.djep.djep.DJep;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

public class Deriva extends JPanel {

	
	private static final long serialVersionUID = 1L;

	private static JFrame frame;
	
	private static JLabel funcionLbl;
    private static JLabel derivadaLbl;
 
    
    private static String funcionString = "Funcion: ";
    private static String derivadaString = "Derivada: ";
   
    
    private static JTextField funcionTxt;
    private static JTextField derivadaTxt;
    
   private static JButton derivaBtn;
   private static JButton limpiarBtn;
   

    public Deriva() {
        super(new BorderLayout());
       
        funcionLbl = new JLabel(funcionString);
        derivadaLbl = new JLabel(derivadaString);
     
       
        funcionTxt = new JTextField();       
        funcionTxt.setColumns(20);
       
        derivadaTxt = new JTextField();        
        derivadaTxt.setColumns(20);
 
        funcionLbl.setLabelFor(funcionTxt);
        derivadaLbl.setLabelFor(derivadaTxt);
        
        derivaBtn = new JButton("Calcular");
        limpiarBtn = new JButton("Limpiar");
       
        JPanel labelPane = new JPanel(new GridLayout(0,2));
        labelPane.add(funcionLbl);
        labelPane.add(funcionTxt);
        labelPane.add(derivadaLbl);
        labelPane.add(derivadaTxt);
       
      
        JPanel fieldPane = new JPanel(new GridLayout(0,2));
        
        labelPane.add(limpiarBtn);
        labelPane.add(derivaBtn);
        
     
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        add(labelPane, BorderLayout.NORTH);
        add(fieldPane, BorderLayout.CENTER);
               
        limpiarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {                
            	//closeFrame();
            	limpiarTxt();
            }
        });
                
        derivaBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calcula();
            }
        });
    }
    
    
    public void limpiarTxt() {
    	funcionTxt.setText("");
    	derivadaTxt.setText("");    	
    }
    
    public void calcula() {    	
    	String funcion = funcionTxt.getText();
    	String derivada = derivar(funcion).toString();
    	derivadaTxt.setText(derivada);
    }
    
    public void closeFrame(){
    	frame.setVisible(false);
    	frame.dispose();
    }
   
    
    private static void createAndShowGUI() {
        frame = new JFrame("Calcula Derivada");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        frame.add(new Deriva());

        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
       
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {       
            UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }

    
    public String derivar(String funcion) {
    	String derivada = "";
    	
    	DJep derivar =  new DJep();
    	
    	derivar.addStandardFunctions();// seno cosenos tangentes
    	
    	derivar.addStandardConstants(); //euler y pi
    	derivar.addComplex();
    	derivar.setAllowUndeclared(true);    	
    	derivar.setAllowAssignment(true);
    	derivar.setImplicitMul(true);//reglas multiplicacion, suma resta
    	
    	derivar.addStandardDiffRules();
    	
    	try {
    		Node node = derivar.parse(funcion);
    		Node diff = derivar.differentiate(node, "x"); //respento a variable
    		Node sim = derivar.simplify(diff);
    		
    		derivada = derivar.toString(sim);
    		
    	} catch(ParseException e) {
    		e.printStackTrace();
    		System.out.println(e.getMessage());
    	}
    	
    	
    	
    	return derivada;
    }
   
}
