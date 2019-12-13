package com.github.jakz.chemquiz;

public interface Attribute
{

  public final static Attribute BASE = new CompoundAttribute("base");
  public final static Attribute ACID = new CompoundAttribute("acido");
  public final static Attribute SALT = new CompoundAttribute("sale");
  
  public final static Attribute ANION = new CompoundAttribute("anione");
  public final static Attribute CATION = new CompoundAttribute("anione");

  public final static Attribute OXYDE = new CompoundAttribute("ossido");
  public final static Attribute HYDROXIDE = new CompoundAttribute("idrossido");
  
  public final static Attribute ORGANIC = new CompoundAttribute("organic");
  public final static Attribute INORGANIC = new CompoundAttribute("inorganic");
}
