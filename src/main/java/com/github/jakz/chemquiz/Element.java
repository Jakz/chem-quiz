package com.github.jakz.chemquiz;

public class Element
{
  public final String symbol;
  
  private Element(String symbol)
  {
    this.symbol = symbol;
  }
  
  public static final Element Oxygen = new Element("O");
  public static final Element Chlorine = new Element("Cl");
  public static final Element Copper = new Element("Cu");
  public static final Element Hydrogen = new Element("H");
  public static final Element Sodium = new Element("Na");

}
