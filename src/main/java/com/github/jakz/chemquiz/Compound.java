package com.github.jakz.chemquiz;

public class Compound
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
  
  public Compound(String formula, String smiles, String iupac, String stock, String[] traditional)
  {
    this.formula = formula;
    this.smiles = smiles;
    this.iupac = iupac;
    this.stock = stock;
    this.traditional = traditional;
  }
  
  public Compound of(String formula, String iupac, String stock, String[] traditional)
  {
    return new Compound(formula, "", iupac, stock, traditional);
  }
  
  public static Compound of(String formula, String iupac, String stock, String[] traditional, String smiles)
  {
    return new Compound(formula, smiles, iupac, stock, traditional);
  }
  
  public static Compound of(String formula, String iupac, String stock, String traditional, String smiles)
  {
    return new Compound(formula, smiles, iupac, stock, new String[] { traditional });
  }
  
  public static Compound of(String iupac, String stock, String traditional, Object... formula)
  {
    return of(iupac, "", stock, traditional, formula);
  }
  
  public static Compound of(String iupac, String traditional, Object... formula)
  {
    return of(iupac, "", "", traditional, formula);
  }
  
  public static Compound of(String iupac, String stock, String traditional, String smiles, Object... formula)
  {
    StringBuilder buffer = new StringBuilder();
    
    for (Object o : formula)
    {
      if (o instanceof Element)
        buffer.append(((Element)o).symbol);
      else if (o instanceof Integer)
      {
        if (((Integer)o) != 1)
          buffer.append(subscript((Integer)o));
      }
      else
        buffer.append(o);
    }
    
    
    return new Compound(buffer.toString(), smiles, iupac, stock, new String[] { traditional });
  }
  
  public final static Element H = Element.Hydrogen;
  public final static Element O = Element.Oxygen;
  public final static Element Cl = Element.Chlorine;
  public final static Element Cu = Element.Copper;
  public final static Element Na = Element.Sodium;

  
  public final static Compound[] compounds = {
    
    // ossidi cloro
    Compound.of("Eptossido di dicloro",  "Ossido di cloro(VII)", "Anidride Perclorica", "O=Cl(=O)(=O)OCl(=O)(=O)=O", Cl, 2, O, 7),
    Compound.of("Pentossido di dicloro", "Ossido di cloro(V)", "Anidride Clorica", Cl, 2, O, 5),
    Compound.of("Triossido di dicloro", "Ossido di cloro(III)", "Anidride Clorosa", Cl, 2, O, 3),
    Compound.of("Monossido di dicloro", "Ossido di cloro(I)", "Anidride Ipoclorosa", Cl, 2, O, 1),
    
    // ossidi rame
    Compound.of("Ossido di monorame" , "Ossido di rame(II)", "Ossido rameico", Cu, 1, O, 1),
    Compound.of("Ossido di dirame" , "Ossido di rame(I)", "Ossido rameoso", Cu, 2, O, 1),
    
    // sali rame
    Compound.of("Cloruro di rame", "Cloruro di rame(I)", "Cloruro rameoso", Cu, 1, Cl, 1),
    Compound.of("Dicloruro di rame", "Cloruro di rame(II)", "Cloruro rameico", Cu, 1, Cl, 2),

    // idracidi H + Alogeno
    Compound.of("Cloruro di idrogeno", "Acido Cloridrico" , H, Cl),
    
    // idruri H + metallo
    Compound.of("Idruro di sodio", "", "[H-].[Na+]", Na, H),
    
    // idrossidi metallo + OH
    Compound.of("Idrossido di sodio", "Idrossido di sodio", "Idrossido di sodio", Na, 1, O, 1, H, 1),
    //Compound.of("Triidrossido di alluminio", "Idrossido di alluminio", "Idrossido di alluminio", Al),
  };
}
