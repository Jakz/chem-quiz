package com.github.jakz.chemquiz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.util.Arrays;

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
    WrapperFrame<MoleculeTable> frame = UIUtils.buildFrame(new MoleculeTable(new Mediator()), "Molecules");
    frame.centerOnScreen();
    frame.exitOnClose();
    frame.panel().refresh(Arrays.asList(Molecule.molecules));
    frame.setVisible(true);
  }
}
