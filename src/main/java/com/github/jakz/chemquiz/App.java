package com.github.jakz.chemquiz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.depict.DepictionGenerator;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.renderer.color.IAtomColorer;
import org.openscience.cdk.smiles.SmilesParser;

import com.pixbits.lib.ui.UIUtils;
import com.pixbits.lib.ui.WrapperFrame;

import net.guha.util.cdk.Misc;
import net.guha.util.cdk.Renderer2DPanel;

/**
 * Hello world!
 *
 */
public class App 
{
  public static void mainz( String[] args )
  {
    for (Molecule molecule : Molecule.molecules)
      System.out.println("Generated "+molecule.formula);
  }
    

  public static void main(String[] args) throws Exception
  {    
    WrapperFrame<MoleculePanel> frame = UIUtils.buildFrame(new MoleculePanel(), "Molecule");
    frame.centerOnScreen();
    frame.exitOnClose();
    frame.panel().refresh(Molecule.Cl2O7);
    frame.setVisible(true);
  }
  
  static class MyAtomColorer implements IAtomColorer
  {
    @Override
    public Color getAtomColor(IAtom atom)
    {
      return getAtomColor(atom, Color.BLACK);
      
    }

    @Override
    public Color getAtomColor(IAtom atom, Color color)
    {
      String symbol = atom.getSymbol();
      
      switch (symbol)
      {
        case "C": return Color.BLACK;
        case "Cl": return new Color(0, 228, 54);
        case "O": return new Color(255, 0, 77);
        case "Na": return new Color(157, 94, 234);
        case "H": return Color.LIGHT_GRAY;
        default: return color;
      }
    }
    
  }
    
  static class MoleculePanel extends JPanel
  {
    private final JLabel icon;
    private final SmilesParser smilesParser = new SmilesParser(DefaultChemObjectBuilder.getInstance());

    
    MoleculePanel()
    {
      setLayout(new BorderLayout());
      
      icon = new JLabel();
      icon.setPreferredSize(new Dimension(300,300));
      
      add(icon, BorderLayout.CENTER);
    }
    
    void refresh(Molecule molecule) throws CDKException
    {
      IAtomContainer mol = smilesParser.parseSmiles(molecule.smiles);
      mol.setTitle(molecule.iupac);
      
      DepictionGenerator dptGen = new DepictionGenerator();
      Image image = dptGen
        .withSize(200, 200)
        .withAtomColors(new MyAtomColorer())
        .withCarbonSymbols()
        .withTerminalCarbons()
        //.withMolTitle()
        .withFillToFit()
        .withBackgroundColor(new java.awt.Color(214,217,223))
        .depict(mol).toImg();
      
      icon.setIcon(new ImageIcon(image));
    }
  }
}
