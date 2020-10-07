package com.github.jakz.chemquiz;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.depict.DepictionGenerator;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.renderer.color.IAtomColorer;
import org.openscience.cdk.smiles.SmilesParser;

public class IconCache extends com.pixbits.lib.util.IconCache<Molecule>
{
  private static final SmilesParser smilesParser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
  
  
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
        case "N": return new Color(0, 59, 209);
        case "H": return Color.LIGHT_GRAY;
        case "Cu": return new Color(182, 132, 85);
        default: return color;
      }
    }  
  }

  public IconCache()
  {
    super(molecule -> {
      try
      {
        DepictionGenerator dptGen = new DepictionGenerator();
        
        Image image = dptGen
          .withSize(200, 200)
          .withAtomColors(new MyAtomColorer())
          .withCarbonSymbols()
          //.withAromaticDisplay()
          .withTerminalCarbons()
          .withFillToFit()
          //.withBackgroundColor(new java.awt.Color(214,217,223))
          .depict(molecule.data()).toImg();
        
        return new ImageIcon(image);
      } 
      catch (CDKException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
      return null;
    });
  }
}
