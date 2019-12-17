package com.github.jakz.chemquiz;

import java.awt.Image;
import java.io.IOException;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.Element;
import org.openscience.cdk.config.Elements;
import org.openscience.cdk.config.IsotopeFactory;
import org.openscience.cdk.config.Isotopes;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IElement;
import org.openscience.cdk.smiles.SmilesParser;

public class Molecule
{
  private static final SmilesParser smilesParser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
  
  public final String iupac;
  public final String traditional[];
  
  public final String formula;
  public final String smiles;
  
  private IAtomContainer data;
  
  private static char subscript(int digit)
  {
    final String subscripts = "₀₁₂₃₄₅₆₇₈₉";
    return subscripts.charAt(digit);
  }
  
  private static char superscript(int digit)
  {
    final String superscripts = "⁰¹²³⁴⁵⁶⁷⁸⁹";
    return superscripts.charAt(digit);
  }
  
  public Molecule(String formula, String smiles, String iupac, String[] traditional)
  {
    this.formula = formula;
    this.smiles = smiles;
    this.iupac = iupac;
    this.traditional = traditional;
  }
  
  public IAtomContainer data()
  {
      try
      {
        if (data == null)
          data = smilesParser.parseSmiles(smiles);
      } 
      catch (InvalidSmilesException e)
      {
        e.printStackTrace();
      }
    
    return data;
  }

  public static Molecule of(String iupac, String traditional, Object... formula)
  {
    return of(iupac, "", "", traditional, formula);
  }
  
  public static Molecule of(String iupac, String traditional, String smiles, Object... formula)
  {
    return of(iupac, new String[] { traditional }, smiles, formula);
  }
  
  public static Molecule of(String iupac, String[] traditional, String smiles, Object... formula)
  {
    StringBuilder buffer = new StringBuilder();
    
    for (int i = 0; i < formula.length; ++i)
    {
      Object o = formula[i];
      if (o instanceof IElement)
        buffer.append(((IElement)o).getSymbol());
      else if (o.equals('+'))
        buffer.append('⁺');
      else if (o.equals('-'))
        buffer.append('⁻');
      else if (o instanceof Integer)
      {
        int v = (Integer)o;
        
        if (formula[i-1] instanceof Integer)
          buffer.append(superscript(v));
        else if (v != 1)
          buffer.append(subscript(v));
      }
      else
        buffer.append(o);
    }
     
    return new Molecule(buffer.toString(), smiles, iupac, traditional);
  }
  
  private static Isotopes factory;
  
  static
  {
    try
    {
      factory = Isotopes.getInstance();
    } catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
  
  
  public final static IElement H = factory.getElement("H");
  public final static IElement O = factory.getElement("O");
  public final static IElement Cl = factory.getElement("Cl");
  public final static IElement Cu = factory.getElement("Cu");
  public final static IElement Na = factory.getElement("Na");
  public final static IElement C = factory.getElement("C");
  public final static IElement N = factory.getElement("N");
  public final static IElement P = factory.getElement("P");

  public static final Molecule Cl2O7 = Molecule.of("Eptossido di dicloro", "Anidride Perclorica", "O=Cl(=O)(=O)OCl(=O)(=O)=O", Cl, 2, O, 7);
  
  //acidi organici
  public static final Molecule Metanoic_Acid = Molecule.of("Acido metanoico", "Acido formico", "C(=O)O", H, C, O, O, H);
  public static final Molecule CH3COOH = Molecule.of("Acido etanoico", "Acido acetico", "CC(=O)O", C, H, 3, C, O, O, H);
  public static final Molecule Butanoic_Acid = Molecule.of("Acido butanoico", "Acido butirrico", "CCCC(=O)O", C, H, 3, C, H, 2, C, H, 2, C, O, O, H);
  public static final Molecule Phtalic_Acid = Molecule.of("Acido 1,2-benzenedicarbossilico", "Acido ftalico", "C1=CC=C(C(=C1)C(=O)O)C(=O)O", C, 8, H, 6, O, 4);
  
  public static final Molecule Succinic_Acid = Molecule.of("Acido 1,4-butandioico", "Acido Succinico", "C(CC(=O)O)C(=O)O", C, 4, H, 6, O, 4);

  public static final Molecule Tartaric_Acid = Molecule.of("Acido 2R,3R-diidrossibutandioico", "Acido Tartarico", "C(C(C(=O)O)O)(C(=O)O)O", C, 4, H, 6, O, 6);

  
  public static final Molecule Aniline = Molecule.of("Anilina", new String[] { "Fenilammina", "Amminobenzene", "Benzenammina" }, "C1=CC=C(C=C1)N", C, 6, H, 7, N);
  
  public static final Molecule NaH = Molecule.of("Idruro di sodio", "", "[H-].[Na+]", Na, H);
  
  public static final Molecule Phosphate = Molecule.of("Fosfato", "", "[O-]P([O-])([O-])=O", P, 4, 3, '-');
  
  public final static Molecule[] molecules = {
    
    // ossidi cloro
    Cl2O7,
    Molecule.of("Pentossido di dicloro", "Anidride Clorica", "O=Cl(=O)OCl(=O)=O", Cl, 2, O, 5),
    Molecule.of("Triossido di dicloro", "Anidride Clorosa", "ClOCl(=O)=O", Cl, 2, O, 3),
    Molecule.of("Monossido di dicloro", "Anidride Ipoclorosa", "O(Cl)Cl", Cl, 2, O, 1),
    
    // ossidi rame
    Molecule.of("Ossido di monorame" , "Ossido rameico", "[O-2].[Cu+2]", Cu, 1, O, 1),
    Molecule.of("Ossido di dirame" , "Ossido rameoso", "[O-2].[Cu+].[Cu+]", Cu, 2, O, 1),
    
    // sali rame
    Molecule.of("Cloruro di rame", "Cloruro rameoso", "", Cu, 1, Cl, 1),
    Molecule.of("Dicloruro di rame", "Cloruro rameico", "", Cu, 1, Cl, 2),

    // idracidi H + Alogeno
    Molecule.of("Cloruro di idrogeno", "Acido Cloridrico", "", H, Cl),
    
    // idruri H + metallo
    NaH,
    
    // idrossidi metallo + OH
    Molecule.of("Idrossido di sodio", "Idrossido di sodio", "", Na, 1, O, 1, H, 1),
    //Compound.of("Triidrossido di alluminio", "Idrossido di alluminio", "Idrossido di alluminio", Al),
    
    // acidi organici
    Metanoic_Acid,
    CH3COOH,
    Butanoic_Acid,
    Phtalic_Acid,
    
    Succinic_Acid,
    Tartaric_Acid,
    
    Aniline,
    
    Phosphate,
  };
  

}
