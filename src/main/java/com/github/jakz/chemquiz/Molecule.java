package com.github.jakz.chemquiz;

import java.io.IOException;

import org.openscience.cdk.Element;
import org.openscience.cdk.config.Elements;
import org.openscience.cdk.config.IsotopeFactory;
import org.openscience.cdk.config.Isotopes;
import org.openscience.cdk.interfaces.IElement;

public class Molecule
{
  public final String iupac;
  public final String stock;
  public final String traditional[];
  
  public final String formula;
  public final String smiles;
  
  private static char subscript(int digit)
  {
    final String subscripts = "₀₁₂₃₄₅₆₇₈₉";
    return subscripts.charAt(digit);
  }
  
  public Molecule(String formula, String smiles, String iupac, String stock, String[] traditional)
  {
    this.formula = formula;
    this.smiles = smiles;
    this.iupac = iupac;
    this.stock = stock;
    this.traditional = traditional;
  }
  
  public Molecule of(String formula, String iupac, String stock, String[] traditional)
  {
    return new Molecule(formula, "", iupac, stock, traditional);
  }
  
  public static Molecule of(String formula, String iupac, String stock, String[] traditional, String smiles)
  {
    return new Molecule(formula, smiles, iupac, stock, traditional);
  }
  
  public static Molecule of(String formula, String iupac, String stock, String traditional, String smiles)
  {
    return new Molecule(formula, smiles, iupac, stock, new String[] { traditional });
  }
  
  public static Molecule of(String iupac, String stock, String traditional, Object... formula)
  {
    return of(iupac, "", stock, traditional, formula);
  }
  
  public static Molecule of(String iupac, String traditional, Object... formula)
  {
    return of(iupac, "", "", traditional, formula);
  }
  
  public static Molecule of(String iupac, String stock, String traditional, String smiles, Object... formula)
  {
    StringBuilder buffer = new StringBuilder();
    
    for (Object o : formula)
    {
      if (o instanceof IElement)
        buffer.append(((IElement)o).getSymbol());
      else if (o instanceof Integer)
      {
        if (((Integer)o) != 1)
          buffer.append(subscript((Integer)o));
      }
      else
        buffer.append(o);
    }
     
    return new Molecule(buffer.toString(), smiles, iupac, stock, new String[] { traditional });
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

  public static final Molecule Cl2O7 = Molecule.of("Eptossido di dicloro",  "Ossido di cloro(VII)", "Anidride Perclorica", "O=Cl(=O)(=O)OCl(=O)(=O)=O", Cl, 2, O, 7);
  
  public static final Molecule CH3COOH = Molecule.of("Acido etanoico", "", "Acido acetico", "CC(=O)O", C, H, 3, C, O, O, H);

  public static final Molecule NaH = Molecule.of("Idruro di sodio", "", "", "[H-].[Na+]", Na, H);
  
  public final static Molecule[] molecules = {
    
    // ossidi cloro
    Cl2O7,
    Molecule.of("Pentossido di dicloro", "Ossido di cloro(V)", "Anidride Clorica", Cl, 2, O, 5),
    Molecule.of("Triossido di dicloro", "Ossido di cloro(III)", "Anidride Clorosa", Cl, 2, O, 3),
    Molecule.of("Monossido di dicloro", "Ossido di cloro(I)", "Anidride Ipoclorosa", Cl, 2, O, 1),
    
    // ossidi rame
    Molecule.of("Ossido di monorame" , "Ossido di rame(II)", "Ossido rameico", Cu, 1, O, 1),
    Molecule.of("Ossido di dirame" , "Ossido di rame(I)", "Ossido rameoso", Cu, 2, O, 1),
    
    // sali rame
    Molecule.of("Cloruro di rame", "Cloruro di rame(I)", "Cloruro rameoso", Cu, 1, Cl, 1),
    Molecule.of("Dicloruro di rame", "Cloruro di rame(II)", "Cloruro rameico", Cu, 1, Cl, 2),

    // idracidi H + Alogeno
    Molecule.of("Cloruro di idrogeno", "Acido Cloridrico" , H, Cl),
    
    // idruri H + metallo
    NaH,
    
    // idrossidi metallo + OH
    Molecule.of("Idrossido di sodio", "Idrossido di sodio", "Idrossido di sodio", Na, 1, O, 1, H, 1),
    //Compound.of("Triidrossido di alluminio", "Idrossido di alluminio", "Idrossido di alluminio", Al),
    
    // acidi organici
    CH3COOH,
  };
  

}
