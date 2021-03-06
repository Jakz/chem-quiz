package com.github.jakz.chemquiz;

import java.awt.Image;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
  
  public static Molecule ofb(String traditional, String smiles, Object... formula)
  {
    return of("", new String[] { traditional }, smiles, formula);
  }
  
  public static Molecule of(String iupac, String traditional, String smiles, Object... formula)
  {
    return of(iupac, new String[] { traditional }, smiles, formula);
  }
  
  public static Molecule of(String iupac, String[] traditional, String smiles, Object... formula)
  {
    StringBuilder buffer = new StringBuilder();
    
    if (formula.length > 0 && formula[0] instanceof String)
    {
      String s = (String)formula[0];

      for (int i = 0; i < s.length(); ++i )
      {
        char c = s.charAt(i);
        
        if (Character.isDigit(c))
          buffer.append(subscript(c - '0'));
        else if (Character.isLetter(c))
          buffer.append(Character.toUpperCase(c));
        else if (c == '+')
          buffer.append('⁺');
        else if (c == '-')
          buffer.append('⁻');         
      }
      
      return new Molecule(buffer.toString(), smiles, iupac, traditional);
    }
    else
    {
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
          {
            if (v > 10)
              buffer.append(subscript(v / 10));
            buffer.append(subscript(v % 10));
          }
        }
        else
          buffer.append(o);
      }
       
      return new Molecule(buffer.toString(), smiles, iupac, traditional);
    }
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
  
  public static void loadCSV(Path file) throws IOException
  {
    Files.lines(file).forEach(line -> {
        String[] fields = line.split(",");
        
        if (fields[0].equals("Smiles"))
          return;
        else
        {
          molecules.add(Molecule.ofb(fields[2], fields[0], fields[1])); 
        }
        
    });
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
  
  public static final Molecule Oxalic_Acid = Molecule.of("Acido etandioico", "Acido Ossalico", "OC(=O)C(=O)O", H, 2, C, 2, O, 4);
  public static final Molecule Malonic_Acid = Molecule.of("Acido 1,3-propandioco", "Acido Malonico", "C(C(=O)O)C(=O)O", C, 3, H, 4, O, 4);
  public static final Molecule Succinic_Acid = Molecule.of("Acido 1,4-butandioico", "Acido Succinico", "C(CC(=O)O)C(=O)O", C, 4, H, 6, O, 4);
  public static final Molecule Tartaric_Acid = Molecule.of("Acido 2R,3R-diidrossibutandioico", "Acido Tartarico", "C(C(C(=O)O)O)(C(=O)O)O", C, 4, H, 6, O, 6);
  public static final Molecule Maleic_Acid = Molecule.of("Acido cis-1,4-butendioico", "Acido Maleico", "O=C(O)\\C=C/C(=O)O", C, 4, H, 4, O, 4);
  public static final Molecule Fumaric_Acid = Molecule.of("Acido trans-1,4-butendioico", "Acido Fumarico", "C(=CC(=O)O)C(=O)O", C, 4, H, 4, O, 4);
  public static final Molecule Citric_Acid = Molecule.ofb("Acido Citrico", "C(C(=O)O)C(CC(=O)O)(C(=O)O)O", C, 6, H, 8, O, 7);

  public static final Molecule Glycerol = Molecule.of("1,2,3-propantriolo", new String[] { "Glicerolo", "Glicerina" }, "C(C(CO)O)O", C, 3, H, 8, O, 3);

  public static final Molecule Glucose = Molecule.ofb("Glucosio", "C(C1C(C(C(C(O1)O)O)O)O)O", C, 6, H, 12, O, 6);
  public static final Molecule Fructose = Molecule.ofb("Fruttosio", "O[C@H]1[C@H](O)[C@H](O[C@]1(O)CO)CO", C, 6, H, 12, O, 6);
  
  public static final Molecule Formaldehyde = Molecule.of("Metanale", new String[] { "Formaldeide", "Aldeide formica", "Formalina" }, "C=O", C, H, 2, O);
  public static final Molecule Acetaldehyde = Molecule.of("Etanale", new String[] { "Acetaldeide", "Aldeide acetica" }, "CC=O", C, 2, H, 4, O);
  public static final Molecule Propionaldehyde = Molecule.of("Propanale", new String[] { "Propionaldeide", "Aldeide propionica" }, "CCC=O", C, 3, H, 6, O);

  public static final Molecule Benzene = Molecule.of("Benzene", "", "C1=CC=CC=C1", C, 6, H, 6);
  public static final Molecule Phenol = Molecule.of("Fenolo", new String[] { "Acido fenico", "Acido carbolico", "Idrossibenzene", "Benzenolo" }, "C1=CC=C(C=C1)O", C, 6, H, 6, O);
  public static final Molecule Aniline = Molecule.of("Anilina", new String[] { "Fenilammina", "Amminobenzene", "Benzenammina" }, "C1=CC=C(C=C1)N", C, 6, H, 5, N, H, 2);
  
 
  public static final Molecule NaH = Molecule.of("Idruro di sodio", "", "[H-].[Na+]", Na, H);
  
  public static final Molecule Phosphate = Molecule.of("Fosfato", "", "[O-]P([O-])([O-])=O", P, 4, 3, '-');
  
  public static final Molecule Chloroform = Molecule.of("Triclorometano", "Cloroformio", "C(Cl)(Cl)Cl", Cl, 3, C, H);

  
  public static final Molecule Tryptophan = Molecule.ofb("Triptofano", "c1[nH]c2ccccc2c1C[C@H](N)C(=O)O", C, 11, H, 12, N, 2, O, 2);
  public static final Molecule Lysine = Molecule.ofb("Lisina", "C(CCN)CC(C(=O)O)N", C, 6, H, 14, N, 2, O, 2);
  public static final Molecule Aspartic_Acid = Molecule.ofb("Acido Aspartico", "O=C(O)CC(N)C(=O)O", C, 4, H, 7, N, 1, O, 4);
  
  public static final Molecule Ornitine = Molecule.ofb("Ornitina", "C(CC(C(=O)O)N)CN", C, 5, H, 12, N, 2, O, 2);
  
  public static final Molecule ATP = Molecule.ofb("ATP", "O=P(O)(O)OP(=O)(O)OP(=O)(O)OC[C@H]3O[C@@H](n2cnc1c(ncnc12)N)[C@H](O)[C@@H]3O", C, 10, H, 16, N, 5, O, 13, P, 3);
  public static final Molecule cAMP = Molecule.ofb("cAMP", "c1nc(c2c(n1)n(cn2)[C@H]3[C@@H]([C@H]4[C@H](O3)COP(=O)(O4)O)O)N", C, 10, H, 11, N, 5, O, 6, P, 1);
  
  public static final Molecule Carnitine = Molecule.ofb("Carnitina", "C[N+](C)(C)CC(CC(=O)[O-])O", C, 7, H, 15, N, 1, O, 3);

  public final static List<Molecule> molecules = new ArrayList<>();
  
  public final static Molecule[] molecules2 = {
    
    /*// ossidi cloro
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
    //Compound.of("Triidrossido di alluminio", "Idrossido di alluminio", "Idrossido di alluminio", Al),*/
    
    // acidi organici
    Metanoic_Acid,
    CH3COOH,
    Butanoic_Acid,
    
    Oxalic_Acid,
    Malonic_Acid,
    Succinic_Acid,
    Tartaric_Acid,
    Maleic_Acid,
    
    Fumaric_Acid,
    Citric_Acid,
    
    Phtalic_Acid,
    
    Formaldehyde,
    Acetaldehyde,
    Propionaldehyde,
    
    Glycerol,
    
    Benzene,
    Phenol,
    Aniline,
    
    Phosphate,
    
    Chloroform,
    
    
    Glucose,
    Fructose,
    
    
    Carnitine,
    
    // aminoacids
    Tryptophan,
    Lysine,
    Aspartic_Acid,
    
    Ornitine,
    
    ATP,
    cAMP,
  };
  

}
