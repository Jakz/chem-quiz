package com.github.jakz.chemquiz;

public interface Attribute
{

  public final static Attribute BASE = new MoleculeAttribute("base");
  public final static Attribute ACID = new MoleculeAttribute("acido");
  public final static Attribute SALT = new MoleculeAttribute("sale");
  
  public final static Attribute ANION = new MoleculeAttribute("anione");
  public final static Attribute CATION = new MoleculeAttribute("anione");

  public final static Attribute OXYDE = new MoleculeAttribute("ossido");
  public final static Attribute HYDROXIDE = new MoleculeAttribute("idrossido");
  
  public final static Attribute ORGANIC = new MoleculeAttribute("organic");
  public final static Attribute INORGANIC = new MoleculeAttribute("inorganic");
}
