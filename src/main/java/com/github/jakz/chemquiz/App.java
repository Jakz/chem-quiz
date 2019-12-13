package com.github.jakz.chemquiz;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
      for (Compound compound : Compound.compounds)
        System.out.println("Generated "+compound.formula);
    }
}
